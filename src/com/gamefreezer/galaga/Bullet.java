package com.gamefreezer.galaga;

public class Bullet extends Entity {

    public Bullet(SpriteCache spriteStore, Screen screen, String[] imageNames,
	    int[] bulletTimes) {
	super(spriteStore, screen, new Location(0, 0), 0, 0, imageNames,
		bulletTimes);
	this.kill();
    }

    @Override
    protected boolean stopIfOffScreenTopOrBottom() {
	return false;
    }

    public void reset(Location startPoint, int velocity) {
	this.moveTo(startPoint);
	this.regenerate();
	this.setSpeed(0, velocity);
    }
}
