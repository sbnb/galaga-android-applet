package com.gamefreezer.galaga;

import java.util.Arrays;
import java.util.Iterator;

public class Bullets extends AllocGuard implements Iterable<Bullet> {

    public Bullets(SpriteCache spriteStore, int numBulletsOnScreen,
	    String bulletImage) {
	super();
	this.bulletsArray = new Bullet[numBulletsOnScreen];

	for (int i = 0; i < bulletsArray.length; i++) {
	    bulletsArray[i] = new Bullet(spriteStore, bulletImage);
	}
	bulletWidth = bulletsArray[0].width;
    }

    public int bulletWidth() {
	return bulletWidth;
    }

    @Override
    public Iterator<Bullet> iterator() {
	return Arrays.asList(bulletsArray).iterator();
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

    private Bullet[] bulletsArray;
    private int bulletWidth;

}
