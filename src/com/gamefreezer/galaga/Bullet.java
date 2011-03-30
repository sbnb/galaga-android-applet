package com.gamefreezer.galaga;

public class Bullet extends Entity {

    private int damage = 0;
    private Animation hitAnimation;

    public Bullet(SpriteCache spriteStore, Screen screen,
	    AnimationSource animationSource) {
	super(spriteStore, screen, new Location(0, 0), 0, 0, animationSource);
	stopIfPartiallyOffScreenTopOrBottom = false;
	killIfPartiallyOffScreen = false;
	killIfCompletelyOffScreen = true;
	this.kill();
    }

    public int damage() {
	return damage;
    }

    /* "Create" a new bullet by reusing s dead one. */
    public void reset(Location startPoint, int velocity) {
	this.moveTo(startPoint);
	this.regenerate();
	this.setSpeed(0, velocity);
    }

    /* Reset bullets visual appearance as well as position. */
    public void reset(Location startPoint, int velocity, Animation animation,
	    int damage) {
	reset(startPoint, velocity);
	this.damage = damage;
	this.animation = animation;
    }
}
