package com.gamefreezer.galaga;

public class Alien extends Entity {

    public Alien next;
    public Alien prev;
    public float relAnchorX;
    public float relAnchorY;

    // main constructor called in Aliens()
    // reset.. methods in Formation handle the bulk of the Alien member settings
    public Alien(SpriteCache spriteStore, Screen screen, Speed targettingSpeed) {
	super(spriteStore, screen, targettingSpeed);
	stopIfPartiallyOffScreenTopOrBottom = false;
	stopIfPartiallyOffScreenLeftOrRight = true;
	killIfPartiallyOffScreen = false;
	killIfCompletelyOffScreen = false;
    }

    // called by Sandbox.getAlien only, a non essential utility constructor
    public Alien(SpriteCache spriteStore, Screen screen,
	    Location location,
	    // this is not chained, and shouldn't be, as it is a dev only
	    // convenience
	    int dx, int dy, int baseDx, int baseDy, int points,
	    String[] imgNames, int[] renderTimes) {

	super(spriteStore, screen, location, dx, dy, imgNames, renderTimes);
	this.setMaxSpeed(baseDx, baseDy);
	this.points = points;
	stopIfPartiallyOffScreenTopOrBottom = false;
	killIfPartiallyOffScreen = false;
    }

    @Override
    public void kill() {
	active = false;
    }

    @Override
    public void setSolo(boolean solo) {
	this.solo = solo;
    }

    public void flip() {
	movement.flip();
    }

    @Override
    protected void adjustIfCompletelyOffScreen() {
	if (completelyOffScreenBottom()) {
	    movement.getLocation().setY(screen.inGameTop() - height);
	    diveComplete = true;
	}
    }

    public void setRelativeToAnchor(Location anchor) {
	relAnchorX = getLocation().getXAsFloat() - anchor.getXAsFloat();
	relAnchorY = getLocation().getYAsFloat() - anchor.getYAsFloat();
    }

    public float getLeftHomeSlot(Location anchor) {
	return anchor.getXAsFloat() + relAnchorX;
    }

    public float getRightHomeSlot(Location anchor) {
	return anchor.getXAsFloat() + width + relAnchorX;
    }

    public float getYHomeSlot(Location anchor) {
	return anchor.getYAsFloat() + height + relAnchorY;
    }
}
