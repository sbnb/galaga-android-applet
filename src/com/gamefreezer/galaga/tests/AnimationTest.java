package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.AbstractGraphics;
import com.gamefreezer.galaga.Animation;

public class AnimationTest {

    Animation anim;
    AbstractGraphics graphics = new MockGraphics();
    boolean oneShot = true;

    @Before
    public void setUp() throws Exception {
	anim = Helper.buildAnimation(oneShot);
    }

    @Test
    public void canDrawAFrame() {
	anim.draw(graphics, 0, 0);
	assertThat(anim.isFinished(), is(false));
    }

    @Test
    public void oneShotAnimationFinishes() {
	anim.draw(graphics, 0, 0);
	anim.draw(graphics, 0, 0);
	assertThat(anim.isFinished(), is(true));
    }

}
