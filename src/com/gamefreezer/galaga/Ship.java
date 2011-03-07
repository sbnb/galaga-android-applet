package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.*;
import static com.gamefreezer.galaga.Constants.SHIP_IMAGE;
import static com.gamefreezer.galaga.Constants.SHIP_MOVEMENT;

public class Ship extends Entity {

    public Ship(Location location) {
	super(location, 0, 0, SHIP_IMAGE, "", "");
    }

    /* The start location for bullets fired by the ship. */
    public Location getGunLocation(int bulletWidth) {
	gunLocation.moveTo(this.getLocation());
	// TODO bullet offset for ship should be configurable, or use ships
	// bullet width
	gunLocation.moveBy(width / 2 - bulletWidth / 2, height);
	return gunLocation;
    }

    public void shoot(Bullets bullets, Score score) {
	boolean firedBullet = gun.shoot(bullets, getGunLocation(bullets
		.bulletWidth()));
	if (firedBullet) {
	    score.incremementShotsFired();
	}
    }

    public boolean triggerDown() {
	return fireMode;
    }

    public void fireModeOn() {
	fireMode = true;
    }

    public void fireModeOff() {
	fireMode = false;
    }

    public void goingLeft() {
	setSpeed(LEFT_SPEED);
    }

    public void goingRight() {
	setSpeed(RIGHT_SPEED);
    }

    public void standingStill() {
	setSpeed(NO_SPEED);
    }

    private final Speed RIGHT_SPEED = new Speed(SHIP_MOVEMENT, 0);
    private final Speed LEFT_SPEED = new Speed(-SHIP_MOVEMENT, 0);
    private final Speed NO_SPEED = new Speed(0, 0);
    private boolean fireMode = false;
    private Gun gun = new Gun(MIN_TIME_BETWEEN_BULLETS, BULLET_MOVEMENT);
    Location gunLocation = new Location(movement.getLocation());
}
