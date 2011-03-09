package com.gamefreezer.galaga;

public class Entity extends AllocGuard {

    protected Constants cfg;
    protected Movement movement;
    protected Speed maxSpeed = new Speed();
    protected int width = 0;
    protected int height = 0;
    protected boolean active = true;
    protected boolean exploding = false;
    protected boolean solo = false;
    protected boolean diveComplete = false;
    protected AnimationFrames animation;
    protected int points = 0;
    protected Screen screen;

    public Entity(SpriteCache spriteStore, Screen screen, Speed targettingSpeed) {
	this(spriteStore, screen, new Location(), 0, 0, 0, 0, null, "", "",
		targettingSpeed);
    }

    public Entity(SpriteCache spriteStore, Screen screen, Location location,
	    int horizontalMovement, int verticalMovement, String imageName,
	    String renderTimes, String renderTicks) {
	this(spriteStore, screen, location, Util.widthFromSprite(spriteStore,
		imageName), Util.heightFromSprite(spriteStore, imageName),
		horizontalMovement, verticalMovement, imageName, renderTimes,
		renderTicks, new Speed(0, 0));
    }

    public Entity(SpriteCache spriteStore, Screen screen, Location location,
	    int width, int height, int horizontalMovement,
	    int verticalMovement, String imageName, String renderTimes,
	    String renderTicks, Speed targettingSpeed) {
	super();

	// assert cfg != null : "cfg is null!";
	assert spriteStore != null : "spriteStore is null!";
	assert location != null : "location is null!";
	assert renderTimes != null : "renderTimes is null!";
	assert renderTicks != null : "renderTicks is null!";
	assert targettingSpeed != null : "targettingSpeed is null!";

	// this.cfg = cfg;
	this.screen = screen;
	this.width = width;
	this.height = height;
	animation = new AnimationFrames(spriteStore);
	movement = new Movement(location, new Speed(horizontalMovement,
		verticalMovement), targettingSpeed);
	this.active = true;

	if (imageName != null) {
	    animation.reset(imageName, renderTimes, renderTicks);
	}
    }

    public void setImagePath(String imageName, String renderTimes,
	    String renderTicks) {
	assert imageName != null : "imageName is null!";
	animation.reset(imageName, renderTimes, renderTicks);
    }

    public void move(int timeDelta) {
	movement.update(timeDelta);
	adjustIfOffScreen();
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
	if (isVisible()) {
	    animation.draw(graphics, getX(), getY());
	    resetExploding();
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

    public void setSolo(boolean solo) {
	this.solo = solo;
    }

    public boolean isSolo() {
	return solo;
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

    public boolean inFormation() {
	return active && !solo;
    }

    public void kill() {
	active = false;
    }

    public void explode(String explosionImages, String explosionTimes) {
	exploding = true;
	animation.reset(explosionImages, explosionTimes, "", true);
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

    protected boolean stopIfOffScreenLeftOrRight() {
	return true;
    }

    protected boolean stopIfOffScreenTopOrBottom() {
	return true;
    }

    private void resetExploding() {
	if (animation.finished()) {
	    exploding = false;
	}
    }

    private void adjustIfOffScreen() {
	adjustIfOffScreenLeft();
	adjustIfOffScreenRight();
	adjustIfOffScreenTop();
	adjustIfOffScreenBottom();
    }

    private void adjustIfOffScreenLeft() {
	if (offScreenLeft() && stopIfOffScreenLeftOrRight()) {
	    movement.getLocation().setX(screen.inGameLeft());
	}
    }

    private void adjustIfOffScreenRight() {
	if (offScreenRight() && stopIfOffScreenLeftOrRight()) {
	    movement.getLocation().setX(screen.inGameRight() - width);
	}
    }

    private void adjustIfOffScreenTop() {
	if (offScreenTop() && stopIfOffScreenTopOrBottom()) {
	    System.out.println("Entity.adjustIfOffScreenTop() fired");
	    movement.getLocation().setY(screen.inGameTop());
	}
    }

    protected void adjustIfOffScreenBottom() {
	if (offScreenBottom() && stopIfOffScreenTopOrBottom()) {
	    System.out.println("Entity.adjustIfOffScreenBottom() fired");
	    movement.getLocation().setY(screen.inGameBottom() - height);
	}
    }

    private boolean offScreen() {
	return offScreenLeft() || offScreenRight() || offScreenTop()
		|| offScreenBottom();
    }

    private boolean offScreenLeft() {
	return getX() < screen.inGameLeft();
    }

    private boolean offScreenRight() {
	return rightEdge() > screen.inGameRight();
    }

    private boolean offScreenTop() {
	return getY() < screen.inGameTop();
    }

    private boolean offScreenBottom() {
	return getY() + height > screen.inGameBottom();
    }

    protected boolean offScreenBottom(int offset) {
	return getY() + height > screen.inGameBottom() + offset;
    }

    protected void killIfOffScreen() {
	if (offScreen()) {
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
		+ height + " active: " + active + " solo: " + solo + " ] "
		+ movement;
    }
}
