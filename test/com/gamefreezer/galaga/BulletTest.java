package com.gamefreezer.galaga;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import static com.gamefreezer.galaga.Constants.*;

public class BulletTest {

	@Test
	public void testCreation() {
		Entity bullet = getABullet(0, 0, 1);
		assertEquals(bullet.getX(), 0);
		assertEquals(bullet.getY(), 0);
	}

	@Test
	public void moveWithTimeDelta() {
		Entity bullet = getABullet(123, 25, 10);
		bullet.move(3000);
		assertThat("moved up", bullet.getY(), is(25 + 10 * 3));
		assertThat("not sideways", bullet.getX(), is(123));
	}

	@Test
	public void testMoveUpWorks() {
		Entity bullet = getABullet(0, 0, 1);
		bullet.move(1000);
		assertThat("x unchanged", bullet.getX(), is(0));
		assertThat("bullet moved up", bullet.getY(), is(1));
	}

	@Test
	public void testGoesOffScreen() {
		Entity bullet = getABullet(0, Screen.height() - 20, 1);
		repeatMove(bullet, 30);
		assertThat("is off screen", bullet.getY(), is(Screen.height() + 10));
	}

	@Test
	public void testDeactivatedWhenOffScreen() {
		Entity bullet = getABullet(0, Screen.height() - 20, 1);
		repeatMove(bullet, 30);
		assertThat("is not alive", !bullet.isAlive(), is(true));
	}

	private static Entity getABullet(int x, int y, int dy) {
		Location source = new Location(x, y);
		Entity bullet = new Bullet(source, dy, BULLET_IMAGE);
		return bullet;
	}

	private void repeatMove(Entity bullet, int N) {
		for (int x = 0; x < N; x++) {
			bullet.move(1000);
		}
	}

}
