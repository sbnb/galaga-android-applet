//package com.gamefreezer.galaga;
//
//import static com.gamefreezer.galaga.Constants.*;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertThat;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
////import com.gamefreezer.applet_galaga.AppletBitmapReader;
////import com.gamefreezer.applet_galaga.AppletColor;
//import com.gamefreezer.galaga.Entity;
//
//import static org.hamcrest.CoreMatchers.*;
//
//public class EntityTest {
//
//    private Entity entity;
//    private String imageUrl = ALIEN_IMAGE;
//
//    // private Game game;
//
//    @Before
//    public void setUp() throws Exception {
//	// Game.bitmapReader = new AppletBitmapReader();
//	// Game.colorDecoder = new AppletColor(Color.BLACK);
//	// assert Game.colorDecoder != null : "Game.colorDecoder is null!";
//	// TODO sort out tests or get rid of them
//	// game = new Game();
//	entity = new Entity(new Location(100, 100), 10, 10, 1, 1, imageUrl, "",
//		"");
//    }
//
//    @After
//    public void tearDown() throws Exception {
//	entity = null;
//    }
//
//    @Test
//    public void settingWidthAndHeightFromSpriteInAlternativeConstructor() {
//	Entity alien = new Entity(new Location(100, 100), 1, 1, imageUrl, "",
//		"");
//	assertThat("width set", alien.getWidth(), is(20));
//	assertThat("height set", alien.getHeight(), is(20));
//    }
//
//    @Test
//    public void perfectlyOverlappedEntitiesCollide() {
//	Entity craft = new Entity(new Location(100, 100), 10, 10, 1, 1,
//		imageUrl, "", "");
//	assertTrue(craft.intersects(entity));
//	assertTrue(entity.intersects(craft));
//    }
//
//    @Test
//    public void leftRightJustOverlappedEntitiesCollide() {
//	Entity craft = new Entity(new Location(91, 100), 10, 10, 1, 1,
//		imageUrl, "", "");
//	assertTrue(craft.intersects(entity));
//	assertTrue(entity.intersects(craft));
//    }
//
//    @Test
//    public void leftRightAdjacentEntitiesDoNotCollide() {
//	Entity craft = new Entity(new Location(90, 100), 10, 10, 1, 1,
//		imageUrl, "", "");
//	assertFalse(craft.intersects(entity));
//	assertFalse(entity.intersects(craft));
//    }
//
//    @Test
//    public void topBottomJustOverlappedEntitiesCollide() {
//	Entity craft = new Entity(new Location(100, 91), 10, 10, 1, 1,
//		imageUrl, "", "");
//	assertTrue(craft.intersects(entity));
//	assertTrue(entity.intersects(craft));
//    }
//
//    @Test
//    public void topBottomAdjacentEntitiesDoNotCollide() {
//	Entity craft = new Entity(new Location(100, 90), 10, 10, 1, 1,
//		imageUrl, "", "");
//	assertFalse(craft.intersects(entity));
//	assertFalse(entity.intersects(craft));
//    }
//
//    @Test
//    public void overlappedCornersCollide() {
//	Entity craft = new Entity(new Location(91, 91), 10, 10, 1, 1, imageUrl,
//		"", "");
//	assertTrue(craft.intersects(entity));
//	assertTrue(entity.intersects(craft));
//    }
//
//    @Test
//    public void moveLeft() {
//	Entity craft = new Entity(new Location(100, 100), 10, 10, -1, 0,
//		imageUrl, "", "");
//	craft.move(1000);
//	assertThat("moved left", craft.getX(), is(99));
//	assertThat("no move vertical", craft.getY(), is(100));
//    }
//
//    @Test
//    public void moveRight() {
//	Entity craft = new Entity(new Location(100, 100), 10, 10, 1, 0,
//		imageUrl, "", "");
//	craft.move(1000);
//	assertThat("moved right", craft.getX(), is(101));
//	assertThat("no move vertical", craft.getY(), is(100));
//    }
//
//    @Test
//    public void moveUp() {
//	Entity craft = new Entity(new Location(100, 100), 10, 10, 0, 1,
//		imageUrl, "", "");
//	craft.move(1000);
//	assertThat("moved up", craft.getY(), is(101));
//	assertThat("no move horizontal", craft.getX(), is(100));
//    }
//
//    @Test
//    public void moveDown() {
//	Entity craft = new Entity(new Location(100, 100), 10, 10, 0, -1,
//		imageUrl, "", "");
//	craft.move(1000);
//	assertThat("moved down", craft.getY(), is(99));
//	assertThat("no move horizontal", craft.getX(), is(100));
//    }
//
//    @Test
//    public void hitWallLeftStops() {
//	Entity craft = new Entity(new Location(Screen.left() + 10, 100), 10,
//		10, -1, 0, imageUrl, "", "");
//	for (int i = 0; i < 20; i++) {
//	    craft.move(1000);
//	}
//	assertThat("hit left wall", craft.getX(), is(Screen.left()));
//    }
//
//    @Test
//    public void hitWallRightStops() {
//	Entity craft = new Entity(new Location(Screen.width() - 20, 100), 10,
//		10, 1, 0, imageUrl, "", "");
//	for (int i = 0; i < 20; i++) {
//	    craft.move(1000);
//	}
//	assertThat("hit right wall", craft.getX(), is(Screen.width() - 10));
//    }
//
//    @Test
//    public void hitTopStops() {
//	Entity craft = new Entity(new Location(100, Screen.playableTop() - 20),
//		10, 10, 0, 1, imageUrl, "", "");
//	for (int i = 0; i < 20; i++) {
//	    craft.move(1000);
//	}
//	assertThat("hit top", craft.getY(), is(Screen.playableTop() - 10));
//    }
//
//    @Test
//    public void hitBottomStops() {
//	Entity craft = new Entity(new Location(100,
//		Screen.playableBottom() + 10), 10, 10, 0, -1, imageUrl, "", "");
//	for (int i = 0; i < 20; i++) {
//	    craft.move(1000);
//	}
//	assertThat("hit bottom", craft.getY(), is(Screen.playableBottom()));
//    }
// }
