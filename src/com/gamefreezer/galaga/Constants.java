package com.gamefreezer.galaga;

import java.util.SortedMap;

public class Constants {
    // pass an object to MyProperties, AbstractConfigFileReader
    // with method getConfigFileInputStream, hardcoded in subclasses
    // Appletxxxx and Androidxxx appropriately
    // but still won;t work if thise classes aren't yet instantiated - is this
    // possible?
    // must be = because look MyProperties is instantiated and then used as an
    // instance below
    // Game.getConfigFileOpener().open()
    // public static final MyProperties CONFIG = new MyProperties(CONFIG_FILE);

    // public static final MyProperties CONFIG = new MyProperties(Game
    // .openConfigFile());

    public static final String CONFIG_FILE = "config.properties";

    public static final MyProperties CONFIG = new MyProperties(Game
	    .openFile(CONFIG_FILE));

    // DELAY is the tick time of the game
    public static final int DELAY = CONFIG.getInt("DELAY");

    // LEVEL_DELAY is the pause between levels
    public static final int LEVEL_DELAY = CONFIG.getInt("LEVEL_DELAY");

    // screen settings
    public static final int LEFT_EDGE = CONFIG.getInt("LEFT_EDGE");
    public static final int BOTTOM_EDGE = CONFIG.getInt("BOTTOM_EDGE");
    public static final int SCREEN_WIDTH = CONFIG.getInt("SCREEN_WIDTH");
    public static final int SCREEN_HEIGHT = CONFIG.getInt("SCREEN_HEIGHT");
    public static final int LEFT_INDENT = CONFIG.getInt("LEFT_INDENT");
    public static final int TOP_INDENT = CONFIG.getInt("TOP_INDENT");
    public static final int INNER_TOP_INDENT = CONFIG
	    .getInt("INNER_TOP_INDENT");
    public static final int INNER_BOTTOM_INDENT = CONFIG
	    .getInt("INNER_BOTTOM_INDENT");
    public static final int BOTTOM_MASK_HEIGHT = CONFIG
	    .getInt("BOTTOM_MASK_HEIGHT");

    // health bar
    public static final int HEALTH_SIDE_INDENT = CONFIG
	    .getInt("HEALTH_SIDE_INDENT");
    public static final int HEALTH_BOTTOM_INDENT = CONFIG
	    .getInt("HEALTH_BOTTOM_INDENT");
    public static final int HEALTH_BAR_HEIGHT = CONFIG
	    .getInt("HEALTH_BAR_HEIGHT");
    public static final float HEALTH_COLOR_CHANGE = CONFIG
	    .getFloat("HEALTH_COLOR_CHANGE");

    // health penalties
    public static final int HEALTH_HIT_LIGHT = CONFIG
	    .getInt("HEALTH_HIT_LIGHT");
    public static final int HEALTH_HIT_SEVERE = CONFIG
	    .getInt("HEALTH_HIT_SEVERE");

    // score display
    public static final int SCORE_LEFT = CONFIG.getInt("SCORE_LEFT");
    public static final int SCORE_TOP = CONFIG.getInt("SCORE_TOP");
    public static final int SCORE_SPACING = CONFIG.getInt("SCORE_SPACING");
    public static final int SCORE_COMMAS = CONFIG.getInt("SCORE_COMMAS");
    public static final int BONUS_THRESHOLD = CONFIG.getInt("BONUS_THRESHOLD");
    public static final int KILLPOINTS_TRACKED = CONFIG
	    .getInt("KILLPOINTS_TRACKED");

    // score fonts
    public static final String NUM_0 = CONFIG.getString("NUM_0");
    public static final String NUM_1 = CONFIG.getString("NUM_1");
    public static final String NUM_2 = CONFIG.getString("NUM_2");
    public static final String NUM_3 = CONFIG.getString("NUM_3");
    public static final String NUM_4 = CONFIG.getString("NUM_4");
    public static final String NUM_5 = CONFIG.getString("NUM_5");
    public static final String NUM_6 = CONFIG.getString("NUM_6");
    public static final String NUM_7 = CONFIG.getString("NUM_7");
    public static final String NUM_8 = CONFIG.getString("NUM_8");
    public static final String NUM_9 = CONFIG.getString("NUM_9");

    // player ship
    public static final int SHIP_MOVEMENT = CONFIG.getInt("SHIP_MOVEMENT");
    public static final Location SHIP_START_LOCATION = new Location(
	    SCREEN_WIDTH / 2, 0);
    public static final String SHIP_IMAGE = CONFIG.getString("SHIP_IMAGE");

    // aliens
    public static final int ALIEN_POOL_SIZE = CONFIG.getInt("ALIEN_POOL_SIZE");
    public static SortedMap<Integer, Integer> ALIEN_SPEEDS = CONFIG
	    .getSortedMap("ALIEN_SPEEDS");
    public static final int ALIEN_MOVEMENT_VERTICAL = CONFIG
	    .getInt("ALIEN_MOVEMENT_VERTICAL");
    public static final int AL_SP_VERT = CONFIG
	    .getInt("ALIEN_SPACING_VERTICAL");
    public static final int AL_SP_HORIZ = CONFIG
	    .getInt("ALIEN_SPACING_HORIZONTAL");
    public static final int VERT_STEP = CONFIG.getInt("VERTICAL_STEP_DISTANCE");
    public static final String ALIEN_IMAGE = CONFIG.getString("ALIEN_IMAGE");
    public static final int MAX_FORMATION = CONFIG.getInt("MAX_FORMATION");

