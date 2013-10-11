//package com.gamefreezer.galaga;
//
//import static com.gamefreezer.galaga.Constants.*;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class BulletsTest {
//
//	Score score;
//
//	@Before
//	public void setUp() {
//		score = new Score();
//	}
//
//	@Test
//	public void trackOneBulletUpScreen() {
//		Bullets bullets = new Bullets(1, BULLET_IMAGE);
//		Ship ship = new Ship(new Location(0, 0));
//		ship.shoot(bullets, score);
//		Entity b = getNthBullet(0, bullets);
//		bullets.move(3000);
//		assertThat("", b.getMovement().getLocation().getY(), is(BULLET_MOVEMENT
//				* 3 + ship.getGunLocation(bullets.bulletWidth()).getY()));
//	}
//
//	@Test
//	public void testCreateBulletsClass() {
//		Bullets bullets = new Bullets(10, BULLET_IMAGE);
//		assertEquals(countBullets(bullets), 10);
//	}
//
//	@Test
//	public void testFireABulletAndCheckItStartsWhereSpecified() {
//		Bullets bullets = new Bullets(5, BULLET_IMAGE);
//		Location source = new Location(42, 43);
//		Ship ship = new Ship(source);
//		ship.shoot(bullets, score);
//		Entity bullet = getNthBullet(0, bullets);
//		assertEquals(bullet.getX(), ship.getGunLocation(bullets.bulletWidth()).getX());
//		assertEquals(bullet.getY(), ship.getGunLocation(bullets.bulletWidth()).getY());
//	}
//
//	@Test
//	public void testUpdateBulletsMovesAnExistingBulletUpTheScreen() {
//		Bullets bullets = new Bullets(5, BULLET_IMAGE);
//		Location source = new Location(42, 43);
//		Ship ship = new Ship(source);
//		ship.shoot(bullets, score);
//		bullets.move(1000);
//		Entity bullet = getNthBullet(0, bullets);
//		assertTrue(bullet.getY() > ship.getGunLocation(bullets.bulletWidth()).getY());
//	}
//
//	@Test
//	public void testMulitpleCreatesAndUpdates() {
//		Bullets bullets = fireABunchOfBulletsOffTheScreen(5);
//		Entity bullet = getNthBullet(0, bullets);
//		assertTrue(bullet.getY() > 43);
//		assertEquals(countBullets(bullets), 5);
//	}
//
//	@Test
//	public void testAreAllBulletInstancesInTheArray() {
//		Bullets bullets = fireABunchOfBulletsOffTheScreen(5);
//		for (Entity bullet : bullets) {
//			assertEquals(bullet.getClass(), Bullet.class);
//		}
//	}
//
//	private Bullets fireABunchOfBulletsOffTheScreen(int total) {
//		Bullets bullets = new Bullets(total, BULLET_IMAGE);
//		Location source = new Location(42, 43);
//		Ship ship = new Ship(source);
//
//		for (int x = 0; x < 100; x++) {
//			ship.shoot(bullets, score);
//			bullets.move(1000);
//		}
//		return bullets;
//	}
//
//	private int countBullets(Bullets bullets) {
//		int count = 0;
//		for (@SuppressWarnings("unused")
//		Entity bullet : bullets) {
//			count++;
//		}
//		return count;
//	}
//
//	private Entity getNthBullet(int n, Bullets bullets) {
//		int i = 0;
//		for (Entity bullet : bullets) {
//			if (i == n) {
//				return bullet;
//			}
//			i++;
//		}
//		return null;
//	}
//
// }
