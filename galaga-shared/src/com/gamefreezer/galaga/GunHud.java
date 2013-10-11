package com.gamefreezer.galaga;

public class GunHud {

    private Animation hudAnimation;
    private CartesianInt topLeft;

    public GunHud(CartesianInt topLeft) {
	this.topLeft = topLeft;
    }

    public void draw(AbstractGraphics graphics) {
	if (hudAnimation != null) {
	    hudAnimation.draw(graphics, topLeft.x, topLeft.y);
	    if (hudAnimation.isFinished()) {
		hudAnimation = null;
	    }
	}
    }

    public void setAnimation(Animation newHudAnimation) {
	hudAnimation = newHudAnimation;
	hudAnimation.reset();
    }

}
