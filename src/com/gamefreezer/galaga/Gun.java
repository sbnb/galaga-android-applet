package com.gamefreezer.galaga;

public class Gun extends AllocGuard {

    public Gun(int minTimeBetweenBullets, int bulletMovement) {
	super();
	this.minTimeBetweenBullets = minTimeBetweenBullets;
	this.bulletMovement = bulletMovement;
    }

    public boolean shoot(Bullets bullets, Location startLocation) {
	boolean created = false;
	if (ready()) {
	    created = bullets.addNewBullet(startLocation, bulletMovement);
	    if (created) {
		recordFireTime();
	    }
	}
	return created;
    }

    public boolean ready() {
	return (System.currentTimeMillis() - lastFiredTime > minTimeBetweenBullets);
    }

    // this is for aliens to randomize the time between bullets
    public void setMinTimeBetweenBullets(int newTime) {
	minTimeBetweenBullets = newTime;
    }
    
    private void recordFireTime() {
	lastFiredTime = System.currentTimeMillis();
    }

    private long lastFiredTime = 0;
    private int minTimeBetweenBullets;
    private final int bulletMovement;
}
