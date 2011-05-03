package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.applet.galaga.AppletBitmapReader;
import com.gamefreezer.galaga.AbstractBitmapReader;
import com.gamefreezer.galaga.Animation;
import com.gamefreezer.galaga.AnimationPool;
import com.gamefreezer.galaga.AnimationSource;
import com.gamefreezer.galaga.SpriteCache;

public class AnimationPoolTest {

    private SpriteCache spriteCache;
    private AnimationSource src;
    private AnimationPool pool;
    private String[] names;
    private int[] times;
    int size;
    int maxFrames;

    @Before
    public void setUp() {
	AbstractBitmapReader reader = new AppletBitmapReader();
	spriteCache = new SpriteCache(reader);
	names = new String[] { "bullet_1.png", "bullet_2.png" };
	times = new int[] { 10, 10 };
	src = new AnimationSource(names, times);
	size = 10;
	maxFrames = 5;
	pool = new AnimationPool("test", spriteCache, maxFrames, src, size,
		true);
    }

    @Test
    public void canGetAnAnimationFromPool() {
	Animation anim = pool.get();
	assertThat(anim, is(Animation.class));
    }

    @Test
    public void poolSizeDecreasedAfterGettingAnAnimation() {
	pool.get();
	assertThat(pool.remaining(), is(size - 1));
    }

    @Test
    public void canReturnAnimationToPool() {
	Animation anim = pool.get();
	pool.put(anim);
	assertThat(pool.remaining(), is(size));
    }

    @Test
    public void poolReturnsNullWhenEmpty() {
	for (int i = 0; i < size; i++) {
	    assertNotNull(pool.get());
	}
	assertNull("Empty pool", pool.get());
    }
}
