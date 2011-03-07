package com.gamefreezer.galaga;

/*
 * Speed is measured in pixels per second.
 */

public class Speed extends AllocGuard {

    Speed(Speed speed) {
	super();
	dx = speed.getDx();
	dy = speed.getDy();
    }

    Speed(int dx, int dy) {
	super();
	this.dx = dx;
	this.dy = dy;
    }

    public Speed() {
	super();
    }

    public static final Speed NULL = new Speed();

    public int getDx() {
	return dx;
    }

    public int getDy() {
	return dy;
    }

    public void reset(int dx, int dy) {
	this.dx = dx;
	this.dy = dy;
    }

    public void reset(Speed speed) {
	this.dx = speed.getDx();
	this.dy = speed.getDy();
    }

    public void flip() {
	dx *= -1;
    }

    public void incrementDy(int pixelsPerSecond) {
	dy += pixelsPerSecond;
    }

    public void incrementDx(int pixelsPerSecond) {
	dx += pixelsPerSecond;
    }

    public float getXDistanceTravelledIn(int timeDelta) {
	float dxPixelsPerMilli = (float) dx / (float) 1000;
	float xDistance = dxPixelsPerMilli * timeDelta;
	return xDistance;
    }

    public float getYDistanceTravelledIn(int timeDelta) {
	float dyPixelsPerMilli = (float) dy / (float) 1000;
	float yDistance = dyPixelsPerMilli * timeDelta;
	return yDistance;
    }

    @Override
    public String toString() {
	return "Speed: dx: " + dx + " dy: " + dy;
    }

    private int dx = 0;
    private int dy = 0;
}
