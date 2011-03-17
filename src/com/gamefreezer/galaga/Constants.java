package com.gamefreezer.galaga;

import java.util.SortedMap;

public class Constants {

    public Constants(AbstractFileOpener fileOpener, AbstractLog log,
	    AbstractColor colorDecoder) {
	CONFIG_FILE = "config.properties";
	PROPS = new MyProperties(fileOpener.open(CONFIG_FILE), log,
		colorDecoder);
	DELAY = PROPS.getInt("DELAY");
	LEVEL_DELAY = PROPS.getInt("LEVEL_DELAY");

	SCREEN_WIDTH = PROPS.getInt("SCREEN_WIDTH");
	SCREEN_HEIGHT = PROPS.getInt("SCREEN_HEIGHT");
	OUTER_VERTICAL_BORDER = PROPS.getInt("OUTER_VERTICAL_BORDER");
	INNER_VERTICAL_BORDER = PROPS.getInt("INNER_VERTICAL_BORDER");
	OUTER_HORIZONTAL_BORDER = PROPS.getInt("OUTER_HORIZONTAL_BORDER");
	INNER_HORIZONTAL_BORDER = PROPS.getInt("INNER_HORIZONTAL_BORDER");

	BOTTOM_MASK_HEIGHT = PROPS.getInt("BOTTOM_MASK_HEIGHT");

	// health bar
	HEALTH_SIDE_INDENT = PROPS.getInt("HEALTH_SIDE_INDENT");
	HEALTH_BOTTOM_INDENT = PROPS.getInt("HEALTH_BOTTOM_INDENT");
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
	SHIP_IMAGE = PROPS.getString("SHIP_IMAGE");

	// aliens
	ALIEN_POOL_SIZE = PROPS.getInt("ALIEN_POOL_SIZE");
	ALIEN_SPEEDS = PROPS.getSortedMap("ALIEN_SPEEDS");
	ALIEN_MOVEMENT_VERTICAL = PROPS.getInt("ALIEN_MOVEMENT_VERTICAL");
	AL_SP_VERT = PROPS.getInt("ALIEN_SPACING_VERTICAL");
	AL_SP_HORIZ = PROPS.getInt("ALIEN_SPACING_HORIZONTAL");
	VERT_STEP = PROPS.getInt("VERTICAL_STEP_DISTANCE");
	ALIEN_IMAGE = PROPS.getString("ALIEN_IMAGE");
	MAX_FORMATION = PROPS.getInt("MAX_FORMATION");

	// free moving aliens
	FREE_ALIEN_X_SPEED_MIN = PROPS.getInt("FREE_ALIEN_X_SPEED_MIN");
	FREE_ALIEN_X_SPEED_MAX = PROPS.getInt("FREE_ALIEN_X_SPEED_MAX");
	FREE_ALIEN_Y_SPEED_MIN = PROPS.getInt("FREE_ALIEN_Y_SPEED_MIN");
	FREE_ALIEN_Y_SPEED_MAX = PROPS.getInt("FREE_ALIEN_Y_SPEED_MAX");
	RETURN_SPEED_X = PROPS.getInt("RETURN_SPEED_X");
	RETURN_SPEED_Y = PROPS.getInt("RETURN_SPEED_Y");
	STAY_SOLO = PROPS.getInt("STAY_SOLO");
	MIN_TIME_BETWEEN_RELEASES = PROPS.getInt("MIN_TIME_BETWEEN_RELEASES");
	MAX_TIME_BETWEEN_RELEASES = PROPS.getInt("MAX_TIME_BETWEEN_RELEASES");

	// explosions and text effects
	// TODO different explosions, even just simple color shifts
	MAX_EXPLOSIONS = PROPS.getInt("MAX_EXPLOSIONS");
	EXPL_IMGS = PROPS.getStringArray("EXPL_IMGS");
	EXPL_TIMES = PROPS.getIntArray("EXPL_TIMES");
	LEVEL_COMPLETE_IMGS = PROPS.getStringArray("LEVEL_COMPLETE_IMGS");
	LEVEL_COMPLETE_TIMES = PROPS.getIntArray("LEVEL_COMPLETE_TIMES");
	COUNTDOWN_IMGS = PROPS.getStringArray("COUNTDOWN_IMGS");
	COUNTDOWN_TIMES = PROPS.getIntArray("COUNTDOWN_TIMES");

	GET_READY = PROPS.getString("GET_READY");
	GET_READY_X = PROPS.getInt("GET_READY_X");
	GET_READY_Y = PROPS.getInt("GET_READY_Y");

	BONUS_DETAILS = PROPS.getString("BONUS_DETAILS");
	BONUS_DETAILS_X = PROPS.getInt("BONUS_DETAILS_X");
	BONUS_DETAILS_Y = PROPS.getInt("BONUS_DETAILS_Y");

	// player bullets
	BULLET_MOVEMENT = PROPS.getInt("BULLET_MOVEMENT");
	BULLETS_ON_SCREEN = PROPS.getInt("BULLETS_ON_SCREEN");
	MIN_TIME_BETWEEN_BULLETS = PROPS.getInt("MIN_TIME_BETWEEN_BULLETS");
	BULLET_IMAGE = PROPS.getString("BULLET_IMAGE");

	// alien bullets
	ALIEN_BULLET_MOVEMENT = PROPS.getInt("ALIEN_BULLET_MOVEMENT");
	ALIEN_BULLETS_ON_SCREEN = PROPS.getInt("ALIEN_BULLETS_ON_SCREEN");
	MIN_TIME_BETWEEN_ALIEN_BULLETS = PROPS
		.getInt("MIN_TIME_BETWEEN_ALIEN_BULLETS");
	MAX_TIME_BETWEEN_ALIEN_BULLETS = PROPS
		.getInt("MAX_TIME_BETWEEN_ALIEN_BULLETS");
	ALIEN_BULLET_IMAGE = PROPS.getString("ALIEN_BULLET_IMAGE");

	// stars
	STAR_WIDTH = PROPS.getInt("STAR_WIDTH");
	STAR_HEIGHT = PROPS.getInt("STAR_HEIGHT");
	STAR_MOVEMENT_HORIZONTAL = PROPS.getInt("STAR_MOVEMENT_HORIZONTAL");
	STAR_MOVEMENT_VERTICAL = PROPS.getInt("STAR_MOVEMENT_VERTICAL");
	NUMBER_OF_STARS = PROPS.getInt("NUMBER_OF_STARS");
	STAR_SPEED = PROPS.getInt("STAR_SPEED");

	// colors
	HEALTH_BAR_OUTLINE = PROPS.getColor("HEALTH_BAR_OUTLINE");
	HEALTH_BAR_HIGH = PROPS.getColor("HEALTH_BAR_HIGH");
	HEALTH_BAR_LOW = PROPS.getColor("HEALTH_BAR_LOW");
	SCORE = PROPS.getColor("SCORE");
	BOTTOM_COVER = PROPS.getColor("BOTTOM_COVER");
	BACKGROUND = PROPS.getColor("BACKGROUND");
	BORDER = PROPS.getColor("BORDER");

	// states
	INTRO_STATE = 0;
	BETWEEN_LIVES_STATE = 1;
	DEAD_STATE = 2;
	GAME_OVER_STATE = 3;
	PLAYING_STATE = 4;
	FROZEN_STATE = 5;
	LEVEL_CLEARED_STATE = 6;
	BONUS_MESSAGE_STATE = 7;
	READY_STATE = 8;
	WAIT_CLEAR_STATE = 9;
	BONUS_PAYOUT_STATE = 10;

	BETWEEN_LIVES_STATE_TIMER = 2000;

	SCREEN = new Screen(this);
    }

