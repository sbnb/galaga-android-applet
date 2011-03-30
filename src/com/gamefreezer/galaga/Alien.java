package com.gamefreezer.galaga;

public class Alien extends Entity {

    public Alien next;
    public Alien prev;
    public float relAnchorX;
    public float relAnchorY;
    private boolean solo = false;
    // TODO health should be read from config files, and may be different by
    // type of alien
    private int health = 100;

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
	    AnimationSource animationSource) {

	super(spriteStore, screen, location, dx, dy, animationSource);
	this.setMaxSpeed(baseDx, baseDy);
	this.points = points;
	stopIfPartiallyOffScreenTopOrBottom = false;
	stopIfPartiallyOffScreenLeftOrRight = true;
	killIfPartiallyOffScreen = false;
	killIfCompletelyOffScreen = false;
    }

    @Override
    public void kill() {
	active = false;
    }

    @Override
    public void regenerate() {
	active = true;
	health = 100;
    }

    public void setHealth(int health) {
	this.health = health;
    }

    public void decrementHealth(int amount) {
	this.health -= amount;
    }

    public int getHealth() {
	return health;
    }

    public void setSolo(boolean solo) {
	this.solo = solo;
    }

    public boolean isSolo() {
	return solo;
    }

    public boolean inFormation() {
	return active && !solo;
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
