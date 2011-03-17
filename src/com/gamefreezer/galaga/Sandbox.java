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
	start = new Location(cfg.SCREEN.inGameLeft() + 20, cfg.SCREEN
		.inGameTop() + 50);
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
	graphics.drawRect(target.getX(), target.getY(), 1, 1);
    }

    public Alien getAlien(Location location) {
	int dx = 0;
	int dy = 0;
	int baseDx = 100;
	int baseDy = 100;
	String[] imgNames = new String[] { "alien_b.png" };
	int points = 10;
	int[] renderTimes = new int[] { 0 };

	return new Alien(spriteStore, cfg.SCREEN, location, dx, dy, baseDx,
		baseDy, points, imgNames, renderTimes);
    }
}
