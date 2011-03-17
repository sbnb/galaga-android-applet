package com.gamefreezer.galaga;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.SortedMap;

public class Formation extends AllocGuard {

    private SpriteCache spriteStore;
    private MyProperties props;
    private String propertiesFileName;
    private String layoutFile;
    private SortedMap<Integer, Integer> alienSpeeds;
    private int spVert;
    private int spHoriz;
    private int vertStep;

    private int last = 0;
    private int[] xLocations;
    private int[] yLocations;
    private String[][] imageNamesStore;
    private int[][] renderTimesStore;
    private int[] widths;
    private int[] heights;
    private int[] pointsStore;
    private Constants cfg;

    public Formation(SpriteCache spriteStore, Constants cfg,
	    String propertiesFileName) {
	super();
	this.cfg = cfg;
	this.spriteStore = spriteStore;
	this.propertiesFileName = propertiesFileName;
	xLocations = new int[cfg.MAX_FORMATION];
	yLocations = new int[cfg.MAX_FORMATION];
	imageNamesStore = new String[cfg.MAX_FORMATION][];
	renderTimesStore = new int[cfg.MAX_FORMATION][];
	widths = new int[cfg.MAX_FORMATION];
	heights = new int[cfg.MAX_FORMATION];
	pointsStore = new int[cfg.MAX_FORMATION];
	loadProperties();
	initializeFromProperties();
	createLayout();
	centerFormation();
    }

    public void createAliens(Alien[] aliens) {
	assert allDead(aliens) : "Aliens are not all dead - kill them all!";
	for (int i = 0; i < last; i++) {
	    resetAlien(aliens[i], i);
	}
    }

    public void resetLivingAliens(Alien[] aliens) {
	for (int i = 0; i < last; i++) {
	    partialReset(aliens[i], i);
	}
    }

    public int size() {
	return last;
    }

    public int getXStart(int i) {
	return xLocations[i];
    }

    public int getYStart(int i) {
	return yLocations[i];
    }

    public void setAnchor(Location anchor) {
	anchor.moveTo(xLocations[0], yLocations[0]);
    }

    private void resetAlien(Alien alien, int i) {
	alien.regenerate();
	alien.setSolo(false);
	alien.setDiveComplete(false);
	alien.setTarget(-1, -1);
	alien.moveTo(xLocations[i], yLocations[i]);
	alien.setSpeed(alienSpeeds.get(100), 0);
	alien.setMaxSpeed(alienSpeeds.get(100), alienSpeeds.get(100));
	alien.setImagePath(imageNamesStore[i], renderTimesStore[i]);
	alien.setDimensions(widths[i], heights[i]);
	alien.setPoints(pointsStore[i]);
    }

    private void partialReset(Alien alien, int i) {
	alien.setSolo(false);
	alien.setDiveComplete(false);
	alien.setTarget(-1, -1);
	alien.moveTo(xLocations[i], yLocations[i]);
    }

    private boolean allDead(Alien[] aliensBetter) {
	for (Alien alien : aliensBetter) {
	    if (alien.isAlive())
		return false;
	}
	return true;
    }

    public Properties getProperties() {
	return props.getProperties();
    }

    public SortedMap<Integer, Integer> getAlienSpeeds() {
	return alienSpeeds;
    }

    public int getVerticalStepDistance() {
	return vertStep;
    }

    private void loadProperties() {
	Game.log("Formation.loadProperties(): new MyProperties("
		+ propertiesFileName + ")");
	props = new MyProperties(Game.openFile(propertiesFileName));
    }

    private void initializeFromProperties() {
	assert props.size() > 0 : "properties must be loaded";
	spVert = props.getInt("spacingVertical", cfg.AL_SP_VERT);
	spHoriz = props.getInt("spacingHorizontal", cfg.AL_SP_HORIZ);
	vertStep = props.getInt("verticalStepDistance", cfg.VERT_STEP);
	alienSpeeds = props.getSortedMap(props.getString("alienSpeeds", ""),
		cfg.ALIEN_SPEEDS);
	layoutFile = props.getString("layoutFile", "");
    }

    private void createLayout() {
	assert !layoutFile.equals("") : "No layoutFile supplied in properties file "
		+ propertiesFileName;
	InputStream in = Game.openFile(layoutFile);
	try {
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String line;
	    int x = cfg.SCREEN.inGameLeft();
	    int y = cfg.SCREEN.inGameTop() + spVert;

	    while ((line = br.readLine()) != null) {
		scanALineOfLayout(line, x, y);
		x = cfg.SCREEN.inGameLeft();
		y += spVert;
	    }
	    in.close();
	} catch (Exception e) {
	    Game.log("Exception reading layoutfile: " + e);
	    throw new AssertionError("Cannot read layoutFile at " + layoutFile
		    + " " + e);
	}
    }

    private void scanALineOfLayout(String line, final int x, final int y) {
	int xLocal = x;
	for (char c : line.toCharArray()) {
	    if (isAlpha(c)) {
		saveAlienDetails(xLocal, y, c);
	    }
	    xLocal += spHoriz;
	}
    }

    // save the information needed to create each alien in this level later
    private void saveAlienDetails(int x, int y, char c) {
	assert last < cfg.MAX_FORMATION : "MAX_FORMATION(" + cfg.MAX_FORMATION
		+ ") <= last (" + last + "): increase it.";

	String[] imageNames = getAlienImagePath(c);
	int[] renderTimes = props.getIntArray(c + "RenderTimes", "0");
	int width = Util.widthFromSprite(spriteStore, imageNames);
	int height = Util.heightFromSprite(spriteStore, imageNames);
	int points = props.getInt(c + "Points");

	xLocations[last] = x;
	yLocations[last] = y;
	imageNamesStore[last] = imageNames;
	renderTimesStore[last] = renderTimes;
	widths[last] = width;
	heights[last] = height;
	pointsStore[last] = points;
	last++;
    }

    private String[] getAlienImagePath(char c) {
	if (props.containsKey(c + "ImgPath")) {
	    return props.getStringArray(c + "ImgPath");
	}
	assert false : c + "ImgPath not found in " + propertiesFileName + "!";
	return null;
    }

    private void centerFormation() {
	int offset = (cfg.SCREEN.inGameWidth() - formationWidth()) / 2;
	for (int i = 0; i < last; i++) {
	    xLocations[i] += offset;
	}
    }

    private int formationWidth() {
	assert xLocations.length > 0 : "Formation must have elements";
	int leftMin = cfg.SCREEN.inGameRight();
	int rightMax = cfg.SCREEN.inGameLeft();
	for (int i = 0; i < last; i++) {
	    leftMin = xLocations[i] < leftMin ? xLocations[i] : leftMin;
	    rightMax = xLocations[i] + widths[i] > rightMax ? xLocations[i]
		    + widths[i] : rightMax;
	}
	int width = rightMax - leftMin;
	assert width > 0 : "formation width must be positive";
	return width;
    }

    private boolean isAlpha(char c) {
	return "abcdefghijklmnopqrstuvwxyz".contains(String.valueOf(c))
		|| "ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(String.valueOf(c));
    }
}
