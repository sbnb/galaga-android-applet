package com.gamefreezer.galaga;

public class Location extends AllocGuard {

    private float xFloat = 0.0f;
    private float yFloat = 0.0f;

    public Location(int x, int y) {
	super();
	this.xFloat = x;
	this.yFloat = y;
    }

    public Location(Location aLocation) {
	super();
	xFloat = aLocation.getXAsFloat();
	yFloat = aLocation.getYAsFloat();
    }

    public Location() {
	super();
    }

    public static final Location NULL = new Location(0, 0);

    public int getX() {
	return Math.round(xFloat);
    }

    public int getY() {
	return Math.round(yFloat);
    }

    public void setX(int x) {
	xFloat = x;
    }

    public void setY(int y) {
	yFloat = y;
    }

    public void setX(float x) {
	xFloat = x;
    }

    public void setY(float y) {
	yFloat = y;
    }

    public void moveBy(float xMovement, float yMovement) {
	xFloat += xMovement;
	yFloat += yMovement;
    }

    public void moveBy(Location anAmount) {
	xFloat += anAmount.getXAsFloat();
	yFloat += anAmount.getYAsFloat();
    }

    public void moveTo(int x, int y) {
	xFloat = x;
	yFloat = y;
    }

    public void moveTo(float x, float y) {
	xFloat = x;
	yFloat = y;
    }

    public void moveTo(Location point) {
	xFloat = point.getXAsFloat();
	yFloat = point.getYAsFloat();
    }

    public float getXAsFloat() {
	return xFloat;
    }

    public float getYAsFloat() {
	return yFloat;
    }

    public void rotate(Location rotationPoint, float radians) {
	float x = xFloat - rotationPoint.getX();
	float y = yFloat - rotationPoint.getY();
	float xComponent = rotateX(radians, x, y);
	float yComponent = rotateY(radians, x, y);
	xFloat = xComponent + rotationPoint.getX();
	yFloat = yComponent + rotationPoint.getY();
    }

    private float rotateY(float radians, float x, float y) {
	return (float) (x * Math.sin(radians) + y * Math.cos(radians));
    }

    private float rotateX(float theta, float x, float y) {
	return (float) (x * Math.cos(theta) - y * Math.sin(theta));
    }

    /* Assumes difference.x&y > this.x&y, but no check made. */
    public void minus(Location difference) {
	xFloat -= difference.xFloat;
	yFloat -= difference.yFloat;
    }

    public void plus(Location addition) {
	xFloat += addition.xFloat;
	yFloat += addition.yFloat;
    }

    @Override
    public String toString() {
	return "Location: x: " + getX() + " y: " + getY() + " fx: " + xFloat
		+ " fy: " + yFloat;
    }

}
