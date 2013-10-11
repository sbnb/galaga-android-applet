//package com.gamefreezer.galaga;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class AnimationFramesTest {
//
//    @Before
//    public void setUp() throws Exception {
//	SpriteStore.instance().clear();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//	// NOP
//    }
//
//    @Test
//    public void parsingImageNames() {
//	String names = "imgs/alien_c_1.png imgs/alien_c_2.png imgs/alien_d_1.png";
//	new AnimationFrames(null, names, "", "");
//	assertThat("sprite store size is 3", SpriteStore.instance().size(),
//		is(3));
//    }
//
//    @Test
//    public void singleFrameAnimations() {
//	String names = "imgs/alien_b.png";
//	new AnimationFrames(null, names, "", "");
//	assertThat("sprite store size is 1", SpriteStore.instance().size(),
//		is(1));
//    }
// }
