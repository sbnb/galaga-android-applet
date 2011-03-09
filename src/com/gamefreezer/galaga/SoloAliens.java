package com.gamefreezer.galaga;

public class SoloAliens extends AllocGuard {

    private Constants cfg;
    private long nextFreeMovingAlienReleaseTime;
    private boolean freeMovingAllowed = true;
    private int lastDirection = 1;

    public SoloAliens(Constants cfg) {
	super();
	this.cfg = cfg;
	nextFreeMovingAlienReleaseTime = System.currentTimeMillis()
		+ cfg.LEVEL_DELAY + cfg.MAX_TIME_BETWEEN_RELEASES;
    }

    public void setFreeMovingallowed(boolean freeMovingAllowed) {
	this.freeMovingAllowed = freeMovingAllowed;
    }

    public void realeaseAnAlien(Alien alien, int theLastDirection) {
	nextFreeMovingAlienReleaseTime = System.currentTimeMillis()
		+ Util.getRandom(cfg.MIN_TIME_BETWEEN_RELEASES,
			cfg.MAX_TIME_BETWEEN_RELEASES);
	if (!freeMovingAllowed || alien == null) {
	    return;
	}
	this.lastDirection = theLastDirection;
	alien.setSolo(true);
	alien.setDiveComplete(false);
	alien.setSpeed(randomXSpeedComponent(), randomYSpeedComponent());
    }

    public boolean timeForRelease() {
	return System.currentTimeMillis() > nextFreeMovingAlienReleaseTime;
    }

    private int randomYSpeedComponent() {
	return Util.getRandom(cfg.FREE_ALIEN_Y_SPEED_MIN,
		cfg.FREE_ALIEN_Y_SPEED_MAX);
    }

    private int randomXSpeedComponent() {
	return Util.getRandom(cfg.FREE_ALIEN_X_SPEED_MIN,
		cfg.FREE_ALIEN_X_SPEED_MAX)
		* lastDirection;
    }
}
