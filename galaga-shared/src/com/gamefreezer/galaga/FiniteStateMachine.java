package com.gamefreezer.galaga;

import java.util.List;

public class FiniteStateMachine {

    public enum State {
	INTRO, BETWEEN_LIVES, DEAD, GAME_OVER, PLAYING, FROZEN, //
	LEVEL_CLEARED, BONUS_MESSAGE, READY, WAIT_CLEAR, BONUS_PAYOUT
    }

    private long timer;
    private Score score;
    private Bullets playerBullets;
    private Bullets alienBullets;
    private Animation shipExplosion;
    private FixedAnimation countDown;
    private FixedAnimation levelComplete;
    private Aliens aliens;
    private List<Formation> formations;
    private int formationsIndex = 0;
    private StateTimes stateTimes;
    private State currentState;

    public FiniteStateMachine(StateTimes stateTimes, Aliens aliens,
	    List<Formation> formations, Score score, Bullets playerBullets,
	    Bullets alienBullets, Animation shipExplosion,
	    FixedAnimation countDown, FixedAnimation levelComplete) {
	this.stateTimes = stateTimes;
	this.aliens = aliens;
	this.formations = formations;
	this.score = score;
	this.playerBullets = playerBullets;
	this.alienBullets = alienBullets;
	this.shipExplosion = shipExplosion;
	this.countDown = countDown;
	this.levelComplete = levelComplete;
	enforcePreconditions();
	currentState = State.READY;
	setStateTimer(stateTimes.LEVEL_DELAY);
	changeFormation();
    }

    private void enforcePreconditions() {
	assert stateTimes != null : "stateTimes is null!";
	assert aliens != null : "aliens is null!";
	assert formations != null : "formations is null!";
	assert score != null : "score is null!";
	assert playerBullets != null : "playerBullets is null!";
	assert alienBullets != null : "alienBullets is null!";
	assert shipExplosion != null : "shipExplosion is null!";
	assert countDown != null : "countDown is null!";
	assert levelComplete != null : "levelComplete is null!";
    }

    public State currentState() {
	return currentState;
    }

    public void update() {
	// BETWEEN_LIVES_STATE
	if (currentState == State.PLAYING && score.getHealth() == 0) {
	    currentState = State.BETWEEN_LIVES;
	    playerBullets.killOnscreenBullets();
	    alienBullets.killOnscreenBullets();
	    shipExplosion.reset();
	    setStateTimer(stateTimes.BETWEEN_LIVES);
	    Tools.log("PLAYING_STATE ==> BETWEEN_LIVES_STATE");
	}

	// READY_STATE
	if (currentState == State.BETWEEN_LIVES && timeUpInState()) {
	    currentState = State.READY;
	    countDown.reset();
	    score.restoreHealth();
	    aliens.resetLivingAliens();
	    // Tools.log("BETWEEN_LIVES_STATE ==> READY_STATE");
	}

	// WAIT_CLEAR_STATE
	if (currentState == State.PLAYING && aliens.levelCleared()) {
	    currentState = State.WAIT_CLEAR;
	    setStateTimer(stateTimes.WAIT_CLEAR);
	    // or wait for bullets
	    // Tools.log("PLAYING_STATE ==> WAIT_CLEAR_STATE");
	}

	// LEVEL_CLEARED_STATE
	if (currentState == State.WAIT_CLEAR && timeUpInState()) {
	    currentState = State.LEVEL_CLEARED;
	    levelComplete.reset();
	    setStateTimer(stateTimes.LEVEL_CLEARED);
	    // Tools.log("PLAYING_STATE ==> LEVEL_CLEARED_STATE");
	}

	// BONUS_MESSAGE_STATE
	if (currentState == State.LEVEL_CLEARED && timeUpInState()) {
	    currentState = State.BONUS_MESSAGE;
	    // score.addLevelBonus();
	    // score.clearLevelScore();
	    score.calculateBonus();
	    setStateTimer(stateTimes.BONUS_MESSAGE);
	    // Tools.log("LEVEL_CLEARED_STATE ==> BONUS_MESSAGE_STATE");
	}

	// BONUS_PAYOUT_STATE
	if (currentState == State.BONUS_MESSAGE && timeUpInState()) {
	    currentState = State.BONUS_PAYOUT;
	    setStateTimer(stateTimes.BONUS_PAYOUT);
	    // Tools.log("BONUS_MESSAGE_STATE ==> BONUS_PAYOUT_STATE");
	}

	// READY_STATE
	if (currentState == State.BONUS_PAYOUT && !score.bonusRemaining()
		&& timeUpInState()) {
	    currentState = State.READY;
	    score.clearLevelScore();
	    countDown.reset();
	    changeFormation();
	    // Tools.log("BONUS_PAYOUT_STATE ==> READY_STATE");
	}

	// PLAYING_STATE
	if (currentState == State.READY && countDown.isFinished()) {
	    currentState = State.PLAYING;
	    // Tools.log("READY_STATE ==> PLAYING_STATE");
	}

    }

    private boolean timeUpInState() {
	return System.currentTimeMillis() > timer;
    }

    private void setStateTimer(int stateInterval) {
	timer = System.currentTimeMillis() + stateInterval;
    }

    private void changeFormation() {
	aliens.newLevel(formations.get(formationsIndex++));
	if (formationsIndex == formations.size()) {
	    formationsIndex = 0;
	}
    }
}
