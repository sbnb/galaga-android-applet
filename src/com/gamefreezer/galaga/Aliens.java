package com.gamefreezer.galaga;

import java.util.SortedMap;

public class Aliens extends AllocGuard {

    public static Alien FIRST = null;
    private Alien[] aliens;
    private Formation formation;
    private Speed speed;
    private Location moveDist;
    private int currentMaxSpeed = 0;
    private float rightMost;
    private float leftMost;
    private float bottomMost;
    private float distRight;
    private float distLeft;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingDown;
    private int livingAliens;
    private int inFormationAliens;

    private static final int RIGHT = 1;
    private static final int LEFT = -1;
    private float targetY;
    private Controller controller;
    private SoloAliens freeMovingAliens;
    private Gun gun;
    private Location anchor = new Location();
    private Constants cfg;
    private final Alien nullAlien;

    public Aliens(SpriteCache spriteCache, Constants cfg, Speed targettingSpeed) {
	super();
	this.cfg = cfg;
	nullAlien = new Alien(spriteCache, cfg.SCREEN, targettingSpeed);
	speed = new Speed();
	moveDist = new Location();
	controller = new Controller(cfg.SCREEN, cfg.STAY_SOLO);
	gun = new Gun(cfg.MIN_TIME_BETWEEN_ALIEN_BULLETS,
		cfg.ALIEN_BULLET_MOVEMENT);
	freeMovingAliens = new SoloAliens(cfg);

	aliens = new Alien[cfg.MAX_FORMATION];
	for (int i = 0; i < cfg.MAX_FORMATION; i++) {
	    aliens[i] = new Alien(spriteCache, cfg.SCREEN, targettingSpeed);
	    aliens[i].kill();
	}
    }

    // doubly link all aliens in a formation (regardless of state, living or
    // dead)
    public void link() {
	FIRST = aliens[0];
	Alien last = null;
	for (int i = 0; i < formation.size(); i++) {
	    aliens[i].prev = last;
	    aliens[i].next = null;
	    if (last != null) {
		last.next = aliens[i];
	    }
	    last = aliens[i];
	}
    }

    // insert b after a
    public void insertAfter(Alien a, Alien b) {
	if (a != null) {
	    b.next = a.next; // a may be null
	    b.prev = a;
	    a.next = b; // but a may be null
	    if (b.next != null) {
		b.next.prev = b; // but b.next may be null
	    }
	} else {
	    // inserting first elmt in list
	    b.next = null;
	    b.prev = null;
	    FIRST = b;
	}
	// TODO if need to insert before (say first in existing list) need to
	// write that method
    }

    public void newLevel(Formation aFormation) {
	this.formation = aFormation;
	aFormation.createAliens(aliens);
	livingAliens = aFormation.size();
	currentMaxSpeed = aFormation.getAlienSpeeds().get(100);
	speed.reset(currentMaxSpeed, 0);
	link();
	anchor.moveTo(aliens[0].getLocation());
	setRelativeToAnchor();
    }

    private void setRelativeToAnchor() {
	for (int i = 0; i < formation.size(); i++) {
	    aliens[i].setRelativeToAnchor(anchor);
	}
    }

    // Send existing aliens back to formation at top of screen
    public void resetLivingAliens() {
	formation.resetLivingAliens(aliens);
	formation.setAnchor(anchor);
	livingAliens = formation.size();
	speed.reset(currentMaxSpeed, 0);
    }

    public Alien[] getArray() {
	return aliens;
    }

    public int size() {
	return formation.size();
    }

    public Speed getSpeed() {
	return speed;
    }

    public SortedMap<Integer, Integer> getAlienSpeeds() {
	return formation.getAlienSpeeds();
    }

    public boolean levelCleared() {
	return livingAliens() == 0;
    }

    public void draw(AbstractGraphics graphics) {
	for (int i = 0; i < formation.size(); i++) {
	    aliens[i].draw(graphics);
	}
    }

    public void setFreeMovingAllowed(boolean freeMovingAllowed) {
	freeMovingAliens.setFreeMovingallowed(freeMovingAllowed);
    }

