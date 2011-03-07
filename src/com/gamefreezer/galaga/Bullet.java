package com.gamefreezer.galaga;

public class Bullet extends Entity {

    public Bullet(Location location, int movement, String bulletImage) {
	super(location, 0, movement, bulletImage, "", "");
    }

    public Bullet(String bulletImage) {
	// create a dead bullet - effectively a null object
	this(new Location(0, 0), 0, bulletImage);
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
