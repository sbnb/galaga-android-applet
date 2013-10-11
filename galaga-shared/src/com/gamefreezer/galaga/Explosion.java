package com.gamefreezer.galaga;

public class Explosion {

    private Animation animation;
    boolean isAvailable;
    private Entity entity;

    public Explosion(Animation animation) {
	this.animation = animation;
	isAvailable = true;
    }

    public boolean available() {
	return isAvailable;
    }

    public void use(Entity entity) {
	isAvailable = false;
	animation.reset();
	this.entity = entity;
	entity.setExploding(true);
    }

    public void draw(AbstractGraphics graphics) {
	animation.draw(graphics, entity.getX(), entity.getY());
	if (animation.isFinished()) {
	    entity.setExploding(false);
	    entity = null;
	    isAvailable = true;
	}

    }

}
