package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.Animation;
import com.gamefreezer.galaga.AnimationPool;
import com.gamefreezer.galaga.AnimationSource;
import com.gamefreezer.galaga.Bullets;
import com.gamefreezer.galaga.Gun;
import com.gamefreezer.galaga.Location;
import com.gamefreezer.galaga.Rectangle;
import com.gamefreezer.galaga.Screen;
import com.gamefreezer.galaga.SpriteCache;
import com.gamefreezer.galaga.StatusBar;

public class GunTest {

    private Gun gun;
    private int bulletSpeed = -100;
    private int rateOfFire = 0;
    private int damage = 30;
    private int heatIncrement = 10;
    private int cooling = 10;
    private int[] bulletTimes;
    private String[] bulletImages;
    private AnimationSource bulletAnimSrc;
    private Location from = new Location(10, 10);
    private SpriteCache spriteCache;
    private Screen screen;
    private int numBulletsOnScreen = 20;
    private Bullets bullets;
    private StatusBar statusBar;
    private AnimationPool hitAnimPool;
    private int hitPoolSize = 10;
    private int maxFrames = 5;

    @Before
    public void setUp() {
	spriteCache = Helper.buildSpriteCache();
	screen = Helper.buildScreen();
	bulletImages = new String[] { "bullet_1.png", "bullet_2.png" };
	bulletTimes = new int[] { 10, 10 };
	bulletAnimSrc = new AnimationSource(bulletImages, bulletTimes);
	AnimationPool bulletAnimPool = new AnimationPool("bullets",
		spriteCache, maxFrames, bulletAnimSrc, numBulletsOnScreen,
		false);
	hitAnimPool = new AnimationPool("hits", spriteCache, maxFrames,
		bulletAnimSrc, hitPoolSize, true);
	Animation firingAnim = new Animation(spriteCache, maxFrames,
		bulletAnimSrc, true);

	bullets = new Bullets(spriteCache, maxFrames, screen,
		numBulletsOnScreen, bulletAnimSrc);

	statusBar = new StatusBar(new Rectangle(10, 10, 10, 10), 100, null,
		null, null);

	gun = new Gun(bulletSpeed, rateOfFire, damage, heatIncrement, cooling,
		bulletAnimPool, hitAnimPool, firingAnim, statusBar);
    }

    @Test
    public void canOverHeatGun() {
	shootUntilOverHeat();
	assertThat(gun.ready(), is(false));
    }

    @Test
    public void canCoolGunAfterOverHeat() {
	shootUntilOverHeat();
	for (int i = 0; i < 100 / cooling; i++) {
	    gun.cool(1000);
	}
	assertThat(gun.ready(), is(true));
    }

    @Test
    public void hitAnimationsNotTakenWhenBulletFired() {
	gun.shoot(bullets, from);
	assertThat(hitAnimPool.remaining(), is(hitPoolSize));
    }

    @Test
    public void bulletCanGetHitAnimationFromPool() {
	gun.shoot(bullets, from);
	bullets.getArray()[0].getHitAnimation();
	assertThat(hitAnimPool.remaining(), is(hitPoolSize - 1));
    }

    @Test
    public void bulletReturnsHitAnimationOnDeath() {
	gun.shoot(bullets, from);
	bullets.killOnscreenBullets();
	assertThat(hitAnimPool.remaining(), is(hitPoolSize));

    }

    private void shootUntilOverHeat() {
	int shotsBeforeOverHeat = 100 / heatIncrement;
	for (int i = 0; i < shotsBeforeOverHeat; i++) {
	    gun.shoot(bullets, from);
	}
    }
}