    public void shoot(Bullets bullets) {
	if (gun.ready()) {
	    gun.shoot(bullets, getShooterLocation());
	    gun.setMinTimeBetweenBullets(Util.getRandom(
		    cfg.MIN_TIME_BETWEEN_ALIEN_BULLETS,
		    cfg.MAX_TIME_BETWEEN_ALIEN_BULLETS));
	}
    }

    public void update(int delta) {
	calculateLookups();
	checkForSpeedIncreaseFromPercentageOfDeadAliens();
	if (freeMovingAliens.timeForRelease()) {
	    freeMovingAliens.realeaseAnAlien(getRandomInFormationAlien(),
		    movingLeft ? RIGHT : LEFT);
	}
	float dtw = distanceToWall();
	float dc = distanceCovered(delta);

	if (dc <= dtw) {
	    setMoveDistance(dc);
	    updateEachAlien(delta);
	} else if (dc > dtw) {
	    setMoveDistance(dtw);
	    updateEachAlien(delta);
	    int remainingDelta = getTimeToTravelDistance(dtw);
	    if (movingRight || movingLeft) {
		speed.reset(0, getMaxAlienSpeed());
		assignTargetToMoveDownTo();
	    } else if (movingDown && distRight < distLeft) {
		speed.reset(-getMaxAlienSpeed(), 0);
	    } else if (movingDown && distLeft <= distRight) {
		speed.reset(getMaxAlienSpeed(), 0);
	    }
	    // recursive call to update with remaining time
	    update(remainingDelta);
	}
    }

    // save all expensive calculated variables in one pass through the array
    private void calculateLookups() {
	rightMost = 0;
	leftMost = cfg.SCREEN.inGameRight();
	float lowest = cfg.SCREEN.inGameTop();
	float highest = cfg.SCREEN.inGameBottom();
	livingAliens = 0;
	inFormationAliens = 0;

	for (int i = 0; i < formation.size(); i++) {
	    if (aliens[i].isVisible()) {
		leftMost = aliens[i].getLeftHomeSlot(anchor) < leftMost ? aliens[i]
			.getLeftHomeSlot(anchor)
			: leftMost;
		rightMost = aliens[i].getRightHomeSlot(anchor) > rightMost ? aliens[i]
			.getRightHomeSlot(anchor)
			: rightMost;
		lowest = aliens[i].getYHomeSlot(anchor) > lowest ? aliens[i]
			.getYHomeSlot(anchor) : lowest;
		highest = (int) Math
			.floor(aliens[i].getYHomeSlot(anchor) < highest ? aliens[i]
				.getYHomeSlot(anchor)
				: highest);
	    }
	    if (aliens[i].isAlive()) {
		livingAliens++;
	    }
	    if (aliens[i].inFormation()) {
		inFormationAliens++;
	    }
	}

	distRight = cfg.SCREEN.inGameRight() - rightMost - 2;
	distLeft = leftMost - cfg.SCREEN.inGameLeft() - 2;
	bottomMost = lowest;
	movingLeft = speed.getDx() < 0;
	movingRight = speed.getDx() > 0;
	movingDown = speed.getDy() > 0;
    }

    private float distBottomTarget() {
	return (targetY - bottomMost);
    }

    private int getTimeToTravelDistance(float distance) {
	return (int) distance * 1000 / getSpeedAbs();
    }

    private Location offset = new Location();

    private void updateEachAlien(int delta) {
	int inFormation = 0;
	for (int i = 0; i < formation.size(); i++) {
	    if (aliens[i].isVisible()) {
		if (!aliens[i].isSolo()) {
		    // in formation aliens
		    checkNotGoingOffscreen(aliens[i]);
		    aliens[i].moveBy(moveDist);
		    inFormation++;
		} else {
		    // solo aliens
		    offset.setX(anchor.getXAsFloat() + aliens[i].relAnchorX);
		    offset.setY(anchor.getYAsFloat() + aliens[i].relAnchorY);
		    offset.moveBy(moveDist);
		    controller.adjust(delta, aliens[i], offset);
		}
	    }
	}
	anchor.moveBy(moveDist);
    }

