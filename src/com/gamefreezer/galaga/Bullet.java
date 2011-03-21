package com.gamefreezer.galaga;

public class Bullet extends Entity {

    public Bullet(SpriteCache spriteStore, Screen screen, String[] imageNames,
	    int[] bulletTimes) {
	super(spriteStore, screen, new Location(0, 0), 0, 0, imageNames,
		bulletTimes);
	stopIfPartiallyOffScreenTopOrBottom = false;
	killIfPartiallyOffScreen = false;
	killIfCompletelyOffScreen = true;
	this.kill();
    }

    public void reset(Location startPoint, int velocity) {
	this.moveTo(startPoint);
	this.regenerate();
	this.setSpeed(0, velocity);
    }
}
