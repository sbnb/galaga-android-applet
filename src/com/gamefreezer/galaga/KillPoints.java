package com.gamefreezer.galaga;

public class KillPoints extends AllocGuard {
    private boolean ACTIVE = false;
    private Alien[] aliens;
    private SpriteCache spriteStore;
    private Constants cfg;

    public KillPoints(SpriteCache spriteStore, Constants cfg) {
	super();
	this.spriteStore = spriteStore;
	this.cfg = cfg;
	aliens = new Alien[cfg.KILLPOINTS_TRACKED];
    }

    public void preload() {
	// TODO preload KillPoint images
    }

    public void draw(AbstractGraphics graphics) {
	if (!ACTIVE) {
	    return;
	}
	for (int i = 0; i < aliens.length; i++) {
	    if (aliens[i] != null) {
		// TODO create the imgs needed for these points
		// (5,10,20,30,...etc)
		// TODO zoom effects - should use AnimationFrames, not Sprite
		// only draw some point text (solo? above X?)
		// draw the sprite at alien.location if alien.exploding
		Sprite sprite = spriteStore.get(cfg.NUM_9);
		int x = aliens[i].getX() + aliens[i].getWidth() / 2
			- sprite.width() / 2;
		int y = aliens[i].getY() + aliens[i].getHeight() / 2
			- sprite.height() / 2;
		sprite.draw(graphics, x, y);
	    }
	}
    }

    public void add(Alien alien) {
	for (int i = 0; i < aliens.length; i++) {
	    if (aliens[i] == null) {
		aliens[i] = alien;
		break;
	    }
	    // if no slots free no score displayed
	}
    }

    public void clear() {
	for (int i = 0; i < aliens.length; i++) {
	    if (aliens[i] != null && !aliens[i].exploding) {
		aliens[i] = null;
	    }
	}

    }
}
