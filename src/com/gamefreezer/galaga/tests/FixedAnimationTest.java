package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.FixedAnimation;

public class FixedAnimationTest {

    private FixedAnimation fanim;
    private MockGraphics graphics;
    private int[] coords = new int[] { 10, 30 };

    @Before
    public void setUp() throws Exception {
	fanim = FixedAnimation.build(Helper.buildSpriteCache(), Helper
		.buildAnimationSource(), coords);
	graphics = new MockGraphics();
    }

    @Test
    public void drawUsesFixedCoordinates() {
	fanim.draw(graphics);
	assertThat("x", graphics.x, is(coords[0]));
	assertThat("y", graphics.y, is(coords[1]));
    }

    @Test
    public void resetWorks() {
	fanim.draw(graphics);
	fanim.draw(graphics);
	fanim.reset();
	assertThat("reset", fanim.isFinished(), is(false));
    }

    @Test
    public void animationFinishesAsExpected() {
	fanim.draw(graphics);
	fanim.draw(graphics);
	assertThat(fanim.isFinished(), is(true));
    }

}
