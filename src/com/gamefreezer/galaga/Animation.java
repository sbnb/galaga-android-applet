package com.gamefreezer.galaga;

public class Animation extends AllocGuard {

    private Sprite[] sprites;
    private int[] frameTimes;
    private int idx = 0;
    private long frameStart = now();
    private boolean oneShot = false;
    private boolean finished = false;
    private SpriteCache spriteStore;
    private AnimationPool pool;

    public Animation(SpriteCache spriteCache) {
	super();
	this.spriteStore = spriteCache;
    }

    public Animation(SpriteCache spriteCache, AnimationSource animationSource,
	    boolean oneShot) {
	this(spriteCache);
	reset(animationSource, oneShot);
    }

    public Animation(SpriteCache spriteCache, AnimationSource src,
	    boolean oneShot, AnimationPool pool) {
	this(spriteCache, src, oneShot);
	this.pool = pool;
    }

    public void reset(AnimationSource animationSource, boolean oneShot) {
	loadSprites(animationSource.names);
	this.frameTimes = animationSource.times;
	reset();
	this.oneShot = oneShot;
    }

    public void reset(AnimationSource animationSource) {
	reset(animationSource, false);
    }

    public void reset() {
	// Reset indexes on arrays and reset time
	idx = 0;
	frameStart = now();
	finished = false;
    }

    public boolean isFinished() {
	return finished;
    }

    /* Not every animation must have a pool. */
    public void returnToPool() {
	if (pool != null) {
	    pool.put(this);
	}
    }

    public void draw(AbstractGraphics graphics, int x, int y) {
	if (finished) {
	    // TODO return to pool?
	    return; // one shot animation over, don't draw anything
	}

	if (currentFrameFinished()) {
	    changeFrames();
	}

	if (!finished) {
	    sprites[idx].draw(graphics, x, y);
	}
    }

    private boolean currentFrameFinished() {
	if (frameTimes == null || frameTimes.length <= 1) {
	    return false; // if no times given always stay on frame 1
	}
	return now() - frameStart > frameTimes[idx];
    }

    private void changeFrames() {
	frameStart = now();
	idx++;
	if (idx == sprites.length) {
	    idx = 0;
	    finished = oneShot ? true : false;
	}
    }

    private void loadSprites(String[] imageNames) {
	// TODO optimise new away in animations
	// or do not call reset on an animation object?
	sprites = new Sprite[imageNames.length];
	for (int i = 0; i < imageNames.length; i++) {
	    sprites[i] = spriteStore.get(imageNames[i]);
	}
    }

    private long now() {
	return System.currentTimeMillis();
    }

    public Dimension getDimensions() {
	return sprites[0].getDimensions();
    }

    public int height() {
	return sprites[0].height();
    }

    public int width() {
	return sprites[0].width();
    }

}
