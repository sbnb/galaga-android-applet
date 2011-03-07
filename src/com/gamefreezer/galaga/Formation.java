package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.ALIEN_SPEEDS;
import static com.gamefreezer.galaga.Constants.AL_SP_HORIZ;
import static com.gamefreezer.galaga.Constants.AL_SP_VERT;
import static com.gamefreezer.galaga.Constants.MAX_FORMATION;
import static com.gamefreezer.galaga.Constants.VERT_STEP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.SortedMap;

public class Formation extends AllocGuard {

    public Formation(String propertiesFileName) {
	super();
	this.propertiesFileName = propertiesFileName;
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
	alien.setImagePath(imagePaths[i], renderTimesStore[i],
		renderTicksStore[i]);
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
	spVert = props.getInt("spacingVertical", AL_SP_VERT);
	spHoriz = props.getInt("spacingHorizontal", AL_SP_HORIZ);
	vertStep = props.getInt("verticalStepDistance", VERT_STEP);
	alienSpeeds = props.getSortedMap(props.getString("alienSpeeds", ""),
		ALIEN_SPEEDS);
	layoutFile = props.getString("layoutFile", "");
    }

    private void createLayout() {
	assert !layoutFile.equals("") : "No layoutFile supplied in properties file "
		+ propertiesFileName;
	InputStream in = Game.openFile(layoutFile);
	try {
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String line;
	    int x = Screen.left();
	    int y = Screen.playableTop() - spVert;

	    while ((line = br.readLine()) != null) {
		scanALineOfLayout(line, x, y);
		x = Screen.left();
		y -= spVert;
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
	assert last < MAX_FORMATION : "MAX_FORMATION(" + MAX_FORMATION
		+ ") <= last (" + last + "): increase it.";

	String imagePath = getAlienImagePath(c);
	String renderTimes = props.getString(c + "RenderTimes", "");
	String renderTicks = props.getString(c + "RenderTicks", "");
	int width = Util.widthFromSprite(imagePath);
	int height = Util.heightFromSprite(imagePath);
	int points = props.getInt(c + "Points");

	xLocations[last] = x;
	yLocations[last] = y;
	imagePaths[last] = imagePath;
	renderTimesStore[last] = renderTimes;
	renderTicksStore[last] = renderTicks;
	widths[last] = width;
	heights[last] = height;
	pointsStore[last] = points;
	last++;
    }

    private String getAlienImagePath(char c) {
	assert props.containsKey(c + "ImgPath") : c
		+ "ImgPath \nnot found in \n  " + propertiesFileName
		+ ", \nbut " + c + " is used in layoutFile \n  " + layoutFile
		+ " ";

	if (props.containsKey(c + "ImgPath")) {
	    return props.getString(c + "ImgPath");
	}
	return "bad image path";
    }

    private void centerFormation() {
	int offset = (Screen.width() - formationWidth()) / 2;
	for (int i = 0; i < last; i++) {
	    xLocations[i] += offset;
	}
    }

    private int formationWidth() {
	assert xLocations.length > 0 : "Formation must have elements";
	int leftMin = Screen.width();
	int rightMax = Screen.left();
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

    private MyProperties props;
    private String propertiesFileName;
    private String layoutFile;
    private SortedMap<Integer, Integer> alienSpeeds;
    private int spVert;
    private int spHoriz;
    private int vertStep;

    private int last = 0;
    private int[] xLocations = new int[MAX_FORMATION];
    private int[] yLocations = new int[MAX_FORMATION];
    private String[] imagePaths = new String[MAX_FORMATION];
    private String[] renderTimesStore = new String[MAX_FORMATION];
    private String[] renderTicksStore = new String[MAX_FORMATION];
    private int[] widths = new int[MAX_FORMATION];
    private int[] heights = new int[MAX_FORMATION];
    private int[] pointsStore = new int[MAX_FORMATION];

}
