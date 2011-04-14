package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.AbstractGraphics;
import com.gamefreezer.galaga.Alien;
import com.gamefreezer.galaga.AnimationPool;
import com.gamefreezer.galaga.AnimationSource;
import com.gamefreezer.galaga.Location;
import com.gamefreezer.galaga.SpriteCache;

public class AlienTest {
    private AbstractGraphics graphics;
    private Alien alien;
    private AnimationPool pool;
    int size;

    @Before
    public void setUp() {
	graphics = new MockGraphics();
	size = 10;
	int hitRendererPoolSize = 10;
	SpriteCache spriteCache = Helper.buildSpriteCache();
	AnimationSource src = Helper.buildAnimationSource();
	pool = new AnimationPool("test", spriteCache, Helper
		.buildAnimationSource(), size, true);
	alien = new Alien(spriteCache, null, null, hitRendererPoolSize);
	alien.setImagePath(src);
    }

    @Test
    public void registerHitUsesPool() {
	alien.registerHit(pool.get(), new Location());
	assertThat(pool.remaining(), is(size - 1));
    }

    @Test
    public void hitAnimationReturnedToPoolWhenKilled() {
	alien.registerHit(pool.get(), new Location());
	alien.kill();
	assertThat(pool.remaining(), is(size));
    }

    @Test
    public void hitAnimationReturnedToPoolWhenFinishedPlaying() {
	alien.registerHit(pool.get(), new Location());
	alien.draw(graphics);
	alien.draw(graphics);
	assertThat(pool.remaining(), is(size));
    }

    @Test
    public void exhaustingHitAnimationPoolIsNotFatal() {
	assignAllHitAnimsToAlien();
	alien.registerHit(pool.get(), new Location());
	assertThat(pool.remaining(), is(0));
    }

    @Test
    public void multipleHitAnimsReleasedOnAlienDeath() {
	assignAllHitAnimsToAlien();
	alien.kill();
	assertThat(pool.remaining(), is(size));
    }

    private void assignAllHitAnimsToAlien() {
	for (int count = 0; count < size; count++) {
	    alien.registerHit(pool.get(), new Location());
	}
    }

}
