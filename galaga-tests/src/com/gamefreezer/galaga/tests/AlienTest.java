package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.AbstractGraphics;
import com.gamefreezer.galaga.Alien;
import com.gamefreezer.galaga.Animation;
import com.gamefreezer.galaga.AnimationPool;
import com.gamefreezer.galaga.AnimationSource;
import com.gamefreezer.galaga.Location;
import com.gamefreezer.galaga.SpriteCache;

public class AlienTest {
    private AbstractGraphics graphics;
    private Alien alien;
    int width = 10;
    int height = 10;
    private AnimationPool pool;
    int size;
    int maxFrames;
    Location center;

    @Before
    public void setUp() {
	graphics = new MockGraphics();
	size = 10;
	maxFrames = 5;
	int hitRendererPoolSize = 10;
	SpriteCache spriteCache = Helper.buildSpriteCache();
	AnimationSource src = Helper.buildAnimationSource();
	pool = new AnimationPool("test", spriteCache, maxFrames, Helper
		.buildAnimationSource(), size, true);
	Animation animation = new Animation(spriteCache, maxFrames);
	alien = new Alien(animation, null, null, hitRendererPoolSize);
	alien.setDimensions(width, height);
	alien.setImagePath(src);
	center = new Location();
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

    @Test
    public void canGetCenter() {
	alien.moveTo(10, 10);
	alien.setToCenter(center);
	assertThat(center.getX(), is(15));
	assertThat(center.getY(), is(15));
    }

    @Test
    public void canGetCenterOddPixels() {
	alien.setDimensions(5, 5);
	alien.setToCenter(center);
	assertThat(center.getX(), is(3));
	assertThat(center.getY(), is(3));
    }

    @Test
    public void canMoveCenterToAPoint() {
	Location point = new Location(100, 100);
	alien.setDimensions(10, 10);
	alien.moveCenterTo(point);
	assertThat(alien.getX(), is(95));
	assertThat(alien.getY(), is(95));

    }

    @Test
    public void mulipleSetRotationsStaysWithinErrorBounds() {
	Location origin = new Location(0, 0);
	Location north = new Location(0, 10);
	float radians = (float) Math.PI / 2;
	alien.moveCenterTo(north);

	float totRotRad = 0f;
	while (totRotRad < 2 * Math.PI) {
	    totRotRad += 0.001f;
	}
	System.out.println("totRotRad: " + totRotRad);
	System.out.println("2 * PI: " + 2 * Math.PI);
	alien.rotate(origin, radians);

	// rotate self 90 degs in radians pi/2
	alien.setRotation((float) Math.toDegrees(Math.PI * 2));
	System.out.println(alien.getRotation());
    }

    private void assignAllHitAnimsToAlien() {
	for (int count = 0; count < size; count++) {
	    alien.registerHit(pool.get(), new Location());
	}
    }

}
