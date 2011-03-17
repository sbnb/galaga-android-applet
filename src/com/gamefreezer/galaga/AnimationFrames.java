package com.gamefreezer.galaga;

public class AnimationFrames extends AllocGuard {

    private Sprite[] sprites;
    private int[] renderTimes;
    private int index = 0;
    private long framesFirstRenderTime = now();
    private boolean oneShot = false;
    private boolean finished = false;
    private SpriteCache spriteStore;

    public AnimationFrames(SpriteCache spriteStore) {
	super();
	this.spriteStore = spriteStore;
    }

    public void reset(String[] imageNames, int[] renderTimes) {
	reset(imageNames, renderTimes, false);
    }

    // TODO an explosion shouldn't be reset into existing animation - use a
    // constant(s) animation
    public void reset(String[] imageNames, int[] renderTimes, boolean oneShot) {
	loadSprites(imageNames);
	this.renderTimes = renderTimes;

	// Reset indexes on arrays and reset time
	reset();
	this.oneShot = oneShot;
    }

    public void reset() {
	// Reset indexes on arrays and reset time
	index = 0;
	framesFirstRenderTime = now();
	finished = false;
    }

    public boolean finished() {
	return finished;
    }

    public void draw(AbstractGraphics graphics, int x, int y) {
	if (finished) {
	    return; // one shot animation over, don't draw anything
	}

	if (currentFrameTimeIsUp()) {
	    framesFirstRenderTime = now();
	    index++;
	    if (index == sprites.length) {
		index = 0;
		if (oneShot) {
		    finished = true;
		}
	    }
	}

	if (!finished) {
	    sprites[index].draw(graphics, x, y);
	}
    }

    private void loadSprites(String[] imageNames) {
	sprites = new Sprite[imageNames.length];
	for (int i = 0; i < imageNames.length; i++) {
	    sprites[i] = spriteStore.get(imageNames[i]);
	}
    }

    private boolean currentFrameTimeIsUp() {
	if (renderTimes != null && renderTimes.length > 1) {
	    return currentFrameRenderTime() > renderTimes[index];
	}
	// if no times given always stay on frame 1
	return false;
    }

    private long currentFrameRenderTime() {
	return now() - framesFirstRenderTime;
    }

    private long now() {
	return System.currentTimeMillis();
    }
}
