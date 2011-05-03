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

    // general animations
    public final int MAX_FRAMES;

    // side buttons
    public final int BTN_WIDTH;
    public final int BTN_HEIGHT;
    public final int BTN_OFFSET;

    // health bar
    public final Rectangle HEALTH_RECT;
    public final float HB_CLR_CHANGE;
    public final int HB_HIT_LIGHT;
    public final int HB_HIT_SEVERE;
    public final AbstractColor HB_OUTLINE_CLR;
    public final AbstractColor HB_HIGH_CLR;
    public final AbstractColor HB_LOW_CLR;

    // score display
    public final int SCORE_LEFT;
    public final int SCORE_TOP;
    public final int SCORE_SPACING;
    public final int SCORE_COMMAS;
    public final int BONUS_THRESHOLD;
    public final int KILLPOINTS_TRACKED;

    // numeric fonts for score, bonus, countdowns
    public final String[] DIGITS;

    // player ship
    public final int SHIP_MOVEMENT;
    public final String[] SHIP_IMAGES;
    public final int[] SHIP_TIMES;

    // aliens
    public final int ALIEN_POOL_SIZE;
    public SortedMap<Integer, Integer> AL_SPEEDS;

    public final AlienSpacing AL_SPACING;

    public final int MAX_FORMATION;
    public final int HIT_RENDERER_POOL_SIZE;

    // solo aliens
    public final CartesianIntRange SOLO_SPEED_RANGE;
    public final CartesianInt SOLO_RETURN_SPEED;
    public final int STAY_SOLO;
    public final IntRange SOLO_RELEASE_RANGE;

    // explosions and text effects
    public final Explosions EXPLOSIONS;
    public final AnimationSource EXPLOSION_SRC;

    public final FixedAnimation COUNT_DOWN;
    public final FixedAnimation GET_READY;
    public final FixedAnimation BONUS_DETAILS;
    public final FixedAnimation LEVEL_COMPLETE;

    // player bullets
    public final int BULLET_MOVEMENT;
    public final int MAX_BULLETS_ON_SCREEN;
    public final int MIN_TIME_BETWEEN_BULLETS;
    public final String[] BULLET_IMAGES;
    public final int[] BULLET_TIMES;

    public final Gun[] GUNS;

    // alien bullets
    public final int ALIEN_BULLET_MOVEMENT;
    public final int ALIEN_BULLETS_ON_SCREEN;
    public final IntRange ALIEN_FIRE_RATE;
    public final String[] ALIEN_BULLET_IMAGES;
    public final int[] ALIEN_BULLET_TIMES;

    // colors
    public final AbstractColor SCORE;
    public final AbstractColor BACKGROUND;
    public final AbstractColor BUTTON_COLOR;
    public final AbstractColor INNER_BORDER_COLOR;
    public final AbstractColor OUTER_BORDER_COLOR;
    public final AbstractColor BOTTOM_COLOR;

    public final StateTimes STATE_TIMES;

    public Constants(AbstractFileOpener fileOpener, AbstractColor colorDecoder) {
	CONFIG_FILE = "config.properties";
	PROPS = new MyProperties(fileOpener.open(CONFIG_FILE), colorDecoder);
	SPRITE_CACHE = new SpriteCache(Tools.bitmapReader);
	DELAY = PROPS.getInt("DELAY");

	SCREEN_WIDTH = PROPS.getInt("SCREEN_WIDTH");
	SCREEN_HEIGHT = PROPS.getInt("SCREEN_HEIGHT");
	INNER_BORDER = new Border(PROPS.getIntArray("INNER_BORDER"));
	OUTER_BORDER = new Border(PROPS.getIntArray("OUTER_BORDER"));
	SCREEN = new Screen(SCREEN_WIDTH, SCREEN_HEIGHT, OUTER_BORDER,
		INNER_BORDER);

	// general animations
	MAX_FRAMES = PROPS.getInt("MAX_FRAMES");

	// side buttons
	BTN_WIDTH = PROPS.getInt("BTN_WIDTH");
	BTN_HEIGHT = PROPS.getInt("BTN_HEIGHT");
	BTN_OFFSET = PROPS.getInt("BTN_OFFSET");

	// health bar
	HEALTH_RECT = new Rectangle(PROPS.getIntArray("HB_DIMS"));
	HEALTH_RECT.translate(SCREEN.inGameLeft(), SCREEN.inGameTop());
	HB_CLR_CHANGE = PROPS.getFloat("HB_CLR_CHANGE");
	HB_HIT_LIGHT = PROPS.getInt("HB_HIT_LIGHT");
	HB_HIT_SEVERE = PROPS.getInt("HB_HIT_SEVERE");
	HB_OUTLINE_CLR = PROPS.getColor("HB_OUTLINE_CLR");
	HB_HIGH_CLR = PROPS.getColor("HB_HIGH_CLR");
	HB_LOW_CLR = PROPS.getColor("HB_LOW_CLR");

	// score display
	SCORE_LEFT = PROPS.getInt("SCORE_LEFT");
	SCORE_TOP = PROPS.getInt("SCORE_TOP");
	SCORE_SPACING = PROPS.getInt("SCORE_SPACING");
	SCORE_COMMAS = PROPS.getInt("SCORE_COMMAS");
	BONUS_THRESHOLD = PROPS.getInt("BONUS_THRESHOLD");
	KILLPOINTS_TRACKED = PROPS.getInt("KILLPOINTS_TRACKED");

	// numeric fonts for score, bonus, countdowns
	DIGITS = PROPS.getStringArray("DIGITS");

	// player ship
	SHIP_MOVEMENT = PROPS.getInt("SHIP_MOVEMENT");
	SHIP_IMAGES = PROPS.getStringArray("SHIP_IMAGES");
	SHIP_TIMES = PROPS.getIntArray("SHIP_TIMES");

	// aliens
	ALIEN_POOL_SIZE = PROPS.getInt("ALIEN_POOL_SIZE");
	AL_SPEEDS = PROPS.getSortedMap("ALIEN_SPEEDS");

	AL_SPACING = new AlienSpacing( //
		PROPS.getInt("ALIEN_SPACING_VERTICAL"), //
		PROPS.getInt("ALIEN_SPACING_HORIZONTAL"), //
		PROPS.getInt("VERTICAL_STEP_DISTANCE"));

	MAX_FORMATION = PROPS.getInt("MAX_FORMATION");
	HIT_RENDERER_POOL_SIZE = PROPS.getInt("HIT_RENDERER_POOL_SIZE");

	// solo aliens
	SOLO_SPEED_RANGE = new CartesianIntRange(PROPS
		.getIntArray("SOLO_ALIEN_SPEED_RANGES"));
	SOLO_RETURN_SPEED = new CartesianInt(PROPS
		.getIntArray("SOLO_RETURN_SPEED"));
	SOLO_RELEASE_RANGE = new IntRange(PROPS
		.getIntArray("SOLO_RELEASE_RANGE"));
	STAY_SOLO = PROPS.getInt("STAY_SOLO");

	// explosions and text effects
	EXPLOSION_SRC = new AnimationSource(PROPS.getStringArray("EXPL_IMGS"),
		PROPS.getIntArray("EXPL_TIMES"));
	EXPLOSIONS = new Explosions(SPRITE_CACHE, MAX_FRAMES, EXPLOSION_SRC,
		PROPS.getInt("MAX_EXPLOSIONS"));

	AnimationSource levelCompleteSrc = new AnimationSource(PROPS
		.getStringArray("LEVEL_COMPLETE_IMGS"), PROPS
		.getIntArray("LEVEL_COMPLETE_TIMES"));
	LEVEL_COMPLETE = FixedAnimation.build(SPRITE_CACHE, MAX_FRAMES,
		levelCompleteSrc, PROPS.getIntArray("LEVEL_COMPLETE_TOPLEFT"));

	AnimationSource countDownSrc = new AnimationSource(PROPS
		.getStringArray("COUNTDOWN_IMGS"), PROPS
		.getIntArray("COUNTDOWN_TIMES"));
	COUNT_DOWN = FixedAnimation.build(SPRITE_CACHE, MAX_FRAMES,
		countDownSrc, PROPS.getIntArray("COUNTDOWN_TOPLEFT"));

	AnimationSource getReadySrc = new AnimationSource(PROPS
		.getStringArray("GET_READY_IMGS"), PROPS
		.getIntArray("GET_READY_TIMES"));
	GET_READY = FixedAnimation.build(SPRITE_CACHE, MAX_FRAMES, getReadySrc,
		PROPS.getIntArray("GET_READY_TOPLEFT"));

	AnimationSource bonusDetailsSrc = new AnimationSource(PROPS
		.getStringArray("BONUS_DETAILS_IMGS"), PROPS
		.getIntArray("BONUS_DETAILS_TIMES"));
	BONUS_DETAILS = FixedAnimation.build(SPRITE_CACHE, MAX_FRAMES,
		bonusDetailsSrc, PROPS.getIntArray("BONUS_DETAILS_TOPLEFT"));

	// player bullets
	BULLET_MOVEMENT = PROPS.getInt("BULLET_MOVEMENT");
	// BULLETS_ON_SCREEN = PROPS.getInt("BULLETS_ON_SCREEN");
	MIN_TIME_BETWEEN_BULLETS = PROPS.getInt("MIN_TIME_BETWEEN_BULLETS");
	BULLET_IMAGES = PROPS.getStringArray("BULLET_IMAGES");
	BULLET_TIMES = PROPS.getIntArray("BULLET_TIMES");

	// distinct guns
	GunBuilder gunBuilder = new GunBuilder(PROPS, MAX_FRAMES, SCREEN,
		SPRITE_CACHE);
	GUNS = gunBuilder.getGuns();
	MAX_BULLETS_ON_SCREEN = gunBuilder.getMaxBulletsOnScreen();

	// alien bullets
	ALIEN_BULLET_MOVEMENT = PROPS.getInt("ALIEN_BULLET_MOVEMENT");
	ALIEN_BULLETS_ON_SCREEN = PROPS.getInt("ALIEN_BULLETS_ON_SCREEN");
	ALIEN_FIRE_RATE = new IntRange(PROPS.getIntArray("ALIEN_FIRE_RATE"));
	ALIEN_BULLET_IMAGES = PROPS.getStringArray("ALIEN_BULLET_IMAGES");
	ALIEN_BULLET_TIMES = PROPS.getIntArray("ALIEN_BULLET_TIMES");

	// colors
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
    }
}
