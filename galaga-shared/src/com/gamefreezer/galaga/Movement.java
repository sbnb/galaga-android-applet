package com.gamefreezer.galaga;

public class Movement extends AllocGuard {

    private Speed speed;
    private Location location;
    private Location target;
    private final Speed targettingSpeed;

    public Movement(Location location, Speed speed) {
	this(location, speed, new Speed(0, 0));
    }

    public Movement(Location location, Speed speed, Speed targettingSpeed) {
	super();

	assert location != null : "location is null!";
	assert speed != null : "speed is null!";
	assert targettingSpeed != null : "targettingSpeed is null!";

	this.location = location;
	this.speed = speed;
	this.target = new Location();
	target.moveTo(-1f, -1f);
	this.targettingSpeed = targettingSpeed;
    }

    public void update(int timeDelta) {
	assert location != null : "location is null!";
	assert target != null : "target is null!";
	if (targetting()) {
	    adjustSpeed(getSpeed(), location, target);
	}
	location.moveBy(speed.getXDistanceTravelledIn(timeDelta), speed
		.getYDistanceTravelledIn(timeDelta));

    }

    public void adjustSpeed(Speed speed, Location src, Location target) {
	adjustSpeed(speed, src, target.getX(), target.getY());
    }

    // !! changes the value of parameter speed
    public void adjustSpeed(Speed speed, Location src, int targetX, int targetY) {

	final int xDist = Math.abs(targetX - src.getX());
	final int yDist = Math.abs(targetY - src.getY());

	// take min of x,y and max speeds dx, dy adjusted for timeDelta
	int x = Math.min(xDist, targettingSpeed.getDx());
	x = Math.max(x, targettingSpeed.getDx() / 2);
	if (xDist < 2)
	    x = 0;
	int y = Math.min(yDist, targettingSpeed.getDy());
	y = Math.max(y, targettingSpeed.getDy() / 2);
	if (yDist < 2)
	    y = 0;

	// negate if alien.x > target.x
	if (src.getX() > targetX) {
	    x = -x;
	}
	// negate if alien.y > target.y
	if (src.getY() > targetY) {
	    y = -y;
	}
	speed.reset(x, y);
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

    public void moveBy(float xDelta, float yDelta) {
	location.moveBy(xDelta, yDelta);
    }

    public void moveBy(Location delta) {
	location.moveBy(delta);
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
}
