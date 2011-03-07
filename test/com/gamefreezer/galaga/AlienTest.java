//package com.gamefreezer.galaga;
//
//import static com.gamefreezer.galaga.Constants.*;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//import org.junit.Test;
//
//public class AlienTest {
//
//	@Test
//	public void testHitWallLeftStops() {
//		Alien alien = new Alien(new Location(Screen.left() + 10, 100), -1,
//				0, 1, 1, ALIEN_IMAGE, 0, "", "");
//		for (int i = 0; i < 21; i++) {
//			alien.move(1000);
//		}
//		assertThat("stopped at left", alien.getX(), is(Screen.left())); //$NON-NLS-1$
//	}
//
//	@Test
//	public void testHitWallRightStops() {
//		Alien alien = new Alien(new Location(Screen.width() - 20, 100), 1,
//				0, 1, 1, ALIEN_IMAGE, 0, "", "");
//		for (int i = 0; i < 21; i++) {
//			alien.move(1000);
//		}
//		assertThat("stopped at right", alien.getX(), is(Screen.width() - 20));
//	}
// }
