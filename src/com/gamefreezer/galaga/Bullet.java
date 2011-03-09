package com.gamefreezer.galaga;

public class Bullet extends Entity {

    public Bullet(SpriteCache spriteStore, Screen screen, Location location,
	    int movement, String imageName) {
	super(spriteStore, screen, location, 0, movement, imageName, "", "");
	this.kill();
    }

    public Bullet(SpriteCache spriteStore, Screen screen, String imageName) {
	this(spriteStore, screen, new Location(0, 0), 0, imageName);
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
