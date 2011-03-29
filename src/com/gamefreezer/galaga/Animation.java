package com.gamefreezer.galaga;

public class Animation extends AllocGuard {

    private Sprite[] sprites;
    private int[] renderTimes;
    private int index = 0;
    private long framesFirstRenderTime = now();
    private boolean oneShot = false;
    private boolean finished = false;
    private SpriteCache spriteStore;

    public Animation(SpriteCache spriteStore) {
	super();
	this.spriteStore = spriteStore;
    }

    public Animation(SpriteCache spriteCache, String[] images,
	    int[] frameTimes, boolean oneShot) {
	this(spriteCache);
	reset(images, frameTimes, oneShot);
    }

    public void reset(String[] imageNames, int[] renderTimes) {
	reset(imageNames, renderTimes, false);
    }

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
	// TODO optimise new away in animations
	// or do not call reset on an animation object?
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

    public Dimension getDimensions() {
	return sprites[0].getDimensions();
    }
}
