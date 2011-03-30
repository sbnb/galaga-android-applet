package com.gamefreezer.galaga;

public class SoloAliens extends AllocGuard {

    private long nextFreeMovingAlienReleaseTime;
    private boolean freeMovingAllowed = true;
    private int lastDirection = 1;
    private final CartesianIntRange speedRange;
    private final IntRange releaseRange;
    private Speed soloReturnSpeed;
    private final SoloController soloController;

    public SoloAliens(CartesianIntRange speedRange, IntRange releaseRange,
	    int levelDelay, Speed soloReturnSpeed, SoloController soloController) {
	super();
	this.speedRange = speedRange;
	this.releaseRange = releaseRange;
	nextFreeMovingAlienReleaseTime = System.currentTimeMillis()
		+ levelDelay + releaseRange.max;
	this.soloReturnSpeed = soloReturnSpeed;
	this.soloController = soloController;
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

    public Speed soloReturnSpeed() {
	return soloReturnSpeed;
    }

    public void adjust(int delta, Alien alien, Location target) {
	soloController.adjust(delta, alien, target);
    }

    public int staySolo() {
	return soloController.staySolo();
    }

    private int randomYSpeedComponent() {
	return speedRange.y.random();
    }

    private int randomXSpeedComponent() {
	return speedRange.x.random() * lastDirection;
    }
}
