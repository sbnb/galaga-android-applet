package com.gamefreezer.galaga;

public class Gun extends AllocGuard {

    private long nextFireTime;
    private final int bulletMovement;
    private IntRange bulletInterval;

    public Gun(IntRange bulletInterval, int bulletMovement) {
	super();
	this.bulletInterval = bulletInterval;
	this.bulletMovement = bulletMovement;
    }

    public Gun(int fixedBulletInterval, int bulletMovement) {
	this(new IntRange(fixedBulletInterval, fixedBulletInterval),
		bulletMovement);
    }

    public boolean shoot(Bullets bullets, Location startLocation) {
	boolean created = false;
	if (ready()) {
	    created = bullets.addNewBullet(startLocation, bulletMovement);
	    if (created) {
		calculateNextAllowableFireTime();
	    }
	}
	return created;
    }

    public boolean ready() {
	return System.currentTimeMillis() > nextFireTime;
    }

    private void calculateNextAllowableFireTime() {
	nextFireTime = System.currentTimeMillis() + bulletInterval.random();
    }
}
