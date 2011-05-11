package com.gamefreezer.galaga;

public class Entity extends AllocGuard {

    protected Config cfg;
    protected Movement movement;
    protected Speed maxSpeed = new Speed();
    protected int width = 0;
    protected int height = 0;
    protected boolean stopIfPartiallyOffScreenLeftOrRight = true;
    protected boolean stopIfPartiallyOffScreenTopOrBottom = true;
    protected boolean killIfPartiallyOffScreen = true;
    protected boolean killIfCompletelyOffScreen = true;
    protected boolean active = true;
    protected boolean exploding = false;
    protected boolean diveComplete = false;
    protected Animation animation;
    protected int points = 0;
    protected Screen screen;
    private float rotation;
    private RotationSprites rotationSprites;

    public Entity(Animation animation, Screen screen, Speed targettingSpeed) {
	this(animation, screen, new Location(), 0, 0, 0, 0, targettingSpeed);
    }

    public Entity(Animation animation, Screen screen, Location location,
	    int xVelocity, int yVelocity) {
	this(animation, screen, location, animation.width(),
		animation.height(), xVelocity, yVelocity, new Speed(0, 0));
    }

    public Entity(Animation animation, Screen screen, Location location,
	    int width, int height, int xVelocity, int yVelocity,
	    Speed targettingSpeed) {

	super();

	assert location != null : "location is null!";
	assert targettingSpeed != null : "targettingSpeed is null!";

	this.screen = screen;
	this.width = width;
	this.height = height;

	this.animation = animation;

	movement = new Movement(location, new Speed(xVelocity, yVelocity),
		targettingSpeed);
	this.active = true;

    }

    public void setImagePath(AnimationSource animSrc) {
	assert animSrc != null : "animSrc is null!";
	animation.reset(animSrc);
    }

    public void move(int timeDelta) {
	movement.update(timeDelta);
	adjustIfPartiallyOffScreen();
	adjustIfCompletelyOffScreen();
	killIfOffScreen();
    }

    public void moveBy(float xDelta, float yDelta) {
	movement.moveBy(xDelta, yDelta);
    }

    public void moveBy(Location delta) {
	movement.moveBy(delta);
    }

    public void moveTo(int x, int y) {
	movement.moveTo(x, y);
    }

    public void moveTo(Location point) {
	movement.moveTo(point);
    }

    /* Rotate entity theta degrees around point location. */
    public void rotate(Location location, float theta) {
	movement.rotate(location, theta);
    }

    /* Rotate supplied degrees around own center point: changes sprite. */
    public void rotateSelf(float degrees) {
	this.rotation = degrees;
    }

    public void setRotationSprites(RotationSprites rotationSprites) {
	this.rotationSprites = rotationSprites;
    }

    public boolean intersects(Entity entity) {
	if (intersectsOnXAxis(entity) && intersectsOnYaxis(entity)) {
	    return true;
	}
	return false;
    }

    public void draw(AbstractGraphics graphics) {
	if (active) {
	    if (rotation > 0 && rotationSprites != null) {
		rotationSprites.draw(graphics, getX(), getY(), rotation);
	    } else {
		animation.draw(graphics, getX(), getY());
	    }
	}
    }

    /* Align this entities center to other entities center */
    public void alignTo(Entity entity) {
	final int xDest = entity.getX() + entity.width / 2 - width / 2;
	final int yDest = entity.getY() + entity.height / 2 - height / 2;
	this.moveTo(xDest, yDest);
    }

    public void setSpeed(Speed speed) {
	movement.setSpeed(speed);
    }

    public void setSpeed(int dx, int dy) {
	movement.setSpeed(dx, dy);
    }

    public Speed getSpeed() {
	return movement.getSpeed();
    }

    public void setMaxSpeed(int xMax, int yMax) {
	maxSpeed.reset(xMax, yMax);
    }

    public Speed getMaxSpeed() {
	return maxSpeed;
    }

    public void setDiveComplete(boolean diveComplete) {
	this.diveComplete = diveComplete;
    }

    public Movement getMovement() {
	return movement;
    }

    public boolean targetting() {
	return movement.targetting();
    }

    public void setTarget(int x, int y) {
	movement.setTarget(x, y);
    }

    public void setTarget(Location target) {
	movement.setTarget(target);
    }

    public Location getLocation() {
	return movement.getLocation();
    }

