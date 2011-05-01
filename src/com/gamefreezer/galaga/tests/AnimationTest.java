package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.AbstractGraphics;
import com.gamefreezer.galaga.Animation;
import com.gamefreezer.galaga.AnimationSource;

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

    @Test
    public void animationFinishes() {
	int frameTime = 100;
	int[] times = new int[] { frameTime, frameTime };
	AnimationSource src = Helper.buildAnimationSource(times);
	Animation animation = Helper.buildAnimation(src, true);

	animation.rewindFrameStart(System.currentTimeMillis() - frameTime);
	animation.draw(graphics, 0, 0);
	assertThat(animation.isFinished(), is(false));

	animation.rewindFrameStart(System.currentTimeMillis() - frameTime);
	animation.draw(graphics, 0, 0);
	assertThat(animation.isFinished(), is(true));
    }

}
