package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.*;

public class Controller extends AllocGuard {

    public Controller() {
	super();
    }

    public void adjust(int delta, Alien alien, Location target) {
	if (atLeftEdge(alien) || atRightEdge(alien)) {
	    alien.flip(); // change direction
	}
	if (readyForTarget(alien, target)) {
	    alien.setTarget(target);
	} else if (alien.targetting()) {
	    alien.setTarget(target);
	    alien.move(delta);
	    int epsilon = 10;
	    if (nearEnoughToTarget(alien, target, epsilon)) {
		alien.moveTo(target);
		alien.setTarget(-1, -1);
		alien.setSolo(false);
		alien.setDiveComplete(false);
	    }
	} else {
	    alien.move(delta);
	}
    }

    private boolean nearEnoughToTarget(Alien alien, Location target, int epsilon) {
	return Math.abs(target.getXAsFloat() - alien.getXAsFloat()) < epsilon
	    && Math.abs(target.getYAsFloat() - alien.getYAsFloat()) < epsilon;
    }

    private boolean readyForTarget(Alien alien, Location target) {
	return alien.diveComplete && !alien.targetting()
		&& target.getYAsFloat() > STAY_SOLO;
    }

    private static Speed maxSpeed = new Speed(RETURN_SPEED_X, RETURN_SPEED_Y);

    public static void adjustSpeed(Speed speed, Location src,
	    Location target) {
	adjustSpeed(speed, src, target.getX(), target.getY());
    }

    public static void adjustSpeed(Speed speed, Location src,
	    int targetX, int targetY) {

	int xDist = Math.abs(targetX - src.getX());
	int yDist = Math.abs(targetY - src.getY());

	// take min of x,y and max speeds dx, dy adjusted for timeDelta
	int x = Math.min(xDist, maxSpeed.getDx());
	x = Math.max(x, maxSpeed.getDx() / 2);
	if (xDist < 2)
	    x = 0;
	int y = Math.min(yDist, maxSpeed.getDy());
	y = Math.max(y, maxSpeed.getDy() / 2);
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

    private boolean atRightEdge(Alien alien) {
	return Screen.right() == alien.rightEdge();
    }

    private boolean atLeftEdge(Alien alien) {
	return Screen.left() == alien.getX();
    }
}
