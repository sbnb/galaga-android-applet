package com.gamefreezer.galaga;

public class Movement extends AllocGuard {

    public Movement(Location location, Speed speed) {
	super();
	this.location = location;
	this.speed = speed;
	this.target = new Location();
	target.moveTo(-1f, -1f);
    }

    public Movement() {
	super();
	this.location = new Location();
	this.target = new Location();
	target.moveTo(-1f, -1f);
	this.speed = new Speed();
    }

    public static Movement NULL = new Movement();

    public void update(int timeDelta) {
	if (targetting()) {
	    Controller.adjustSpeed(getSpeed(), location, target);
	}
	location.moveBy(speed.getXDistanceTravelledIn(timeDelta), speed
		.getYDistanceTravelledIn(timeDelta));

    }

    public boolean targetting() {
	if (target.getX() > -1 && target.getY() > -1)
	    return true;
	return false;
    }

    public void rotate(Location rotationPoint, float theta) {
	location.rotate(rotationPoint, theta);
    }

    public Location getLocation() {
	return location;
    }

    public void setTarget(int x, int y) {
	target.moveTo(x, y);
    }

    public void setTarget(float x, float y) {
	target.moveTo(x, y);
    }

    public void setTarget(Location location) {
	target.moveTo(location);
    }

    public int getX() {
	return this.location.getX();
    }

    public float getXAsFloat() {
	return location.getXAsFloat();
    }

    public int getY() {
	return this.location.getY();
    }

    public float getYAsFloat() {
	return location.getYAsFloat();
    }

    public void moveTo(int x, int y) {
	location.moveTo(x, y);
    }

    public void moveTo(Location point) {
	location.moveTo(point);
    }

    public void moveBy(float x, float y) {
	location.moveBy(x, y);
    }

    public void moveBy(Location anAmount) {
	location.moveBy(anAmount);
    }

    public Speed getSpeed() {
	return speed;
    }

    public void setSpeed(Speed aSpeed) {
	speed.reset(aSpeed);
    }

    public void setSpeed(int dx, int dy) {
	speed.reset(dx, dy);
    }

    public void flip() {
	speed.flip();
    }

    @Override
    public String toString() {
	return "Movement: [" + location.toString() + "] [" + speed.toString()
		+ "]";
    }

    private Speed speed;
    private Location location;
    private Location target;
}
