package com.gamefreezer.galaga;

import java.util.SortedMap;

public class Constants {
    public final String CONFIG_FILE;
    public final MyProperties PROPS;
    public final SpriteCache SPRITE_CACHE;
    // DELAY is the tick time of the game
    public final int DELAY;

    // new screen settings
    public final int SCREEN_WIDTH;
    public final int SCREEN_HEIGHT;
    public final Border INNER_BORDER;
    public final Border OUTER_BORDER;

    public final Screen SCREEN;

    // health bar
    public final int HEALTH_SIDE_INDENT;
    public final int HEALTH_BOTTOM_INDENT;
    public final int HEALTH_BAR_WIDTH;
    public final int HEALTH_BAR_HEIGHT;
    public final float HEALTH_COLOR_CHANGE;

    // health penalties
    public final int HEALTH_HIT_LIGHT;
    public final int HEALTH_HIT_SEVERE;

    // score display
    public final int SCORE_LEFT;
    public final int SCORE_TOP;
    public final int SCORE_SPACING;
    public final int SCORE_COMMAS;
    public final int BONUS_THRESHOLD;
    public final int KILLPOINTS_TRACKED;

    // score fonts
    public final String NUM_0;
    public final String NUM_1;
    public final String NUM_2;
    public final String NUM_3;
    public final String NUM_4;
    public final String NUM_5;
    public final String NUM_6;
    public final String NUM_7;
    public final String NUM_8;
    public final String NUM_9;

    // player ship
    public final int SHIP_MOVEMENT;
    public final String[] SHIP_IMAGES;
    public final int[] SHIP_TIMES;

    // aliens
    public final int ALIEN_POOL_SIZE;
    public SortedMap<Integer, Integer> ALIEN_SPEEDS;
    public final int ALIEN_MOVEMENT_VERTICAL;
    public final int AL_SP_VERT;
    public final int AL_SP_HORIZ;
    public final int VERT_STEP;
    public final int MAX_FORMATION;

    // solo aliens
    public final CartesianIntRange SOLO_SPEED_RANGE;
    public final CartesianInt SOLO_RETURN_SPEED;
    public final int STAY_SOLO;
    public final IntRange SOLO_RELEASE_RANGE;

    // explosions and text effects
    public final int MAX_EXPLOSIONS;
    public final AnimationSource EXPL_ANIM_SRC;

    public final AnimationSource LEVEL_COMPLETE_ANIM_SRC;
    public final AnimationSource COUNTDOWN_ANIM_SRC;

    public final String GET_READY_IMG;
    public final CartesianInt GET_READY_TOPLEFT;

    public final String BONUS_DETAILS_IMG;
    public final CartesianInt BONUS_DETAILS_TOPLEFT;

    // player bullets
    public final int BULLET_MOVEMENT;
    public final int MAX_BULLETS_ON_SCREEN;
    public final int MIN_TIME_BETWEEN_BULLETS;
    public final String[] BULLET_IMAGES;
    public final int[] BULLET_TIMES;

    public final String[] GUN_REFS;
    public final Gun[] GUNS;

    // alien bullets
    public final int ALIEN_BULLET_MOVEMENT;
    public final int ALIEN_BULLETS_ON_SCREEN;
    public final IntRange ALIEN_FIRE_RATE;
    public final String[] ALIEN_BULLET_IMAGES;
    public final int[] ALIEN_BULLET_TIMES;

    // colors
    public final AbstractColor HEALTH_BAR_OUTLINE;
    public final AbstractColor HEALTH_BAR_HIGH;
    public final AbstractColor HEALTH_BAR_LOW;
    public final AbstractColor SCORE;
    public final AbstractColor BACKGROUND;
    public final AbstractColor BUTTON_COLOR;
    public final AbstractColor INNER_BORDER_COLOR;
    public final AbstractColor OUTER_BORDER_COLOR;
    public final AbstractColor BOTTOM_COLOR;

    public final StateTimes STATE_TIMES;

