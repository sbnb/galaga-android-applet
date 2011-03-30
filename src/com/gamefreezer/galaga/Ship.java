package com.gamefreezer.galaga;

public class Ship extends Entity {

    private final Speed RIGHT_SPEED;
    private final Speed LEFT_SPEED;
    private final Speed NO_SPEED;
    private boolean fireMode;
    private Gun[] guns;
    private int currentGunIndex;
    Location gunLocation;

    public Ship(SpriteCache spriteStore, Screen screen,
	    AnimationSource animationSource, Gun[] guns, Speed rightSpeed,
	    Speed leftSpeed, Speed noSpeed) {
	super(spriteStore, screen, new Location(), 0, 0, animationSource);

	assert guns != null : "guns is null!";
	assert rightSpeed != null : "rightSpeed is null!";
	assert leftSpeed != null : "leftSpeed is null!";
	assert noSpeed != null : "noSpeed is null!";

	RIGHT_SPEED = rightSpeed;
	LEFT_SPEED = leftSpeed;
	NO_SPEED = noSpeed;
	fireMode = false;
	this.guns = guns;
	this.moveTo(screen.middleX(), screen.inGameBottom() - height - 3);
	gunLocation = new Location(movement.getLocation());
	currentGunIndex = 0;
    }

    /* The start location for bullets fired by the ship. */
    public Location getGunLocation() {
	gunLocation.moveTo(this.getLocation());
	gunLocation.moveBy(width / 2, 0);
	Dimension bullet = guns[currentGunIndex].getBulletDimensions();
	gunLocation.moveBy(-bullet.width / 2, 0);
	return gunLocation;
    }

    public void shoot(Bullets bullets, Score score) {
	boolean firedBullet = guns[currentGunIndex].shoot(bullets,
		getGunLocation());
	if (firedBullet) {
	    score.incremementShotsFired();
	}
    }

    public void coolGuns(int timeDelta) {
	for (Gun gun : guns) {
	    gun.cool(timeDelta);
	}
    }

    @Override
    public void draw(AbstractGraphics graphics) {
	guns[currentGunIndex].draw(graphics, this.getGunLocation());
	super.draw(graphics);
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

    // TODO create message for cycling weapons up and down
    public void cycleWeaponUp() {
	currentGunIndex++;
	if (currentGunIndex >= guns.length) {
	    currentGunIndex = 0;
	}
    }

    public void cycleWeaponDown() {
	currentGunIndex--;
	if (currentGunIndex < 0) {
	    currentGunIndex = guns.length - 1;
	}
    }
}
