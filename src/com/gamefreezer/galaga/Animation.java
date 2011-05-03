package com.gamefreezer.galaga;

public class Animation extends AllocGuard {

    private Sprite[] sprites;
    private int[] frameTimes;
    private int curFrameIdx = 0;
    private int frameLength;
    private long frameStart = now();
    private boolean oneShot = false;
    private boolean finished = false;
    private SpriteCache spriteCache;
    private AnimationPool pool;
    private AnimationSource animationSource;

    public Animation(SpriteCache spriteCache, int maxFrames) {
	super();
	this.spriteCache = spriteCache;
	sprites = new Sprite[maxFrames];
    }

    public Animation(SpriteCache spriteCache, int maxFrames,
	    AnimationSource animationSource, boolean oneShot) {
	this(spriteCache, maxFrames);
	reset(animationSource, oneShot);
    }

    public Animation(SpriteCache spriteCache, int maxFrames,
	    AnimationSource src, boolean oneShot, AnimationPool pool) {
	this(spriteCache, maxFrames, src, oneShot);
	this.pool = pool;
    }

    public void reset(AnimationSource animationSource, boolean oneShot) {
	loadSprites(animationSource.names);
	this.frameTimes = animationSource.times;
	this.animationSource = animationSource;
	reset();
	this.oneShot = oneShot;
    }

    public void reset(AnimationSource animationSource) {
	reset(animationSource, false);
    }

    public void reset() {
	// Reset indexes on arrays and reset time
	curFrameIdx = 0;
	frameStart = now();
	finished = false;
    }

    public void reset(boolean oneShot) {
	reset();
	this.oneShot = oneShot;
    }

    public boolean isFinished() {
	return finished;
    }

    public void rewindFrameStart(long toTime) {
	frameStart = toTime;
    }

    /* Not every animation must have a pool. */
    public void returnToPool() {
	if (pool != null) {
	    pool.put(this);
	}
    }

    public int poolSize() {
	if (pool != null) {
	    return pool.remaining();
	}
	return -1;
    }

    public void draw(AbstractGraphics graphics, Location loc) {
	draw(graphics, loc.getX(), loc.getY());
    }

    public void draw(AbstractGraphics graphics, int x, int y) {
	if (finished) {
	    return; // one shot animation over, don't draw anything
	}

	if (currentFrameFinished()) {
	    changeFrames();
	}

	if (!finished) {
	    sprites[curFrameIdx].draw(graphics, x, y);
	}
    }

    private boolean currentFrameFinished() {
	if (frameTimes == null || frameTimes.length <= 1) {
	    return false; // if no times given always stay on frame 1
	}
	return now() - frameStart >= frameTimes[curFrameIdx];
    }

    private void changeFrames() {
	frameStart = now();
	curFrameIdx++;
	// if (curFrameIdx == sprites.length) {
	if (curFrameIdx == frameLength) {
	    curFrameIdx = 0;
	    finished = oneShot ? true : false;
	}
    }

    private void loadSprites(String[] imageNames) {
	assert imageNames.length <= sprites.length : "animation too long! "
		+ imageNames.length;
	frameLength = imageNames.length;
	for (int idx = 0; idx < imageNames.length; idx++) {
	    sprites[idx] = spriteCache.get(imageNames[idx]);
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

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("[Animation: ");
	sb.append(animationSource.toString());
	sb.append(pool == null ? "pool:null" : "pool:set");
	sb.append(" oneShot:" + oneShot);
	sb.append(" finished:" + finished);
	sb.append(" idx:" + curFrameIdx);
	sb.append("]");
	return sb.toString();
    }
}