    public Constants(AbstractFileOpener fileOpener, AbstractLog log,
	    AbstractColor colorDecoder) {
	CONFIG_FILE = "config.properties";
	PROPS = new MyProperties(fileOpener.open(CONFIG_FILE), log,
		colorDecoder);
	SPRITE_CACHE = new SpriteCache(Tools.bitmapReader);
	DELAY = PROPS.getInt("DELAY");

	SCREEN_WIDTH = PROPS.getInt("SCREEN_WIDTH");
	SCREEN_HEIGHT = PROPS.getInt("SCREEN_HEIGHT");
	INNER_BORDER = new Border(PROPS.getIntArray("INNER_BORDER"));
	OUTER_BORDER = new Border(PROPS.getIntArray("OUTER_BORDER"));

	// health bar
	HEALTH_SIDE_INDENT = PROPS.getInt("HEALTH_SIDE_INDENT");
	HEALTH_BOTTOM_INDENT = PROPS.getInt("HEALTH_BOTTOM_INDENT");
	HEALTH_BAR_WIDTH = PROPS.getInt("HEALTH_BAR_WIDTH");
	HEALTH_BAR_HEIGHT = PROPS.getInt("HEALTH_BAR_HEIGHT");
	HEALTH_COLOR_CHANGE = PROPS.getFloat("HEALTH_COLOR_CHANGE");

	// health penalties
	HEALTH_HIT_LIGHT = PROPS.getInt("HEALTH_HIT_LIGHT");
	HEALTH_HIT_SEVERE = PROPS.getInt("HEALTH_HIT_SEVERE");

	// score display
	SCORE_LEFT = PROPS.getInt("SCORE_LEFT");
	SCORE_TOP = PROPS.getInt("SCORE_TOP");
	SCORE_SPACING = PROPS.getInt("SCORE_SPACING");
	SCORE_COMMAS = PROPS.getInt("SCORE_COMMAS");
	BONUS_THRESHOLD = PROPS.getInt("BONUS_THRESHOLD");
	KILLPOINTS_TRACKED = PROPS.getInt("KILLPOINTS_TRACKED");

	// score fonts
	NUM_0 = PROPS.getString("NUM_0");
	NUM_1 = PROPS.getString("NUM_1");
	NUM_2 = PROPS.getString("NUM_2");
	NUM_3 = PROPS.getString("NUM_3");
	NUM_4 = PROPS.getString("NUM_4");
	NUM_5 = PROPS.getString("NUM_5");
	NUM_6 = PROPS.getString("NUM_6");
	NUM_7 = PROPS.getString("NUM_7");
	NUM_8 = PROPS.getString("NUM_8");
	NUM_9 = PROPS.getString("NUM_9");

	// player ship
	SHIP_MOVEMENT = PROPS.getInt("SHIP_MOVEMENT");
	SHIP_IMAGES = PROPS.getStringArray("SHIP_IMAGES");
	SHIP_TIMES = PROPS.getIntArray("SHIP_TIMES");

	// aliens
	ALIEN_POOL_SIZE = PROPS.getInt("ALIEN_POOL_SIZE");
	ALIEN_SPEEDS = PROPS.getSortedMap("ALIEN_SPEEDS");
	ALIEN_MOVEMENT_VERTICAL = PROPS.getInt("ALIEN_MOVEMENT_VERTICAL");
	AL_SP_VERT = PROPS.getInt("ALIEN_SPACING_VERTICAL");
	AL_SP_HORIZ = PROPS.getInt("ALIEN_SPACING_HORIZONTAL");
	VERT_STEP = PROPS.getInt("VERTICAL_STEP_DISTANCE");
	MAX_FORMATION = PROPS.getInt("MAX_FORMATION");

	// solo aliens
	SOLO_SPEED_RANGE = new CartesianIntRange(PROPS
		.getIntArray("SOLO_ALIEN_SPEED_RANGES"));
	SOLO_RETURN_SPEED = new CartesianInt(PROPS
		.getIntArray("SOLO_RETURN_SPEED"));
	SOLO_RELEASE_RANGE = new IntRange(PROPS
		.getIntArray("SOLO_RELEASE_RANGE"));
	STAY_SOLO = PROPS.getInt("STAY_SOLO");

	// explosions and text effects
	MAX_EXPLOSIONS = PROPS.getInt("MAX_EXPLOSIONS");
	EXPL_ANIM_SRC = new AnimationSource(PROPS.getStringArray("EXPL_IMGS"),
		PROPS.getIntArray("EXPL_TIMES"));

	LEVEL_COMPLETE_ANIM_SRC = new AnimationSource(PROPS
		.getStringArray("LEVEL_COMPLETE_IMGS"), PROPS
		.getIntArray("LEVEL_COMPLETE_TIMES"));

	COUNTDOWN_ANIM_SRC = new AnimationSource(PROPS
		.getStringArray("COUNTDOWN_IMGS"), PROPS
		.getIntArray("COUNTDOWN_TIMES"));

	GET_READY_IMG = PROPS.getString("GET_READY_IMG");
	GET_READY_TOPLEFT = new CartesianInt(PROPS
		.getIntArray("GET_READY_TOPLEFT"));

	BONUS_DETAILS_IMG = PROPS.getString("BONUS_DETAILS_IMG");
	BONUS_DETAILS_TOPLEFT = new CartesianInt(PROPS
		.getIntArray("BONUS_DETAILS_TOPLEFT"));

	// player bullets
	BULLET_MOVEMENT = PROPS.getInt("BULLET_MOVEMENT");
	// BULLETS_ON_SCREEN = PROPS.getInt("BULLETS_ON_SCREEN");
	MIN_TIME_BETWEEN_BULLETS = PROPS.getInt("MIN_TIME_BETWEEN_BULLETS");
	BULLET_IMAGES = PROPS.getStringArray("BULLET_IMAGES");
	BULLET_TIMES = PROPS.getIntArray("BULLET_TIMES");

	// distinct guns
	// TODO move to own builder class
	GUN_REFS = PROPS.getStringArray("GUN_REFS");
	GUNS = new Gun[GUN_REFS.length];
	int maxBulletsOnScreen = 0;
	for (int i = 0; i < GUNS.length; i++) {
	    final String gunName = GUN_REFS[i];
	    // TODO read displayBar details from file
	    Rectangle displayBarRect = new Rectangle(60, 40, 100, 10);
	    AbstractColor outlineColor = Tools.decodeColor("#AAAAAA");
	    AbstractColor primaryFillColor = Tools.decodeColor("#6666FF");
	    AbstractColor secondaryFillColor = Tools.decodeColor("#FF0000");
	    StatusBar statusBar = new StatusBar(displayBarRect, 100.0f,
		    outlineColor, primaryFillColor, secondaryFillColor);

	    final AnimationSource bulletAnimSrc = new AnimationSource(PROPS
		    .getStringArray(gunName + "_BULLET_IMAGES"), //
		    PROPS.getIntArray(gunName + "_BULLET_TIMES"));
	    int bulletsOnScreen = PROPS.getInt(gunName + "_BULLETS_ON_SCREEN");
	    maxBulletsOnScreen = Math.max(maxBulletsOnScreen, bulletsOnScreen);
	    AnimationPool bulletAnimPool = new AnimationPool(SPRITE_CACHE,
		    bulletAnimSrc, bulletsOnScreen);

	    final AnimationSource hitAnimSrc = new AnimationSource(PROPS
		    .getStringArray(gunName + "_HIT_IMAGES"), //
		    PROPS.getIntArray(gunName + "_HIT_TIMES"));
	    // TODO magic number for size of hit animation pool
	    AnimationPool hitAnimPool = new AnimationPool(SPRITE_CACHE,
		    hitAnimSrc, 100);

	    final AnimationSource firingAnimationSource = new AnimationSource(
		    PROPS.getStringArray(gunName + "_FIRING_IMAGES"), //
		    PROPS.getIntArray(gunName + "_FIRING_TIMES"));
	    Animation firingAnimation = new Animation(SPRITE_CACHE,
		    firingAnimationSource, true);

	    GUNS[i] = new Gun( //
		    PROPS.getInt(gunName + "_BULLET_SPEED"), //
		    PROPS.getInt(gunName + "_RATE_OF_FIRE"), //
		    PROPS.getInt(gunName + "_DAMAGE"), //
		    PROPS.getInt(gunName + "_HEAT_INCREMENT"), //
		    PROPS.getInt(gunName + "_COOLING"), //
		    bulletAnimPool, hitAnimPool, firingAnimation, statusBar);

	}
	MAX_BULLETS_ON_SCREEN = maxBulletsOnScreen;

	// alien bullets
	ALIEN_BULLET_MOVEMENT = PROPS.getInt("ALIEN_BULLET_MOVEMENT");
	ALIEN_BULLETS_ON_SCREEN = PROPS.getInt("ALIEN_BULLETS_ON_SCREEN");
	ALIEN_FIRE_RATE = new IntRange(PROPS.getIntArray("ALIEN_FIRE_RATE"));
	ALIEN_BULLET_IMAGES = PROPS.getStringArray("ALIEN_BULLET_IMAGES");
	ALIEN_BULLET_TIMES = PROPS.getIntArray("ALIEN_BULLET_TIMES");

	// colors
	HEALTH_BAR_OUTLINE = PROPS.getColor("HEALTH_BAR_OUTLINE");
	HEALTH_BAR_HIGH = PROPS.getColor("HEALTH_BAR_HIGH");
	HEALTH_BAR_LOW = PROPS.getColor("HEALTH_BAR_LOW");
	SCORE = PROPS.getColor("SCORE");
	BACKGROUND = PROPS.getColor("BACKGROUND");
	BUTTON_COLOR = PROPS.getColor("BUTTON_COLOR");
	INNER_BORDER_COLOR = PROPS.getColor("INNER_BORDER_COLOR");
	OUTER_BORDER_COLOR = PROPS.getColor("OUTER_BORDER_COLOR");
	BOTTOM_COLOR = PROPS.getColor("BOTTOM_COLOR");

	STATE_TIMES = new StateTimes(PROPS.getInt("LEVEL_DELAY"), PROPS
		.getInt("BETWEEN_LIVES"), PROPS.getInt("WAIT_CLEAR"), PROPS
		.getInt("LEVEL_CLEARED"), PROPS.getInt("BONUS_MESSAGE"), PROPS
		.getInt("BONUS_PAYOUT"));

	SCREEN = new Screen(SCREEN_WIDTH, SCREEN_HEIGHT, OUTER_BORDER,
		INNER_BORDER);
    }
}
