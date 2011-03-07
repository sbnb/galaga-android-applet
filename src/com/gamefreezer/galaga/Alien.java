package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.*;

public class Alien extends Entity {

    public Alien next;
    public Alien prev;

    public Alien(Location location, int dx, int dy, int baseDx, int baseDy,
	    String imgPath, int points, String renderTimes, String renderTicks) {
	super(location, dx, dy, imgPath, renderTimes, renderTicks);
	this.setMaxSpeed(baseDx, baseDy);
	this.points = points;
    }

    public Alien() {
	super();
    }

    // @SuppressWarnings("hiding")
    public static final Alien NULL = new Alien();

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
	if (offScreenBottom(BOTTOM_MASK_HEIGHT)) {
	    movement.getLocation().setY(Screen.playableTop());
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

    public float relAnchorX;
    public float relAnchorY;
}
