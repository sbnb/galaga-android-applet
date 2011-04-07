package com.gamefreezer.galaga;

public class Bullet extends Entity {

    private int damage = 0;
    private Gun gun;

    public Bullet(SpriteCache spriteStore, Screen screen,
	    AnimationSource animationSource) {
	super(spriteStore, screen, new Location(0, 0), 0, 0, animationSource);
	stopIfPartiallyOffScreenTopOrBottom = false;
	killIfPartiallyOffScreen = false;
	killIfCompletelyOffScreen = true;
	this.kill();
	gun = null;
    }

    public int getDamage() {
	return damage;
    }

    /* "Create" a new bullet by reusing a dead one. */
    public void reset(Location startPoint, int yVelocity) {
	moveTo(startPoint);
	regenerate();
	setSpeed(0, yVelocity);
    }

    /* Reset bullets visual appearance as well as position. */
    public void reset(Gun gun, Location startPoint, int yVelocity,
	    Animation bulletAnim, int damage) {
	reset(startPoint, yVelocity);
	this.gun = gun;
	animation = bulletAnim;
	this.damage = damage;
    }

    @Override
    public void kill() {
	super.kill();
	animation.returnToPool();
    }

    public Animation getHitAnimation() {
	return gun.getHitAnimation();
    }
}
