//package com.gamefreezer.galaga;
//
//import static com.gamefreezer.galaga.Constants.*;
//import static org.junit.Assert.assertEquals;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.gamefreezer.galaga.Util;
//import com.gamefreezer.galaga.Sprite;
//import com.gamefreezer.galaga.SpriteStore;
//
//
//public class SpriteStoreTest {
//
//	@Before
//	public void setUp() throws Exception {
//		SpriteStore.instance().clear();
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		SpriteStore.instance().clear();
//	}
//
//	@Test(expected=IllegalArgumentException.class)
//	public void badPathThrowsIllegalArgumentException() {
//		SpriteStore.instance().get("bad path");
//	}
//
//	@Test
//	public void testCanLoadAGoodUrl() {
//		Sprite sprite = SpriteStore.instance().get(ALIEN_IMAGE);
//		assertEquals("sprite.getWidth()", sprite.getWidth(), 20);
//	}
//
//	@Test
//	public void testSpritesAreOnlyLoadedOnceIntoTheSpriteStore() {
//		assertEquals("SpriteStore size", 0, SpriteStore.instance().size());
//		SpriteStore.instance().get(ALIEN_IMAGE);
//		SpriteStore.instance().get(ALIEN_IMAGE);
//		assertEquals("SpriteStore size", 1, SpriteStore.instance().size());
//	}
//	
//	@Test
//	public void testDifferentSpritesBothGetSavedInSpriteStore() {
//		assertEquals("SpriteStore size", 0, SpriteStore.instance().size());
//		
//		String firstAlienImage = Util.getStringAsList(ALIEN_IMAGE).get(0);
//		SpriteStore.instance().get(firstAlienImage);
//		
//		String firstShipImage = Util.getStringAsList(SHIP_IMAGE).get(0);
//		SpriteStore.instance().get(firstShipImage);
//		
//		assertEquals("SpriteStore size", 2, SpriteStore.instance().size());
//	}
// }
