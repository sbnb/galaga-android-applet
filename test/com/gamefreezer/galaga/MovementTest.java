//package com.gamefreezer.galaga;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class MovementTest {
//
//	private Movement m;
//
//	@Before
//	public void setUp() {
//		m = new Movement(new Location(10, 10), new Speed(1, 1));
//	}
//
//	@Test
//	public void movesExpectedAmount() {
//		m.update(1000);
//		assertThat("moved 1 pixel +x", m.getLocation().getX(), is(11));
//		assertThat("moved 1 pixel +y", m.getLocation().getY(), is(11));
//	}
//
//	@Test
//	public void lotsOfSubWholePixelMovesAddToAPixelMove() {
//		updateNTimes(10000);
//		assertThat("moved 1 pixel +x", m.getLocation().getX(), is(20));
//		assertThat("moved 1 pixel +y", m.getLocation().getY(), is(20));
//	}
//
//	@Test
//	public void fractionalPixelLocation() {
//		updateNTimes(500);
//		float newX = m.getLocation().getXAsFloat();
//		float newY = m.getLocation().getYAsFloat();
//		assertThat("moved .5 pixel +x", floatEquals(newX, 10.5f, 0.001f), is(true));
//		assertThat("moved .5 pixel +y", floatEquals(newY, 10.5f, 0.001f), is(true));
//	}
//
//	@Test
//	public void movementIsReflexive() {
//		updateNTimes(500);
//		m.setSpeed(new Speed(-1, -1));
//		updateNTimes(500);
//		assertThat("x at origin", floatEquals(m.getLocation().getXAsFloat(),
//				10.0f, 0.001f), is(true));
//		assertThat("y at origin", floatEquals(m.getLocation().getYAsFloat(),
//				10.0f, 0.001f), is(true));
//	}
//
//	private void updateNTimes(int n) {
//		for (int i = 0; i < n; i++) {
//			m.update(1);
//		}
//	}
//
//	private boolean floatEquals(float f1, float f2, float epsilon) {
//		float result = Math.abs(Math.abs(f1) - Math.abs(f2));
//		return result < epsilon;
//	}
// }
