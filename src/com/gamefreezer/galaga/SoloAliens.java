package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.*;

public class SoloAliens extends AllocGuard {

    public SoloAliens() {
	super();
    }

    public void setFreeMovingallowed(boolean freeMovingAllowed) {
	this.freeMovingAllowed = freeMovingAllowed;
    }

    public void realeaseAnAlien(Alien alien, int theLastDirection) {
	nextFreeMovingAlienReleaseTime = System.currentTimeMillis()
		+ Util.getRandom(MIN_TIME_BETWEEN_RELEASES,
			MAX_TIME_BETWEEN_RELEASES);
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
	return -Util.getRandom(FREE_ALIEN_Y_SPEED_MIN, FREE_ALIEN_Y_SPEED_MAX);
    }

    private int randomXSpeedComponent() {
	return Util.getRandom(FREE_ALIEN_X_SPEED_MIN, FREE_ALIEN_X_SPEED_MAX)
		* lastDirection;
    }

    private long nextFreeMovingAlienReleaseTime = System.currentTimeMillis()
	    + LEVEL_DELAY + MAX_TIME_BETWEEN_RELEASES;
    private boolean freeMovingAllowed = true;
    private int lastDirection = 1;
}
