package com.gamefreezer.galaga;

import java.util.SortedMap;

public class Aliens extends AllocGuard {

    private final Alien[] aliensArray;
    private Formation formation;
    private final Speed speed;
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
    private final SoloAliens soloAliens;
    private final OldGun gun;
    private Location anchor = new Location();
    private final Alien nullAlien;
    private Screen screen;

    public Aliens(SpriteCache spriteCache, Screen screen, OldGun gun,
	    SoloAliens soloAliens, int maxFormation, int hitRendererPoolSize) {
	super();
	this.screen = screen;

	// TODO is this required? it is why the stray bullet appears left screen
	// at end of level
	nullAlien = new Alien(spriteCache, screen,
		soloAliens.soloReturnSpeed(), hitRendererPoolSize);

	speed = new Speed();
	moveDist = new Location();

	this.gun = gun;
	this.soloAliens = soloAliens;

	aliensArray = new Alien[maxFormation];
	for (int i = 0; i < maxFormation; i++) {
	    aliensArray[i] = new Alien(spriteCache, screen, soloAliens
		    .soloReturnSpeed(), hitRendererPoolSize);
	    aliensArray[i].kill();
	}
    }

    public void newLevel(Formation aFormation) {
	this.formation = aFormation;
	aFormation.createAliens(aliensArray);
	livingAliens = aFormation.size();
	currentMaxSpeed = aFormation.getAlienSpeeds().get(100);
	speed.reset(currentMaxSpeed, 0);
	anchor.moveTo(aliensArray[0].getLocation());
	setRelativeToAnchor();
    }

    private void setRelativeToAnchor() {
	for (int i = 0; i < formation.size(); i++) {
	    aliensArray[i].setRelativeToAnchor(anchor);
	}
    }

    // Send existing aliens back to formation at top of screen
    public void resetLivingAliens() {
	formation.resetLivingAliens(aliensArray);
	formation.setAnchor(anchor);
	livingAliens = formation.size();
	speed.reset(currentMaxSpeed, 0);
    }

    public Alien[] getArray() {
	return aliensArray;
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
	    aliensArray[i].draw(graphics);
	}
    }

    public void setFreeMovingAllowed(boolean freeMovingAllowed) {
	soloAliens.setFreeMovingallowed(freeMovingAllowed);
    }

    public void shoot(Bullets bullets) {
	if (gun.ready()) {
	    gun.shoot(bullets, getShooterLocation());
	}
    }

    public void update(int delta) {
	calculateLookups();
	checkForSpeedIncreaseFromPercentageOfDeadAliens();
	if (soloAliens.timeForRelease()) {
	    soloAliens.realeaseAnAlien(getRandomInFormationAlien(),
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
	leftMost = screen.inGameRight();
	float lowest = screen.inGameTop();
	float highest = screen.inGameBottom();
	livingAliens = 0;
	inFormationAliens = 0;

	for (int i = 0; i < formation.size(); i++) {
	    if (aliensArray[i].isVisible()) {
		leftMost = aliensArray[i].getLeftHomeSlot(anchor) < leftMost ? aliensArray[i]
			.getLeftHomeSlot(anchor)
			: leftMost;
		rightMost = aliensArray[i].getRightHomeSlot(anchor) > rightMost ? aliensArray[i]
			.getRightHomeSlot(anchor)
			: rightMost;
		lowest = aliensArray[i].getYHomeSlot(anchor) > lowest ? aliensArray[i]
			.getYHomeSlot(anchor)
			: lowest;
		highest = (int) Math
			.floor(aliensArray[i].getYHomeSlot(anchor) < highest ? aliensArray[i]
				.getYHomeSlot(anchor)
				: highest);
	    }
	    if (aliensArray[i].isAlive()) {
		livingAliens++;
	    }
	    if (aliensArray[i].inFormation()) {
		inFormationAliens++;
	    }
	}

	distRight = screen.inGameRight() - rightMost - 2;
	distLeft = leftMost - screen.inGameLeft() - 2;
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
	    if (aliensArray[i].isVisible()) {
		if (!aliensArray[i].isSolo()) {
		    // in formation aliens
		    checkNotGoingOffscreen(aliensArray[i]);
		    if (aliensArray[i].getY() < soloAliens.staySolo()) {
			aliensArray[i].moveBy(moveDist);
			inFormation++;
		    } else {
			// break into solo mode as so low on screen
			soloAliens.realeaseAnAlien(aliensArray[i], Util
				.getRandom(0, 1) == 1 ? RIGHT : LEFT);
		    }
		} else {
		    // solo aliens
		    offset.setX(anchor.getXAsFloat()
			    + aliensArray[i].relAnchorX);
		    offset.setY(anchor.getYAsFloat()
			    + aliensArray[i].relAnchorY);
		    offset.moveBy(moveDist);
		    soloAliens.adjust(delta, aliensArray[i], offset);
		}
	    }
	}
	anchor.moveBy(moveDist);
    }

    private void checkNotGoingOffscreen(Alien alien) {
	int x = alien.getX() + moveDist.getX();
	int alienR = alien.rightEdge() + moveDist.getX();
	assert x >= screen.inGameLeft() : "off screen left: x: " + x + " s.l: "
		+ screen.inGameLeft() + "\n" + alien + "\nMoveDist: "
		+ moveDist + "\ndistLeft: " + distLeft + "\nleftMost: "
		+ leftMost + "\nrightMost: " + rightMost;
	assert alienR <= screen.inGameRight() : "off screen right: alienR: "
		+ alienR + " s.r: " + screen.inGameRight() + "\n" + alien
		+ "\nMoveDist: " + moveDist + "\ndistRight: " + distRight
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
	boolean mustBeInFormation = false;
	boolean mustBeAlive = true;
	return getRandomAlienWithConditions(nullAlien, livingAliens,
		mustBeInFormation, mustBeAlive);
    }

    private Alien getRandomInFormationAlien() {
	boolean mustBeInFormation = true;
	boolean mustBeAlive = false;
	return getRandomAlienWithConditions(null, inFormationAliens,
		mustBeInFormation, mustBeAlive);
    }

    private Alien getRandomAlienWithConditions(Alien returnOnFail, int count,
	    boolean mustBeInFormation, boolean mustBeAlive) {
	if (count == 0) {
	    return null;
	}
	int r = Util.getRandom(0, count - 1);
	int x = 0;

	for (int i = 0; i < formation.size(); i++) {
	    if ((mustBeInFormation && aliensArray[i].inFormation())
		    || (mustBeAlive && aliensArray[i].isAlive())) {
		if (x == r) {
		    return aliensArray[i];
		}
		x++;
	    }
	}
	return returnOnFail;
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
	    if (aliensArray[i].isAlive()) {
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
	    sb.append(aliensArray[i].toString());
	    sb.append("\n");
	}
	return sb.toString();
    }
}