    public final String CONFIG_FILE;

    public final MyProperties PROPS;

    // DELAY is the tick time of the game
    public final int DELAY;

    // LEVEL_DELAY is the pause between levels
    public final int LEVEL_DELAY;

    // new screen settings
    public final int SCREEN_WIDTH;
    public final int SCREEN_HEIGHT;
    public final int OUTER_VERTICAL_BORDER;
    public final int INNER_VERTICAL_BORDER;
    public final int OUTER_HORIZONTAL_BORDER;
    public final int INNER_HORIZONTAL_BORDER;
    public final int BOTTOM_MASK_HEIGHT;

    public final Screen SCREEN;

    // health bar
    public final int HEALTH_SIDE_INDENT;
    public final int HEALTH_BOTTOM_INDENT;
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
    public final String SHIP_IMAGE;

    // aliens
    public final int ALIEN_POOL_SIZE;
    public SortedMap<Integer, Integer> ALIEN_SPEEDS;
    public final int ALIEN_MOVEMENT_VERTICAL;
    public final int AL_SP_VERT;
    public final int AL_SP_HORIZ;
    public final int VERT_STEP;
    public final String ALIEN_IMAGE;
    public final int MAX_FORMATION;

    // free moving aliens
    public final int FREE_ALIEN_X_SPEED_MIN;

