package com.gamefreezer.galaga;

public class SoloAliens extends AllocGuard {

    private long nextFreeMovingAlienReleaseTime;
    private boolean freeMovingAllowed = true;
    private int lastDirection = 1;
    private final CartesianIntRange speedRange;
    private final IntRange releaseRange;

    public SoloAliens(CartesianIntRange speedRange, IntRange releaseRange,
	    int levelDelay) {
	super();
	this.speedRange = speedRange;
	this.releaseRange = releaseRange;
	nextFreeMovingAlienReleaseTime = System.currentTimeMillis()
		+ levelDelay + releaseRange.max;
    }

    public void setFreeMovingallowed(boolean freeMovingAllowed) {
	this.freeMovingAllowed = freeMovingAllowed;
    }

    public void realeaseAnAlien(Alien alien, int theLastDirection) {

	nextFreeMovingAlienReleaseTime = System.currentTimeMillis()
		+ releaseRange.random();
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
	return speedRange.y.random();
    }

    private int randomXSpeedComponent() {
	return speedRange.x.random() * lastDirection;
    }
}
