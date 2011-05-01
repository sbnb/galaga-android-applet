package com.gamefreezer.galaga;

public class FixedAnimation {

    private Animation animation;
    private CartesianInt coords;

    public FixedAnimation(Animation animation, CartesianInt coords) {
	this.animation = animation;
	this.coords = coords;
    }

    public static FixedAnimation build(SpriteCache spriteCache,
	    AnimationSource animSrc, int[] coords) {
	return new FixedAnimation(new Animation(spriteCache, animSrc, true),
		new CartesianInt(coords));
    }

    public void draw(AbstractGraphics graphics) {
	animation.draw(graphics, coords.x, coords.y);
    }

    public boolean isFinished() {
	return animation.isFinished();
    }

    public void reset() {
	animation.reset();
    }

    public void rewindFrameStart(long toTime) {
	animation.rewindFrameStart(toTime);
    }
}
