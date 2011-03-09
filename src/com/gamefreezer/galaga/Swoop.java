//package com.gamefreezer.galaga;
//
//public class Swoop extends AllocGuard {
//
//    public Swoop(Alien alien) {
//	super();
//	this.alien = alien;
//	init();
//    }
//
//    public void init() {
//	radius = Util.getRandom(20, 100);
//	pointOfRotation = getPointOfRotation();
//	// directionOfRotation = directionOfRotation * -1;
//	targetRotation = (float) (Math.PI / 2);
//	totalRotation = 0;
//    }
//
//    public void update(int timeDelta) {
//	// int speed = alien.getBaseDx() + 50;
//	int speed = alien.getMaxSpeed().getDx() + 50;
//	float displacement = speed * timeDelta / 1000;
//	float theta = displacement / radius * directionOfRotation;
//	// 3. calculate x' and y' (perform rotation)
//	if (totalRotation < targetRotation) {
//	    alien.rotate(pointOfRotation, theta);
//	    totalRotation += Math.abs(theta);
//	} else {
//	    init();
//	}
//	// check if swoop completed - mark if so
//
//    }
//
//    private Location getPointOfRotation() {
//	if (lastMove == LEFT_CLIMB) {
//	    return getLeftDiveRotationPoint();
//	}
//	if (lastMove == RIGHT_CLIMB) {
//	    return getRightDiveRotationPoint();
//	}
//	if (lastMove == RIGHT_DIVE || lastMove == LEFT_DIVE) {
//	    if (screenLeft()) {
//		return getRightClimbRotationPoint();
//	    }
//	    return getLeftClimbRotationPoint();
//	}
//	return getRightDiveRotationPoint();
//    }
//
//    private boolean screenLeft() {
//	return alien.getX() < Screen.middleHorizontal();
//    }
//
//    private Location getRightClimbRotationPoint() {
//	lastMove = RIGHT_CLIMB;
//	directionOfRotation = LEFT;
//	return new Location(alien.getX() + radius, alien.getY());
//    }
//
//    private Location getLeftClimbRotationPoint() {
//	lastMove = LEFT_CLIMB;
//	directionOfRotation = RIGHT;
//	return new Location(alien.getX() - radius, alien.getY());
//    }
//
//    private Location getRightDiveRotationPoint() {
//	lastMove = RIGHT_DIVE;
//	directionOfRotation = RIGHT;
//	return new Location(alien.getX(), alien.getY() - radius);
//    }
//
//    private Location getLeftDiveRotationPoint() {
//	lastMove = LEFT_DIVE;
//	directionOfRotation = LEFT;
//	return new Location(alien.getX(), alien.getY() - radius);
//    }
//
//    private Alien alien;
//    private int radius;
//    private Location pointOfRotation;
//    private int directionOfRotation = -1;
//    private float targetRotation;
//    private float totalRotation = 0;
//
//    private int RIGHT_CLIMB = 0;
//    private int LEFT_CLIMB = 1;
//    private int RIGHT_DIVE = 2;
//    private int LEFT_DIVE = 3;
//    private int lastMove = RIGHT_DIVE;
//    private int LEFT = 1;
//    private int RIGHT = -1;
// }
