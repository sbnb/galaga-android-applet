package com.gamefreezer.galaga;

import java.util.ArrayList;
import java.util.List;

public class AnimationFrames extends AllocGuard {

    public AnimationFrames(SpriteCache spriteStore) {
	super();
	this.spriteStore = spriteStore;
	// TODO wait till can measure actual GC then look at changing how this
	// works
	// get MAX from config file
	// int MAX = 100;
	// renderTimes = new int[MAX];
	// renderTicks = new int[MAX];
    }

    public AnimationFrames(SpriteCache spriteStore, String imageNames,
	    String renderTimesStr, String renderTicksStr) {
	this(spriteStore);
	reset(imageNames, renderTimesStr, renderTicksStr);
    }

    public void reset(String imageNames, String renderTimesStr,
	    String renderTicksStr) {
	reset(imageNames, renderTimesStr, renderTicksStr, false);
    }

    public void reset(String imageNames, String renderTimesStr,
	    String renderTicksStr, boolean oneShot) {
	// TODO can't just lose array references, they will be GC'd
	// need to reuse the same array, and preallocate the size big enough
	loadSprites(Util.getStringAsList(imageNames));
	renderTimes = Util.getStringAsIntArray(renderTimesStr);
	renderTicks = Util.getStringAsIntArray(renderTicksStr);

	// Reset indexes on arrays and reset time
	reset();
	this.oneShot = oneShot;
    }

    public void reset() {
	// Reset indexes on arrays and reset time
	index = 0;
	framesFirstRenderTime = now();
	ticks = 0;
	finished = false;
    }

    public boolean finished() {
	return finished;
    }

    // note: currently x and y are expected to be pre-translated to screen
    // coordinates
    public void draw(AbstractGraphics graphics, int x, int y) {
	if (finished) {
	    return; // one shot animation over, don't draw anything
	}

	if (currentFrameTimeIsUp()) {
	    framesFirstRenderTime = now();
	    ticks = 0;
	    index++;
	    if (index == sprites.size()) {
		index = 0;
		if (oneShot) {
		    finished = true;
		}
	    }
	}

	if (!finished) {
	    // TODO index out of bounds error here occasionaly, track down
	    sprites.get(index).draw(graphics, x, y);
	    ticks++;
	}

	if (ticks == Integer.MAX_VALUE)
	    ticks = 0;
    }

    private void loadSprites(List<String> imageNames) {
	sprites = new ArrayList<Sprite>();
	for (String name : imageNames) {
	    sprites.add(spriteStore.get(name));
	}
    }

    private boolean currentFrameTimeIsUp() {
	// switch on ticks or renderTimes here

	// renderTimes has precedence if both provided
	if (renderTimes.length > 1) {
	    return currentFrameRenderTime() > renderTimes[index];
	}

	if (renderTicks.length > 1) {
	    return ticks > renderTicks[index];
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

    private List<Sprite> sprites;
    private int[] renderTimes;
    private int[] renderTicks;
    private int index = 0;
    private long framesFirstRenderTime = now();
    private long ticks = 0;
    private boolean oneShot = false;
    private boolean finished = false;
    private SpriteCache spriteStore;
}
