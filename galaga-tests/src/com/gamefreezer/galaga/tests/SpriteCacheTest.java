package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.applet.galaga.AppletBitmapReader;
import com.gamefreezer.galaga.SpriteCache;

public class SpriteCacheTest {

    SpriteCache spriteCache;
    AppletBitmapReader bitmapReader = new AppletBitmapReader();
    String firstImage = "alien_a.png";
    String secondImage = "alien_b.png";

    @Before
    public void setUp() {
	spriteCache = Helper.buildSpriteCache();
    }

    @Test
    public void canStoreTwoImages() {
	assertThat(spriteCache.size(), is(0));
	spriteCache.get(firstImage);
	spriteCache.get(secondImage);
	assertThat(spriteCache.size(), is(2));
    }

    @Test
    public void secondGetUsesCache() {
	assertThat(spriteCache.size(), is(0));
	spriteCache.get(firstImage);
	spriteCache.get(firstImage);
	assertThat(spriteCache.size(), is(1));
    }
}
