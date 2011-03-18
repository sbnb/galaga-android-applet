package com.gamefreezer.galaga;

public class Bullets extends AllocGuard {

    private Bullet[] bulletsArray;
    private int bulletWidth;

    public Bullets(SpriteCache spriteStore, Screen screen,
	    int numBulletsOnScreen, String bulletImage) {
	super();

	assert bulletImage != null : "bulletImage is null!";
	assert bulletImage != "" : "bulletImage is empty string!";

	this.bulletsArray = new Bullet[numBulletsOnScreen];
	for (int i = 0; i < bulletsArray.length; i++) {
	    bulletsArray[i] = new Bullet(spriteStore, screen, bulletImage);
	}
	bulletWidth = bulletsArray[0].getWidth();
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
