package com.gamefreezer.galaga;

public class KillPoints extends AllocGuard {
    private Alien[] aliens;
    private SpriteCache spriteStore;
    private Constants cfg;
    private Screen screen;

    public KillPoints(SpriteCache spriteStore, Constants cfg) {
	super();
	this.spriteStore = spriteStore;
	this.cfg = cfg;
	this.screen = cfg.SCREEN;
	aliens = new Alien[cfg.KILLPOINTS_TRACKED];
    }

    public void preload() {
	// TODO preload KillPoint images
    }

    boolean ACTIVE = false;

    public void draw(AbstractGraphics graphics) {
	if (!ACTIVE) {
	    return;
	}
	for (int i = 0; i < aliens.length; i++) {
	    if (aliens[i] != null) {
		// TODO find the sprite needed for these points
		// draw the sprite at alien.location if alien.exploding
		Sprite sprite = spriteStore.get(cfg.NUM_9);
		int x = aliens[i].getX() + aliens[i].getWidth() / 2
			- sprite.getWidth() / 2;
		int y = aliens[i].getY() + aliens[i].getHeight() / 2
			- sprite.getHeight() / 2;
		sprite.draw(graphics, screen.translateX(x), screen
			.translateY(y)
			- sprite.getHeight());
		// TODO zoom effects - should use AnimaitonFrames, not Sprite
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
