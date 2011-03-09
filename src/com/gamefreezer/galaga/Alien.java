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
    }

    // called by Sandbox.getAlien only, a non essential utility constructor
    // this is not chained, and shouldn't be, as it is a dev only convenience
    public Alien(SpriteCache spriteStore, Screen screen, Location location,
	    int dx, int dy, int baseDx, int baseDy, String imgPath, int points,
	    String renderTimes, String renderTicks) {

	super(spriteStore, screen, location, dx, dy, imgPath, renderTimes,
		renderTicks);
	this.setMaxSpeed(baseDx, baseDy);
	this.points = points;
    }

    @Override
    public void kill() {
	active = false;
	delink();
    }

    @Override
    public void setSolo(boolean solo) {
	this.solo = solo;
	delink();
    }

    public void flip() {
	movement.flip();
    }

    @Override
    protected boolean stopIfOffScreenTopOrBottom() {
	return false;
    }

    @Override
    protected void adjustIfOffScreenBottom() {
	if (offScreenBottom(screen.bottomMaskHeight())) {
	    movement.getLocation().setY(screen.playableTop());
	    diveComplete = true;
	}
    }

    @Override
    protected void killIfOffScreen() {
	// NOP - don't kill alien for going off screen
    }

    private void delink() {
	if (prev != null) {
	    prev.next = next;

	} else {
	    Aliens.FIRST = next; // this was FIRST, now next is
	}
	if (next != null) {
	    next.prev = prev;
	}
	prev = next = null;
    }

    public void setRelativeToAnchor(Location anchor) {
	relAnchorX = getLocation().getXAsFloat() - anchor.getXAsFloat();
	relAnchorY = getLocation().getYAsFloat() - anchor.getYAsFloat();
    }

    public float getLeftHomeSlot(Location anchor) {
	return anchor.getXAsFloat() + relAnchorX;
    }

    public float getRightHomeSlot(Location anchor) {
	return anchor.getXAsFloat() + relAnchorX + width;
    }

    public float getYHomeSlot(Location anchor) {
	return anchor.getYAsFloat() + relAnchorY;
    }
}
