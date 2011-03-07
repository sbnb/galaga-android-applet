package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.EXPL_IMGS;
import static com.gamefreezer.galaga.Constants.EXPL_TIMES;

public class Entity extends AllocGuard {

    protected Movement movement = new Movement();
    protected Speed maxSpeed = new Speed();
    protected int width = 0;
    protected int height = 0;
    protected boolean active = true;
    protected boolean exploding = false;
    protected boolean solo = false;
    protected boolean diveComplete = false;
    protected AnimationFrames animation;
    protected int points = 0;

    public Entity(SpriteCache spriteStore) {
	super();
	animation = new AnimationFrames(spriteStore);
    }

    public Entity(SpriteCache spriteStore, Location location, int width,
	    int height, int horizontalMovement, int verticalMovement,
	    String imagePath, String renderTimes, String renderTicks) {
	// super();
	this(spriteStore);
	// animation = new AnimationFrames(spriteStore);
	this.width = width;
	this.height = height;
	this.active = true;

	if (imagePath != null) {
	    animation.reset(imagePath, renderTimes, renderTicks);
	}

	this.movement = new Movement(location, new Speed(horizontalMovement,
		verticalMovement));
    }

    public Entity(SpriteCache spriteStore, Location location,
	    int horizontalMovement, int verticalMovement, String imageUrl,
	    String renderTimes, String renderTicks) {
	this(spriteStore, location,
		Util.widthFromSprite(spriteStore, imageUrl), Util
			.heightFromSprite(spriteStore, imageUrl),
		horizontalMovement, verticalMovement, imageUrl, renderTimes,
		renderTicks);
    }

    public void setImagePath(String imagePath) {
	setImagePath(imagePath, "", "");
    }

    public void setImagePath(String imagePath, String renderTimes,
	    String renderTicks) {
	if (imagePath != null) {
	    animation.reset(imagePath, renderTimes, renderTicks);
	}
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
	    int x = Screen.translateX(getX());
	    int y = Screen.translateY(getY());
	    // graphics.setColor(color);
	    animation.draw(graphics, x, y - height);
	    resetExploding();
	    // if (!isVisible()) {
	    // SpriteStore.instance().get("imgs/ship.jpg").draw(graphics, x, y -
	    // height);
	    // }
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

    public int getScreenX() {
	return Screen.translateX(movement.getX());
    }

    public int getScreenY() {
	return Screen.translateY(movement.getY());
    }

    public int leftEdge() {
	return movement.getX();
    }

    public int rightEdge() {
	return movement.getX() + width;
    }

    public int getDx() {
	return movement.getSpeed().getDx();
    }

    public int getDy() {
	return movement.getSpeed().getDy();
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

    public void explode() {
	exploding = true;
	animation.reset(EXPL_IMGS, EXPL_TIMES, "", true);
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
	    movement.getLocation().setX(Screen.left());
	}
    }

    private void adjustIfOffScreenRight() {
	if (offScreenRight() && stopIfOffScreenLeftOrRight()) {
	    movement.getLocation().setX(Screen.width() - width);
	}
    }

    private void adjustIfOffScreenTop() {
	if (offScreenTop() && stopIfOffScreenTopOrBottom()) {
	    movement.getLocation().setY(Screen.playableTop() - height);
	}
    }

    protected void adjustIfOffScreenBottom() {
	if (offScreenBottom() && stopIfOffScreenTopOrBottom()) {
	    movement.getLocation().setY(Screen.playableBottom());
	}
    }

    private boolean offScreen() {
	return offScreenLeft() || offScreenRight() || offScreenTop()
		|| offScreenBottom();
    }

    private boolean offScreenLeft() {
	return getX() < Screen.left();
    }

    private boolean offScreenRight() {
	return getX() + width > Screen.width();
    }

    private boolean offScreenTop() {
	return getY() + height > Screen.playableTop();
    }

    private boolean offScreenBottom() {
	return getY() < Screen.playableBottom();
    }

    protected boolean offScreenBottom(int offset) {
	return getY() < Screen.playableBottom() - offset;
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
