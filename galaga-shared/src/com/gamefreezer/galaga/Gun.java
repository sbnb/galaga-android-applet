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
    private Animation firingAnimation;
    private AnimationPool bulletAnimations;
    private AnimationPool hitAnimations;
    private Location adjGunCentre;
    private Animation hudAnimation;

    public Gun(int bulletSpeed, int rateOfFire, int damage, int heatIncrement,
	    int cooling, AnimationPool bulletAnimations,
	    AnimationPool hitAnimations, Animation firingAnimation,
	    Animation hudAnimation, StatusBar statusBar) {
	this.bulletSpeed = bulletSpeed;
	this.rateOfFire = rateOfFire;
	this.damage = damage;
	this.heatIncrement = heatIncrement;
	this.cooling = cooling;
	heatDegradation = 100.0f;
	overheat = false;
	this.bulletAnimations = bulletAnimations;
	this.hitAnimations = hitAnimations;
	this.firingAnimation = firingAnimation;
	this.hudAnimation = hudAnimation;
	this.statusBar = statusBar;
	adjGunCentre = new Location();
    }

    public boolean ready() {
	return System.currentTimeMillis() >= nextFireTime && !overheat;
    }

    public boolean shoot(Bullets bullets, Location gunLocation) {
	boolean created = false;
	if (ready()) {
	    // gunLocation is the middle of the barrel (nearest pixel)
	    adjGunCentre.moveTo(gunLocation);
	    adjGunCentre.moveBy(-getBulletDimensions().width / 2, 0);

	    created = bullets.addNewBullet(this, adjGunCentre, bulletSpeed,
		    bulletAnimations.get(), damage);
	    if (created) {
		heatDegradation -= heatIncrement;
		applyHeatDegradation();
		calculateNextAllowableFireTime();
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

    public float getHeatDegradation() {
	return heatDegradation;
    }

    public void draw(AbstractGraphics graphics, Location location) {
	// draw the gun itself perhaps
	// draw firing animation if appropriate
	if (!firingAnimation.isFinished()) {
	    firingAnimation.draw(graphics, location.getX()
		    - firingAnimation.width() / 2, location.getY()
		    - firingAnimation.height() * 2 / 3);
	}
	statusBar.draw(graphics);
    }

    public Animation getHitAnimation() {
	// System.out.println("Gun.getHitAnimation(): getting from pool "
	// + hitAnimations.remaining());
	return hitAnimations.get();
    }

    public Dimension getBulletDimensions() {
	return bulletAnimations.getDimensions();
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

    public Animation getHudAnimation() {
	return hudAnimation;
    }
}
