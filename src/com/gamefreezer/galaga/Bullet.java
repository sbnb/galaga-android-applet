package com.gamefreezer.galaga;

public class Bullet extends Entity {

    private int damage = 0;
    private Animation hitAnimation;
    private boolean hit = false;

    public Bullet(SpriteCache spriteStore, Screen screen,
	    AnimationSource animationSource) {
	super(spriteStore, screen, new Location(0, 0), 0, 0, animationSource);
	stopIfPartiallyOffScreenTopOrBottom = false;
	killIfPartiallyOffScreen = false;
	killIfCompletelyOffScreen = true;
	this.kill();
    }

    public int damage() {
	return damage;
    }

    /* "Create" a new bullet by reusing a dead one. */
    public void reset(Location startPoint, int yVelocity) {
	moveTo(startPoint);
	regenerate();
	setSpeed(0, yVelocity);
	hit = false;
    }

    /* Reset bullets visual appearance as well as position. */
    public void reset(Location startPoint, int yVelocity, Animation bulletAnim,
	    Animation hitAnim, int damage) {
	reset(startPoint, yVelocity);
	this.damage = damage;
	animation = bulletAnim;
	hitAnimation = hitAnim;
	hit = false;
    }

    @Override
    public void kill() {
	super.kill();
	animation.returnToPool();
	if (hitAnimation != null) {
	    hitAnimation.returnToPool();
	}
    }

    @Override
    public void draw(AbstractGraphics graphics) {
	// TODO Auto-generated method stub
	super.draw(graphics);
	if (hit) {
	    hitAnimation.draw(graphics, getX(), getY());
	}
    }

    public void markAsHit() {
	// TODO Auto-generated method stub
	hit = true;
    }
}
