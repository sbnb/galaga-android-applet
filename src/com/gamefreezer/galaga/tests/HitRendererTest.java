package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.AbstractGraphics;
import com.gamefreezer.galaga.Animation;
import com.gamefreezer.galaga.AnimationPool;
import com.gamefreezer.galaga.HitRenderer;
import com.gamefreezer.galaga.Location;

public class HitRendererTest {

    HitRenderer hitRenderer;
    Animation hitAnim;
    AnimationPool pool;
    AbstractGraphics graphics;
    int rendererSize = 10;
    int poolSize = 10;
    Location origin;
    int maxFrames = 5;

    @Before
    public void setUp() throws Exception {
	hitRenderer = new HitRenderer(rendererSize);
	hitAnim = Helper.buildAnimation(true);
	pool = new AnimationPool("hits", Helper.buildSpriteCache(), maxFrames,
		Helper.buildAnimationSource(), poolSize, true);
	graphics = new MockGraphics();
	origin = new Location();
    }

    @Test
    public void canAddNewHit() {
	assertThat(hitRenderer.currentRenders(), is(0));
	hitRenderer.registerHit(hitAnim, new Location(), origin);
	assertThat(hitRenderer.currentRenders(), is(1));
    }

    @Test
    public void extraHitsIgnoredSafely() {
	for (int idx = 0; idx < rendererSize; idx++) {
	    hitRenderer
		    .registerHit(Helper.buildAnimation(true), origin, origin);
	}
	assertThat("pre", hitRenderer.currentRenders(), is(rendererSize));
	hitRenderer.registerHit(Helper.buildAnimation(true), origin, origin);
	assertThat("post", hitRenderer.currentRenders(), is(rendererSize));
    }

    @Test
    public void hitsNulledAfterPlaying() {
	hitRenderer.registerHit(hitAnim, origin, origin);
	hitRenderer.draw(graphics, origin);
	hitRenderer.draw(graphics, origin);
	assertThat(hitRenderer.currentRenders(), is(0));
    }

    @Test
    public void hitsReturnedToPoolAfterPlaying() {
	hitRenderer.registerHit(pool.get(), origin, origin);
	assertThat(pool.remaining(), is(poolSize - 1));
	hitRenderer.draw(graphics, origin);
	hitRenderer.draw(graphics, origin);
	assertThat(pool.remaining(), is(poolSize));
    }
}