    // free moving aliens
    public static final int FREE_ALIEN_X_SPEED_MIN = CONFIG
	    .getInt("FREE_ALIEN_X_SPEED_MIN");
    public static final int FREE_ALIEN_X_SPEED_MAX = CONFIG
	    .getInt("FREE_ALIEN_X_SPEED_MAX");
    public static final int FREE_ALIEN_Y_SPEED_MIN = CONFIG
	    .getInt("FREE_ALIEN_Y_SPEED_MIN");
    public static final int FREE_ALIEN_Y_SPEED_MAX = CONFIG
	    .getInt("FREE_ALIEN_Y_SPEED_MAX");
    public static final int RETURN_SPEED_X = CONFIG.getInt("RETURN_SPEED_X");
    public static final int RETURN_SPEED_Y = CONFIG.getInt("RETURN_SPEED_Y");
    public static final int STAY_SOLO = CONFIG.getInt("STAY_SOLO");
    public static final int MIN_TIME_BETWEEN_RELEASES = CONFIG
	    .getInt("MIN_TIME_BETWEEN_RELEASES");
    public static final int MAX_TIME_BETWEEN_RELEASES = CONFIG
	    .getInt("MAX_TIME_BETWEEN_RELEASES");

    // explosions and text effects
    // TODO different explosions, even just simple color shifts
    public static final String EXPL_IMGS = CONFIG.getString("EXPL_IMGS");
    public static final String EXPL_TIMES = CONFIG.getString("EXPL_TIMES");
    public static final String LEVEL_COMPLETE_IMGS = CONFIG
	    .getString("LEVEL_COMPLETE_IMGS");
    public static final String LEVEL_COMPLETE_TIMES = CONFIG
	    .getString("LEVEL_COMPLETE_TIMES");
    public static final String COUNTDOWN_IMGS = CONFIG
	    .getString("COUNTDOWN_IMGS");
    public static final String COUNTDOWN_TIMES = CONFIG
	    .getString("COUNTDOWN_TIMES");

    // player bullets
    public static final int BULLET_MOVEMENT = CONFIG.getInt("BULLET_MOVEMENT");
    public static final int BULLETS_ON_SCREEN = CONFIG
	    .getInt("BULLETS_ON_SCREEN");
    public static final int MIN_TIME_BETWEEN_BULLETS = CONFIG
	    .getInt("MIN_TIME_BETWEEN_BULLETS");
    public static final String BULLET_IMAGE = CONFIG.getString("BULLET_IMAGE");

    // alien bullets
    public static final int ALIEN_BULLET_MOVEMENT = CONFIG
	    .getInt("ALIEN_BULLET_MOVEMENT");
    public static final int ALIEN_BULLETS_ON_SCREEN = CONFIG
	    .getInt("ALIEN_BULLETS_ON_SCREEN");
    public static final int MIN_TIME_BETWEEN_ALIEN_BULLETS = CONFIG
	    .getInt("MIN_TIME_BETWEEN_ALIEN_BULLETS");
    public static final int MAX_TIME_BETWEEN_ALIEN_BULLETS = CONFIG
	    .getInt("MAX_TIME_BETWEEN_ALIEN_BULLETS");
    public static final String ALIEN_BULLET_IMAGE = CONFIG
	    .getString("ALIEN_BULLET_IMAGE");

    // stars
    public static final int STAR_WIDTH = CONFIG.getInt("STAR_WIDTH");
    public static final int STAR_HEIGHT = CONFIG.getInt("STAR_HEIGHT");
    public static final int STAR_MOVEMENT_HORIZONTAL = CONFIG
	    .getInt("STAR_MOVEMENT_HORIZONTAL");
    public static final int STAR_MOVEMENT_VERTICAL = CONFIG
	    .getInt("STAR_MOVEMENT_VERTICAL");
    public static final int NUMBER_OF_STARS = CONFIG.getInt("NUMBER_OF_STARS");
    public static final int STAR_SPEED = CONFIG.getInt("STAR_SPEED");

    // colors
    public static final AbstractColor HEALTH_BAR_OUTLINE = CONFIG
	    .getColor("HEALTH_BAR_OUTLINE");
    public static final AbstractColor HEALTH_BAR_HIGH = CONFIG
	    .getColor("HEALTH_BAR_HIGH");
    public static final AbstractColor HEALTH_BAR_LOW = CONFIG
	    .getColor("HEALTH_BAR_LOW");
    public static final AbstractColor SCORE = CONFIG.getColor("SCORE");
    public static final AbstractColor BOTTOM_COVER = CONFIG
	    .getColor("BOTTOM_COVER");
    public static final AbstractColor BACKGROUND = CONFIG
	    .getColor("BACKGROUND");
    public static final AbstractColor BORDER = CONFIG.getColor("BORDER");

    // states
    public static final int INTRO_STATE = 0;
    public static final int BETWEEN_LIVES_STATE = 1;
    public static final int DEAD_STATE = 2;
    public static final int GAME_OVER_STATE = 3;
    public static final int PLAYING_STATE = 4;
    public static final int FROZEN_STATE = 5;
    public static final int LEVEL_CLEARED_STATE = 6;
    public static final int BONUS_MESSAGE_STATE = 7;
    public static final int READY_STATE = 8;
    public static final int WAIT_CLEAR_STATE = 9;
    public static final int BONUS_PAYOUT_STATE = 10;

    public static final int BETWEEN_LIVES_STATE_TIMER = 2000;
}
