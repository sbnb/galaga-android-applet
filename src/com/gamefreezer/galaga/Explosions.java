package com.gamefreezer.galaga;

public class Explosions {
    private Constants cfg;
    private SpriteCache spriteCache;
    private Explosion[] mExplosions;

    // TODO pass in at is needed, lose config from Explosions
    public Explosions(SpriteCache spriteCache, Constants cfg) {
	this.spriteCache = spriteCache;
	this.cfg = cfg;
	initExplosions();
    }

    // TODO different explosions, even just simple color shifts
    private void initExplosions() {
	mExplosions = new Explosion[cfg.MAX_EXPLOSIONS];
	for (int i = 0; i < mExplosions.length; i++) {
	    mExplosions[i] = new Explosion(spriteCache, cfg.EXPL_ANIM_SRC);
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
