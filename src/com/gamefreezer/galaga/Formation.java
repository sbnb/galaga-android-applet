package com.gamefreezer.galaga;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.SortedMap;

public class Formation extends AllocGuard {

    private final SpriteCache spriteCache;
    private final String propertiesFileName;
    private MyProperties props;
    private String layoutFile;
    private SortedMap<Integer, Integer> alienSpeeds;
    private int last = 0;
    private DataRecord[] records;
    private Screen screen;
    private int maxFormation;
    private AlienSpacing defSpacing;
    private AlienSpacing spacing;
    private SortedMap<Integer, Integer> defSpeeds;

    public Formation(SpriteCache spriteCache, Screen screen, int maxFormation,
	    AlienSpacing defSpacing, SortedMap<Integer, Integer> defSpeeds,
	    String propertiesFileName) {
	super();
	this.spriteCache = spriteCache;
	this.screen = screen;
	this.maxFormation = maxFormation;
	this.defSpacing = defSpacing;
	this.defSpeeds = defSpeeds;
	this.propertiesFileName = propertiesFileName;

	records = new DataRecord[maxFormation];

	loadProperties();
	initializeFromProperties();
	createLayout();
	centerFormation();
    }

    public void createAliens(Alien[] aliens) {
	assert allDead(aliens) : "Aliens are not all dead - kill them all!";
	for (int idx = 0; idx < last; idx++) {
	    resetAlien(aliens[idx], records[idx]);
	}
    }

    public void resetLivingAliens(Alien[] aliens) {
	for (int idx = 0; idx < last; idx++) {
	    partialReset(aliens[idx], records[idx]);
	}
    }

    public int size() {
	return last;
    }

    public void setAnchor(Location anchor) {
	anchor.moveTo(records[0].xLocation, records[0].yLocation);
    }

    public Properties getProperties() {
	return props.getProperties();
    }

    public SortedMap<Integer, Integer> getAlienSpeeds() {
	return alienSpeeds;
    }

    public int getVerticalStepDistance() {
	return spacing.vertStep;
    }

    private void resetAlien(Alien alien, DataRecord record) {
	alien.regenerate();
	alien.setHealth(record.health);
	partialReset(alien, record);
	alien.setSpeed(alienSpeeds.get(100), 0);
	alien.setMaxSpeed(alienSpeeds.get(100), alienSpeeds.get(100));
	alien.setImagePath(record.animationSource);
	alien.setDimensions(record.width, record.height);
	alien.setPoints(record.points);
    }

    private void partialReset(Alien alien, DataRecord record) {
	alien.setSolo(false);
	alien.setDiveComplete(false);
	alien.setTarget(-1, -1);
	alien.moveTo(record.xLocation, record.yLocation);
    }

    private boolean allDead(Alien[] aliens) {
	for (Alien alien : aliens) {
	    if (alien.isAlive()) {
		return false;
	    }
	}
	return true;
    }

    private void loadProperties() {
	props = new MyProperties(Tools.openFile(propertiesFileName));
    }

    private void initializeFromProperties() {
	assert props.size() > 0 : "properties must be loaded";

	spacing = new AlienSpacing( //
		props.getInt("spacingVertical", defSpacing.vertical), //
		props.getInt("spacingHorizontal", defSpacing.horizontal), //
		props.getInt("verticalStepDistance", defSpacing.vertStep));

	alienSpeeds = props.getSortedMap(props.getString("alienSpeeds", ""),
		defSpeeds);
	layoutFile = props.getString("layoutFile", "");
    }

    private void createLayout() {
	assert !"".equals(layoutFile) : "No layoutFile supplied in properties file "
		+ propertiesFileName;
	final InputStream inStream = Tools.openFile(layoutFile);
	try {
	    final BufferedReader bufferedReader = new BufferedReader(
		    new InputStreamReader(inStream));
	    String line;
	    int x = screen.inGameLeft();
	    int y = screen.inGameTop() + spacing.vertical;

	    while ((line = bufferedReader.readLine()) != null) {
		scanALineOfLayout(line, x, y);
		x = screen.inGameLeft();
		y += spacing.vertical;
	    }
	    inStream.close();
	} catch (Exception e) {
	    Tools.log("Exception reading layoutfile: " + e);
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
	    xLocal += spacing.horizontal;
	}
    }

    // save the information needed to create each alien in this level later
    private void saveAlienDetails(int x, int y, char chr) {
	assert last < maxFormation : "maxFormation(" + maxFormation
		+ ") <= last (" + last + "): increase it.";

	final String[] imageNames = getAlienImagePath(chr);
	final int[] renderTimes = props.getIntArray(chr + "RenderTimes", "0");
	final AnimationSource animationSource = new AnimationSource(imageNames,
		renderTimes);

	final DataRecord record = new DataRecord(x, y, animationSource,
		animationSource.width(spriteCache), animationSource
			.height(spriteCache), props.getInt(chr + "Points"),
		props.getInt(chr + "Health", 100));
	records[last] = record;
	last++;
    }

    private String[] getAlienImagePath(char chr) {
	if (props.containsKey(chr + "ImgPath")) {
	    return props.getStringArray(chr + "ImgPath");
	}
	assert false : chr + "ImgPath not found in " + propertiesFileName + "!";
	return null;
    }

    private void centerFormation() {
	final int offset = (screen.inGameWidth() - formationWidth()) / 2;
	for (int idx = 0; idx < last; idx++) {
	    records[idx].xLocation += offset;
	}
    }

    private int formationWidth() {
	assert records.length > 0 : "Formation must have elements";
	int leftMin = screen.inGameRight();
	int rightMax = screen.inGameLeft();
	for (int idx = 0; idx < last; idx++) {
	    leftMin = records[idx].xLocation < leftMin ? records[idx].xLocation
		    : leftMin;
	    rightMax = records[idx].xLocation + records[idx].width > rightMax ? records[idx].xLocation
		    + records[idx].width
		    : rightMax;
	}
	final int width = rightMax - leftMin;
	assert width > 0 : "formation width must be positive";
	return width;
    }

    private boolean isAlpha(char c) {
	return "abcdefghijklmnopqrstuvwxyz".contains(String.valueOf(c))
		|| "ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(String.valueOf(c));
    }
}
