package com.gamefreezer.galaga;

public class Bullet extends Entity {

    public Bullet(SpriteCache spriteStore, Location location, int movement,
	    String bulletImage) {
	super(spriteStore, location, 0, movement, bulletImage, "", "");
    }

    public Bullet(SpriteCache spriteStore, String bulletImage) {
	// create a dead bullet - effectively a null object
	this(spriteStore, new Location(0, 0), 0, bulletImage);
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