    public final int FREE_ALIEN_X_SPEED_MAX;

    public final int FREE_ALIEN_Y_SPEED_MIN;

    public final int FREE_ALIEN_Y_SPEED_MAX;

    public final int RETURN_SPEED_X;
    public final int RETURN_SPEED_Y;
    public final int STAY_SOLO;
    public final int MIN_TIME_BETWEEN_RELEASES;

    public final int MAX_TIME_BETWEEN_RELEASES;

    // explosions and text effects
    public final int MAX_EXPLOSIONS;
    public final String[] EXPL_IMGS;
    public final int[] EXPL_TIMES;
    public final String[] LEVEL_COMPLETE_IMGS;
    public final int[] LEVEL_COMPLETE_TIMES;
    public final String[] COUNTDOWN_IMGS;
    public final int[] COUNTDOWN_TIMES;

    public final String GET_READY;
    public final int GET_READY_X;
    public final int GET_READY_Y;

    public final String BONUS_DETAILS;
    public final int BONUS_DETAILS_X;
    public final int BONUS_DETAILS_Y;

    // player bullets
    public final int BULLET_MOVEMENT;
    public final int BULLETS_ON_SCREEN;
    public final int MIN_TIME_BETWEEN_BULLETS;

    public final String BULLET_IMAGE;

    // alien bullets
    public final int ALIEN_BULLET_MOVEMENT;
    public final int ALIEN_BULLETS_ON_SCREEN;
    public final int MIN_TIME_BETWEEN_ALIEN_BULLETS;
    public final int MAX_TIME_BETWEEN_ALIEN_BULLETS;
    public final String ALIEN_BULLET_IMAGE;

    // stars
    public final int STAR_WIDTH;
    public final int STAR_HEIGHT;
    public final int STAR_MOVEMENT_HORIZONTAL;

    public final int STAR_MOVEMENT_VERTICAL;

    public final int NUMBER_OF_STARS;
    public final int STAR_SPEED;

    // colors
    public final AbstractColor HEALTH_BAR_OUTLINE;

    public final AbstractColor HEALTH_BAR_HIGH;
    public final AbstractColor HEALTH_BAR_LOW;
    public final AbstractColor SCORE;
    public final AbstractColor BOTTOM_COVER;
    public final AbstractColor BACKGROUND;
    public final AbstractColor BORDER;

    // states
    public final int INTRO_STATE;
    public final int BETWEEN_LIVES_STATE;
    public final int DEAD_STATE;
    public final int GAME_OVER_STATE;
    public final int PLAYING_STATE;
    public final int FROZEN_STATE;
    public final int LEVEL_CLEARED_STATE;
    public final int BONUS_MESSAGE_STATE;
    public final int READY_STATE;
    public final int WAIT_CLEAR_STATE;
    public final int BONUS_PAYOUT_STATE;

    public final int BETWEEN_LIVES_STATE_TIMER;
}
