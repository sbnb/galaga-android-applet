package com.gamefreezer.galaga;

public class Sandbox extends AllocGuard {

    private Constants cfg;
    private SpriteCache spriteStore;
    private Alien alien;
    Location start;
    private Location target;

    // private Controller controller;

    public Sandbox(SpriteCache spriteStore, Constants cfg) {
	super();
	this.spriteStore = spriteStore;
	this.cfg = cfg;
	start = new Location(cfg.SCREEN.left() + 20, cfg.SCREEN.height() - 50);
	target = new Location(200, 200);
	// controller = new Controller(cfg.SCREEN, cfg.STAY_SOLO);
	alien = getAlien(start);
    }

    public void update(int delta) {
	// TODO resolve usage of adjust speed - it now lives in movement
	// controller.adjustSpeed(alien.getSpeed(), alien.getLocation(),
	// target);
	alien.move(delta);
    }

    public void draw(AbstractGraphics graphics) {
	alien.draw(graphics);
	graphics.drawRect(cfg.SCREEN.translateX(target.getX()), cfg.SCREEN
		.translateY(target.getY()), 1, 1);
    }

    public Alien getAlien(Location location) {
	int dx = 0;
	int dy = 0;
	int baseDx = 100;
	int baseDy = 100;
	String imgPath = "alien_b.png";
	int points = 10;
	String renderTimes = "";
	String renderTicks = "";

	return new Alien(spriteStore, cfg.SCREEN, location, dx, dy, baseDx,
		baseDy, imgPath, points, renderTimes, renderTicks);
    }
}
