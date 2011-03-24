package com.gamefreezer.galaga;

import java.util.List;

public class State {
    public enum States {
	INTRO, BETWEEN_LIVES, DEAD, GAME_OVER, PLAYING, FROZEN, //
	LEVEL_CLEARED, BONUS_MESSAGE, READY, WAIT_CLEAR, BONUS_PAYOUT
    }

    // TODO make this an enum
    public static int INTRO_STATE = 0;
    public static int BETWEEN_LIVES_STATE = 1;
    public static int DEAD_STATE = 2;
    public static int GAME_OVER_STATE = 3;
    public static int PLAYING_STATE = 4;
    public static int FROZEN_STATE = 5;
    public static int LEVEL_CLEARED_STATE = 6;
    public static int BONUS_MESSAGE_STATE = 7;
    public static int READY_STATE = 8;
    public static int WAIT_CLEAR_STATE = 9;
    public static int BONUS_PAYOUT_STATE = 10;

    private int state;
    private long stateTimer;
    private Score score;
    private Bullets playerBullets;
    private Bullets alienBullets;
    private AnimationFrames shipExplosion;
    private AnimationFrames countDown;
    private AnimationFrames textFx;
    private Aliens aliens;
    private List<Formation> formations;
    private int formationsIndex = 0;
    private StateTimes stateTimes;

    public State(StateTimes stateTimes, Aliens aliens,
	    List<Formation> formations, Score score, Bullets playerBullets,
	    Bullets alienBullets, AnimationFrames shipExplosion,
	    AnimationFrames countDown, AnimationFrames textFx) {
	this.stateTimes = stateTimes;
	this.aliens = aliens;
	this.formations = formations;
	this.score = score;
	this.playerBullets = playerBullets;
	this.alienBullets = alienBullets;
	this.shipExplosion = shipExplosion;
	this.countDown = countDown;
	this.textFx = textFx;
	enforcePreconditions();
	state = READY_STATE;
	setStateTimer(stateTimes.LEVEL_DELAY);
	changeFormation();
    }

    private void enforcePreconditions() {
	assert aliens != null : "aliens is null!";
	assert formations != null : "formations is null!";
	assert score != null : "score is null!";
	assert playerBullets != null : "playerBullets is null!";
	assert alienBullets != null : "alienBullets is null!";
	assert shipExplosion != null : "shipExplosion is null!";
	assert countDown != null : "countDown is null!";
	assert textFx != null : "textFx is null!";
    }

    public int current() {
	return state;
    }

    public void update() {
	// BETWEEN_LIVES_STATE
	if (state == PLAYING_STATE && score.getHealth() == 0) {
	    state = BETWEEN_LIVES_STATE;
	    playerBullets.killOnscreenBullets();
	    alienBullets.killOnscreenBullets();
	    shipExplosion.reset();
	    setStateTimer(stateTimes.BETWEEN_LIVES);
	    Tools.log("PLAYING_STATE ==> BETWEEN_LIVES_STATE");
	    // TODO aliens should move in BETWEEN_LIVES_STATE, but no shoot or
	    // collisions
	}

	// READY_STATE
	if (state == BETWEEN_LIVES_STATE && timeUpInState()) {
	    state = READY_STATE;
	    countDown.reset();
	    score.restoreHealth();
	    aliens.resetLivingAliens();
	    Tools.log("BETWEEN_LIVES_STATE ==> READY_STATE");
	}

	// WAIT_CLEAR_STATE
	if (state == PLAYING_STATE && aliens.levelCleared()) {
	    state = WAIT_CLEAR_STATE;
	    setStateTimer(stateTimes.WAIT_CLEAR);
	    // or wait for bullets
	    Tools.log("PLAYING_STATE ==> WAIT_CLEAR_STATE");
	}

	// LEVEL_CLEARED_STATE
	if (state == WAIT_CLEAR_STATE && timeUpInState()) {
	    state = LEVEL_CLEARED_STATE;
	    textFx.reset();
	    // TODO between state times are magic numbers
	    setStateTimer(stateTimes.LEVEL_CLEARED);
	    Tools.log("PLAYING_STATE ==> LEVEL_CLEARED_STATE");
	}

	// BONUS_MESSAGE_STATE
	if (state == LEVEL_CLEARED_STATE && timeUpInState()) {
	    state = BONUS_MESSAGE_STATE;
	    // score.addLevelBonus();
	    // score.clearLevelScore();
	    score.calculateBonus();
	    setStateTimer(stateTimes.BONUS_MESSAGE);
	    Tools.log("LEVEL_CLEARED_STATE ==> BONUS_MESSAGE_STATE");
	}

	// BONUS_PAYOUT_STATE
	if (state == BONUS_MESSAGE_STATE && timeUpInState()) {
	    state = BONUS_PAYOUT_STATE;
	    setStateTimer(stateTimes.BONUS_PAYOUT);
	    Tools.log("BONUS_MESSAGE_STATE ==> BONUS_PAYOUT_STATE");
	}

	// READY_STATE
	if (state == BONUS_PAYOUT_STATE && !score.bonusRemaining()
		&& timeUpInState()) {
	    state = READY_STATE;
	    score.clearLevelScore();
	    countDown.reset();
	    changeFormation();
	    Tools.log("BONUS_PAYOUT_STATE ==> READY_STATE");
	}

	// PLAYING_STATE
	if (state == READY_STATE && countDown.finished()) {
	    state = PLAYING_STATE;
	    Tools.log("READY_STATE ==> PLAYING_STATE");
	}

    }

    private boolean timeUpInState() {
	return System.currentTimeMillis() > stateTimer;
    }

    private void setStateTimer(int stateInterval) {
	stateTimer = System.currentTimeMillis() + stateInterval;
    }

    private void changeFormation() {
	aliens.newLevel(formations.get(formationsIndex++));
	if (formationsIndex == formations.size()) {
	    formationsIndex = 0;
	}
    }

}
