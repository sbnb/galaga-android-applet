package com.gamefreezer.galaga;

public class Explosions {
    private SpriteCache spriteCache;
    private Explosion[] mExplosions;
    private AnimationSource explosionSrc;
    private int maxExplosions;
    private int maxFrames;

    public Explosions(SpriteCache spriteCache, int maxFrames,
	    AnimationSource explosionSrc, int maxExplosions) {
	this.spriteCache = spriteCache;
	this.maxFrames = maxFrames;
	this.explosionSrc = explosionSrc;
	this.maxExplosions = maxExplosions;
	initExplosions();
    }

    // TODO different explosions, even just simple color shifts
    private void initExplosions() {
	mExplosions = new Explosion[maxExplosions];
	for (int i = 0; i < mExplosions.length; i++) {
	    Animation animation = new Animation(spriteCache, maxFrames,
		    explosionSrc, true);
	    mExplosions[i] = new Explosion(animation);
	}
    }

    public void newExplosion(Entity entity) {
	Explosion explosion = findAvailableExplosion();
	if (explosion != null) {
	    explosion.use(entity);
	} else {
	    // too many explosions, drop this one
	    assert false : "No free explosions, increase pool size!";
	}
    }

    private Explosion findAvailableExplosion() {
	for (int i = 0; i < mExplosions.length; i++) {
	    if (mExplosions[i].available()) {
		return mExplosions[i];
	    }
	}
	return null;
    }

    public void draw(AbstractGraphics graphics) {
	for (int i = 0; i < mExplosions.length; i++) {
	    if (!mExplosions[i].available()) {
		mExplosions[i].draw(graphics);
	    }
	}
    }

}