    private void checkNotGoingOffscreen(Alien alien) {
	int x = alien.getX() + moveDist.getX();
	int alienR = alien.rightEdge() + moveDist.getX();
	assert x >= cfg.SCREEN.inGameLeft() : "off screen left: x: " + x
		+ " s.l: " + cfg.SCREEN.inGameLeft() + "\n" + alien
		+ "\nMoveDist: " + moveDist + "\ndistLeft: " + distLeft
		+ "\nleftMost: " + leftMost + "\nrightMost: " + rightMost;
	assert alienR <= cfg.SCREEN.inGameRight() : "off screen right: alienR: "
		+ alienR
		+ " s.r: "
		+ cfg.SCREEN.inGameRight()
		+ "\n"
		+ alien
		+ "\nMoveDist: "
		+ moveDist
		+ "\ndistRight: "
		+ distRight
		+ "\nleftMost: " + leftMost + "\nrightMost: " + rightMost;
    }

    private void setMoveDistance(float dc) {
	if (movingLeft) {
	    moveDist.moveTo(-dc, 0);
	} else if (movingRight) {
	    moveDist.moveTo(dc, 0);
	} else if (movingDown) {
	    moveDist.moveTo(0, dc);
	} else {
	    assert false : "Aliens are not moving!";
	}
    }

    private float distanceCovered(int delta) {
	int speedAbs = getSpeedAbs();
	return speedAbs * (float) delta / 1000;
    }

    private int getSpeedAbs() {
	int speedAbs = 0;
	if (movingLeft || movingRight) {
	    speedAbs = Math.abs(speed.getDx());
	} else if (movingDown) {
	    speedAbs = Math.abs(speed.getDy());
	} else {
	    assert false : "Aliens not moving, impossible!";
	}
	return speedAbs;
    }

    private float distanceToWall() {
	float dtw = 0;
	if (movingLeft) {
	    dtw = distLeft;
	} else if (movingRight) {
	    dtw = distRight;
	} else if (movingDown) {
	    dtw = distBottomTarget();
	} else {
	    assert false : "Not moving left right or down, impossible!";
	}
	return dtw;
    }

    private Location getShooterLocation() {
	Alien alien = getRandomLivingAlien();
	return alien.getLocation();
    }

    private Alien getRandomLivingAlien() {
	int count = this.livingAliens;
	if (count == 0) {
	    return null;
	}
	int r = Util.getRandom(0, count - 1);
	int x = 0;
	for (int i = 0; i < formation.size(); i++) {
	    if (aliens[i].isAlive()) {
		if (x == r) {
		    return aliens[i];
		}
		x++;
	    }
	}
	return nullAlien;
    }

    private Alien getRandomInFormationAlien() {
	int count = inFormationAliens;
	if (count == 0) {
	    return null;
	}
	int r = Util.getRandom(0, count - 1);
	int x = 0;
	for (int i = 0; i < formation.size(); i++) {
	    if (aliens[i].inFormation()) {
		if (x == r) {
		    return aliens[i];
		}
		x++;
	    }
	}
	return null;
    }

    private void checkForSpeedIncreaseFromPercentageOfDeadAliens() {
	int newMaxSpeed = getMaxAlienSpeed();
	if (newMaxSpeed != currentMaxSpeed) {
	    currentMaxSpeed = newMaxSpeed;
	    adjustSpeedToNewMax();
	}
    }

    private void adjustSpeedToNewMax() {
	int newDx = speed.getDx() == 0 ? 0
		: speed.getDx() > 0 ? currentMaxSpeed : -currentMaxSpeed;
	int newDy = speed.getDy() == 0 ? 0
		: speed.getDy() > 0 ? currentMaxSpeed : -currentMaxSpeed;
	speed.reset(newDx, newDy);
    }

    private int getMaxAlienSpeed() {
	final int percentLiving = livingAliens * 100 / formation.size();
	return getAlienSpeeds().get(percentLiving);
    }

    private void assignTargetToMoveDownTo() {
	targetY = bottomMost + formation.getVerticalStepDistance();
    }

    private int livingAliens() {
	int living = 0;
	for (int i = 0; i < formation.size(); i++) {
	    if (aliens[i].isAlive()) {
		living++;
	    }
	}
	return living;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Aliens: [" + speed + "] [currentMaxSpeed: "
		+ currentMaxSpeed + "]");
	sb.append("\n");
	for (int i = 0; i < formation.size(); i++) {
	    sb.append(aliens[i].toString());
	    sb.append("\n");
	}
	return sb.toString();
    }
}
