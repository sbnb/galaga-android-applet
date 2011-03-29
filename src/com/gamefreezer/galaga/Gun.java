package com.gamefreezer.galaga;

public class Gun extends AllocGuard {

    private long nextFireTime;
    private final int bulletSpeed;
    private int rateOfFire;
    private int damage;
    private float heatDegradation;
    private int heatIncrement;
    private int cooling;
    private StatusBar statusBar;
    private boolean overheat;
    private Animation bulletAnimation;
    private Animation firingAnimation;

    public Gun(int bulletSpeed, int rateOfFire, int damage, int heatIncrement,
	    int cooling, Animation bulletAnimation, Animation firingAnimation,
	    StatusBar statusBar) {
	this.bulletSpeed = bulletSpeed;
	this.rateOfFire = rateOfFire;
	this.damage = damage;
	this.heatIncrement = heatIncrement;
	this.cooling = cooling;
	heatDegradation = 100.0f;
	overheat = false;
	this.bulletAnimation = bulletAnimation;
	this.firingAnimation = firingAnimation;
	this.statusBar = statusBar;
    }

    public boolean ready() {
	return System.currentTimeMillis() >= nextFireTime && !overheat;
    }

    public boolean shoot(Bullets bullets, Location gunLocation) {
	boolean created = false;
	if (ready()) {
	    // TODO account for width and height of this bullet
	    // startLocation is the middle of the barrel
	    created = bullets.addNewBullet(gunLocation, bulletSpeed,
		    bulletAnimation, damage);
	    if (created) {
		heatDegradation -= heatIncrement;
		applyHeatDegradation();
		calculateNextAllowableFireTime();
		// TODO display firing animation now
		firingAnimation.reset();
	    }
	}
	return created;
    }

    public void cool(int timeDelta) {
	float amount = (float) cooling * timeDelta / 1000;
	heatDegradation += amount;
	applyHeatDegradation();
    }

    public void draw(AbstractGraphics graphics, Location location) {
	// draw the gun itself perhaps
	// draw firing animation if appropriate
	if (!firingAnimation.finished()) {
	    firingAnimation.draw(graphics, location.getX(), location.getY());
	}
	statusBar.draw(graphics);
    }

    private void applyHeatDegradation() {
	if (heatDegradation <= 0) {
	    heatDegradation = 0;
	    overheat = true;
	    statusBar.setSecondaryMode();
	}
	if (heatDegradation >= 100) {
	    heatDegradation = 100;
	    overheat = false;
	    statusBar.setPrimaryMode();
	}
	statusBar.setValue(heatDegradation);
    }

    private void calculateNextAllowableFireTime() {
	nextFireTime = System.currentTimeMillis() + rateOfFire;
    }

    public Dimension getBulletDimensions() {
	return bulletAnimation.getDimensions();
    }
}
