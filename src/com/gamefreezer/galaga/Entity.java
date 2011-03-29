package com.gamefreezer.galaga;

public class Entity extends AllocGuard {

    protected Constants cfg;
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

    public Entity(SpriteCache spriteStore, Screen screen, Speed targettingSpeed) {
	this(spriteStore, screen, new Location(), 0, 0, 0, 0, null, null,
		targettingSpeed);
    }

    public Entity(SpriteCache spriteStore, Screen screen, Location location,
	    int horizontalMovement, int verticalMovement, String[] imageNames,
	    int[] renderTimes) {
	this(spriteStore, screen, location, Util.widthFromSprite(spriteStore,
		imageNames), Util.heightFromSprite(spriteStore, imageNames),
		horizontalMovement, verticalMovement, imageNames, renderTimes,
		new Speed(0, 0));
    }

    // TODO dependency injection of animation required
    public Entity(SpriteCache spriteStore, Screen screen, Location location,
	    int width, int height, int horizontalMovement,
	    int verticalMovement, String[] imageNames, int[] renderTimes,
	    Speed targettingSpeed) {
	super();

	assert spriteStore != null : "spriteStore is null!";
	assert location != null : "location is null!";
	assert targettingSpeed != null : "targettingSpeed is null!";

	this.screen = screen;
	this.width = width;
	this.height = height;
	animation = new Animation(spriteStore);
	movement = new Movement(location, new Speed(horizontalMovement,
		verticalMovement), targettingSpeed);
	this.active = true;

	if (imageNames != null) {
	    animation.reset(imageNames, renderTimes);
	}
    }

    public void setImagePath(String[] imageNames, int[] renderTimes) {
	assert imageNames != null : "imageNames is null!";
	assert imageNames.length > 0 : "imageNames is empty!";
	animation.reset(imageNames, renderTimes);
    }

    public void move(int timeDelta) {
	movement.update(timeDelta);
	adjustIfPartiallyOffScreen();
	adjustIfCompletelyOffScreen();
	killIfOffScreen();
    }

    public void moveBy(float x, float y) {
	movement.moveBy(x, y);
    }

    public void moveBy(Location anAmount) {
	movement.moveBy(anAmount);
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

    public boolean intersects(Entity entity) {
	if (intersectsOnXAxis(entity) && intersectsOnYaxis(entity)) {
	    return true;
	}
	return false;
    }

    public void draw(AbstractGraphics graphics) {
	if (active) {
	    animation.draw(graphics, getX(), getY());
	}
    }

    /* Align this entities center to other entities center */
    public void alignTo(Entity entity) {
	int x = entity.getX() + entity.getWidth() / 2 - getWidth() / 2;
	int y = entity.getY() + entity.getHeight() / 2 - getHeight() / 2;
	this.moveTo(x, y);
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
		+ height + " active: " + active + " ] " + movement;
    }
}
