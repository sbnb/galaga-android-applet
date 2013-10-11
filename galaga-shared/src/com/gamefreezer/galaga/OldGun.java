package com.gamefreezer.galaga;

public class OldGun extends AllocGuard {

    private long nextFireTime;
    private final int bulletMovement;
    private IntRange bulletInterval;

    public OldGun(IntRange bulletInterval, int bulletMovement) {
	super();
	this.bulletInterval = bulletInterval;
	this.bulletMovement = bulletMovement;
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
