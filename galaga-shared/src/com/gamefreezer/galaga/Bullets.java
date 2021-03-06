package com.gamefreezer.galaga;

public class Bullets extends AllocGuard {

    private Bullet[] bulletsArray;
    private int bulletWidth;

    public Bullets(SpriteCache spriteCache, int maxFrames, Screen screen,
	    int numBulletsOnScreen, AnimationSource animationSource) {
	super();

	assert animationSource != null : "animationSource is null!";
	assert animationSource.names[0] != "" : "animationSource.names[0] is empty string!";

	this.bulletsArray = new Bullet[numBulletsOnScreen];
	for (int i = 0; i < bulletsArray.length; i++) {
	    final Animation anim = new Animation(spriteCache, maxFrames,
		    animationSource, false);
	    bulletsArray[i] = new Bullet(anim, screen);

	}
	bulletWidth = bulletsArray.length > 0 ? bulletsArray[0].getWidth() : 0;
    }

    public int bulletWidth() {
	return bulletWidth;
    }

    public Bullet[] getArray() {
	return bulletsArray;
    }

    /* Return true if new bullet added, false if not. */
    public boolean addNewBullet(Location startPoint, int velocity) {
	for (int i = 0; i < bulletsArray.length; i++) {
	    if (!(bulletsArray[i].isAlive())) {
		bulletsArray[i].reset(startPoint, velocity);
		return true;
	    }
	}
	return false;
    }

    /* Return true if new bullet added, false if not. */
    public boolean addNewBullet(Gun gun, Location startPoint, int velocity,
	    Animation bulletAnim, int damage) {

	if (bulletAnim == null) {
	    return false; // possible if animation pool was exhausted
	}

	for (int i = 0; i < bulletsArray.length; i++) {
	    if (!(bulletsArray[i].isAlive())) {
		bulletsArray[i].reset(gun, startPoint, velocity, bulletAnim,
			damage);
		return true;
	    }
	}
	return false;
    }

    public void killOnscreenBullets() {
	for (int i = 0; i < bulletsArray.length; i++) {
	    bulletsArray[i].kill();
	}
    }

    public boolean anyOnScreen() {
	for (Entity b : bulletsArray) {
	    if ((b.isAlive())) {
		return true;
	    }
	}
	return false;
    }

    public void move(int timeDelta) {
	for (Entity b : bulletsArray) {
	    if ((b.isAlive())) {
		b.move(timeDelta);
	    }
	}
    }

    public void draw(AbstractGraphics graphics) {
	for (Entity bullet : bulletsArray) {
	    if (bullet.isAlive()) {
		bullet.draw(graphics);
	    }
	}
    }

    @Override
    public String toString() {
	String buffer = "";
	for (Entity b : bulletsArray) {
	    buffer += b.toString() + "\n";
	}
	return buffer;
    }

}