    public int getX() {
	return movement.getX();
    }

    public int getY() {
	return movement.getY();
    }

    public float getXAsFloat() {
	return movement.getXAsFloat();
    }

    public float getYAsFloat() {
	return movement.getYAsFloat();
    }

    public int rightEdge() {
	return movement.getX() + width;
    }

    public void setDimensions(int width, int height) {
	this.width = width;
	this.height = height;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public boolean isAlive() {
	return active;
    }

    public boolean isVisible() {
	return active || exploding;
    }

    public void setExploding(boolean exploding) {
	this.exploding = exploding;
    }

    public void kill() {
	active = false;
    }

    public void regenerate() {
	assert !active : "trying to regenerate living Entity!";
	active = true;
    }

    public int points() {
	return points;
    }

    public void setPoints(int points) {
	this.points = points;
    }

    private void adjustIfPartiallyOffScreen() {
	adjustIfPartiallyOffScreenLeft();
	adjustIfPartiallyOffScreenRight();
	adjustIfPartiallyOffScreenTop();
	adjustIfPartiallyOffScreenBottom();
    }

    private void adjustIfPartiallyOffScreenLeft() {
	if (stopIfPartiallyOffScreenLeftOrRight && partiallyOffScreenLeft()) {
	    movement.getLocation().setX(screen.inGameLeft());
	}
    }

    private void adjustIfPartiallyOffScreenRight() {
	if (stopIfPartiallyOffScreenLeftOrRight && partiallyOffScreenRight()) {
	    movement.getLocation().setX(screen.inGameRight() - width);
	}
    }

    private void adjustIfPartiallyOffScreenTop() {
	if (stopIfPartiallyOffScreenTopOrBottom && partiallyOffScreenTop()) {
	    movement.getLocation().setY(screen.inGameTop());
	}
    }

    protected void adjustIfPartiallyOffScreenBottom() {
	if (stopIfPartiallyOffScreenTopOrBottom && partiallyOffScreenBottom()) {
	    movement.getLocation().setY(screen.inGameBottom() - height);
	}
    }

    private boolean partiallyOffScreen() {
	return partiallyOffScreenLeft() || partiallyOffScreenRight()
		|| partiallyOffScreenTop() || partiallyOffScreenBottom();
    }

    private boolean partiallyOffScreenLeft() {
	return getX() < screen.inGameLeft();
    }

    private boolean partiallyOffScreenRight() {
	return rightEdge() > screen.inGameRight();
    }

    private boolean partiallyOffScreenTop() {
	return getY() < screen.inGameTop();
    }

    private boolean partiallyOffScreenBottom() {
	return getY() + height > screen.inGameBottom();
    }

    protected void adjustIfCompletelyOffScreen() {
	// NOP - behaviour deferred to subclasses
    }

    private boolean completelyOffScreen() {
	return completelyOffScreenLeft() || completelyOffScreenRight()
		|| completelyOffScreenTop() || completelyOffScreenBottom();
    }

    private boolean completelyOffScreenLeft() {
	return rightEdge() < screen.inGameLeft();
    }

    private boolean completelyOffScreenRight() {
	return getX() > screen.inGameRight();
    }

    private boolean completelyOffScreenTop() {
	return getY() + height < screen.inGameTop();
    }

    protected boolean completelyOffScreenBottom() {
	return getY() > screen.inGameBottom();
    }

    protected void killIfOffScreen() {
	if (killIfPartiallyOffScreen && partiallyOffScreen()) {
	    this.kill();
	}
	if (killIfCompletelyOffScreen && completelyOffScreen()) {
	    this.kill();
	}
    }

    private boolean intersectsOnYaxis(Entity entity) {
	return inRange(getY(), entity.getY(), entity.getHeight())
		|| inRange(entity.getY(), getY(), getHeight());
    }

    private boolean intersectsOnXAxis(Entity entity) {
	return inRange(getX(), entity.getX(), entity.getWidth())
		|| inRange(entity.getX(), getX(), getWidth());
    }

    /* true if a in range min..min+offset */
    private boolean inRange(int a, int min, int offset) {
	return (min <= a && a < (min + offset));
    }

    @Override
    public String toString() {
	return this.getClass().getSimpleName() + " [w: " + width + " h: "
		+ height + " active: " + active + " ] " + movement + " "
		+ animation;
    }
}
