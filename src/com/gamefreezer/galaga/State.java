package com.gamefreezer.galaga;

import java.util.List;

public class State {

    private Constants cfg;
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

    public State(Constants cfg, Aliens aliens,
	    List<Formation> formations, Score score, Bullets playerBullets,
	    Bullets alienBullets, AnimationFrames shipExplosion,
	    AnimationFrames countDown, AnimationFrames textFx) {
	this.cfg = cfg;
	this.aliens = aliens;
	this.formations = formations;
	this.score = score;
	this.playerBullets = playerBullets;
	this.alienBullets = alienBullets;
	this.shipExplosion = shipExplosion;
	this.countDown = countDown;
	this.textFx = textFx;
	enforcePreconditions();
	state = cfg.READY_STATE;
	setStateTimer(cfg.LEVEL_DELAY);
	changeFormation();
    }

    private void enforcePreconditions() {
	assert cfg != null : "cfg is null!";
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
	if (state == cfg.PLAYING_STATE && score.getHealth() == 0) {
	    state = cfg.BETWEEN_LIVES_STATE;
	    playerBullets.killOnscreenBullets();
	    alienBullets.killOnscreenBullets();
	    shipExplosion.reset();
	    setStateTimer(cfg.BETWEEN_LIVES_STATE_TIMER);
	    Game.log("PLAYING_STATE ==> BETWEEN_LIVES_STATE");
	    // TODO aliens should move in BETWEEN_LIVES_STATE, but no shoot or
	    // collisions
	}

	// READY_STATE
	if (state == cfg.BETWEEN_LIVES_STATE && timeUpInState()) {
	    state = cfg.READY_STATE;
	    countDown.reset();
	    score.restoreHealth();
	    aliens.resetLivingAliens();
	    Game.log("BETWEEN_LIVES_STATE ==> READY_STATE");
	}

	// WAIT_CLEAR_STATE
	if (state == cfg.PLAYING_STATE && aliens.levelCleared()) {
	    state = cfg.WAIT_CLEAR_STATE;
	    setStateTimer(500);
	    // or wait for bullets
	    Game.log("PLAYING_STATE ==> WAIT_CLEAR_STATE");
	}

	// LEVEL_CLEARED_STATE
	if (state == cfg.WAIT_CLEAR_STATE && timeUpInState()) {
	    state = cfg.LEVEL_CLEARED_STATE;
	    textFx.reset();
	    setStateTimer(1000);
	    Game.log("PLAYING_STATE ==> LEVEL_CLEARED_STATE");
	}

	// BONUS_MESSAGE_STATE
	if (state == cfg.LEVEL_CLEARED_STATE && timeUpInState()) {
	    state = cfg.BONUS_MESSAGE_STATE;
	    // score.addLevelBonus();
	    // score.clearLevelScore();
	    score.calculateBonus();
	    setStateTimer(3000);
	    Game.log("LEVEL_CLEARED_STATE ==> BONUS_MESSAGE_STATE");
	}

	// BONUS_PAYOUT_STATE
	if (state == cfg.BONUS_MESSAGE_STATE && timeUpInState()) {
	    state = cfg.BONUS_PAYOUT_STATE;
	    setStateTimer(3000);
	    Game.log("BONUS_MESSAGE_STATE ==> BONUS_PAYOUT_STATE");
	}

	// READY_STATE
	if (state == cfg.BONUS_PAYOUT_STATE && !score.bonusRemaining()
		&& timeUpInState()) {
	    state = cfg.READY_STATE;
	    score.clearLevelScore();
	    countDown.reset();
	    changeFormation();
	    Game.log("BONUS_PAYOUT_STATE ==> READY_STATE");
	}

	// PLAYING_STATE
	if (state == cfg.READY_STATE && countDown.finished()) {
	    state = cfg.PLAYING_STATE;
	    Game.log("READY_STATE ==> PLAYING_STATE");
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
