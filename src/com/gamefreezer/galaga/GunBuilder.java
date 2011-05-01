package com.gamefreezer.galaga;

public class GunBuilder {

    private Gun[] guns;
    private int maxBulletsOnScreen;
    private MyProperties props;
    private SpriteCache spriteCache;
    private Screen screen;

    public GunBuilder(MyProperties props, Screen screen, SpriteCache spriteCache) {
	this.props = props;
	this.spriteCache = spriteCache;
	this.screen = screen;

	String[] gunRefs = props.getStringArray("GUN_REFS");
	guns = new Gun[gunRefs.length];
	maxBulletsOnScreen = 0;

	for (int i = 0; i < guns.length; i++) {
	    final String gunName = gunRefs[i];
	    final int bulletsOnScreen = props.getInt( //
		    gunName + "_BULLETS_ON_SCREEN");
	    maxBulletsOnScreen = Math.max(maxBulletsOnScreen, bulletsOnScreen);

	    AnimationPool bulletPool = buildBulletPool(gunName, bulletsOnScreen);
	    AnimationPool hitPool = buildHitPool(gunName, bulletsOnScreen);
	    Animation firingAnim = buildFiringAnim(gunName);
	    StatusBar statusBar = buildStatusBar();

	    guns[i] = new Gun( //
		    props.getInt(gunName + "_BULLET_SPEED"), //
		    props.getInt(gunName + "_RATE_OF_FIRE"), //
		    props.getInt(gunName + "_DAMAGE"), //
		    props.getInt(gunName + "_HEAT_INCREMENT"), //
		    props.getInt(gunName + "_COOLING"), //
		    bulletPool, hitPool, firingAnim, statusBar);
	}
    }

    public Gun[] getGuns() {
	return guns;
    }

    public int getMaxBulletsOnScreen() {
	return maxBulletsOnScreen;
    }

    private AnimationPool buildBulletPool(String gunName, int bulletsOnScreen) {
	final AnimationSource bulletAnimSrc = new AnimationSource( //
		props.getStringArray(gunName + "_BULLET_IMAGES"), //
		props.getIntArray(gunName + "_BULLET_TIMES"));
	AnimationPool bulletAnimPool = new AnimationPool("bullets",
		spriteCache, bulletAnimSrc, bulletsOnScreen, false);
	return bulletAnimPool;
    }

    private AnimationPool buildHitPool(String gunName, int bulletsOnScreen) {
	final AnimationSource hitAnimSrc = new AnimationSource( //
		props.getStringArray(gunName + "_HIT_IMAGES"), //
		props.getIntArray(gunName + "_HIT_TIMES"));
	AnimationPool hitAnimPool = new AnimationPool("hits", spriteCache,
		hitAnimSrc, bulletsOnScreen, true);
	return hitAnimPool;
    }

    private Animation buildFiringAnim(String gunName) {
	final AnimationSource firingAnimSrc = new AnimationSource( //
		props.getStringArray(gunName + "_FIRING_IMAGES"), //
		props.getIntArray(gunName + "_FIRING_TIMES"));
	Animation firingAnim = new Animation(spriteCache, firingAnimSrc, true);
	return firingAnim;
    }

    private StatusBar buildStatusBar() {
	Rectangle displayBarRect = new Rectangle(props.getIntArray("HEAT_DIMS"));
	displayBarRect.translate(screen.inGameLeft(), screen.inGameTop());
	StatusBar statusBar = new StatusBar(displayBarRect, 100.0f, //
		props.getColor("HEAT_OUTLINE_COL"), //
		props.getColor("HEAT_PRIMARY_COL"), //
		props.getColor("HEAT_SECONDARY_COL"));
	return statusBar;
    }
}
