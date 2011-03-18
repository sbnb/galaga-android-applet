package com.gamefreezer.galaga;

public class Explosions {
    private Constants cfg;
    private SpriteCache spriteCache;
    private Explosion[] mExplosions;

    public Explosions(SpriteCache spriteCache, Constants cfg) {
	this.spriteCache = spriteCache;
	this.cfg = cfg;
	initExplosions();
    }

    // TODO different explosions, even just simple color shifts
    private void initExplosions() {
	mExplosions = new Explosion[cfg.MAX_EXPLOSIONS];
	for (int i = 0; i < mExplosions.length; i++) {
	    mExplosions[i] = new Explosion(spriteCache, cfg.EXPL_IMGS,
		    cfg.EXPL_TIMES);
	}
    }

    public void newExplosion(Entity entity) {
	Explosion explosion = findAvailableExplosion();
	assert explosion != null : "No free explosions, increase pool size!";
	if (explosion != null) {
	    explosion.use(entity);
	}
	// too many explosions, drop this one
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
