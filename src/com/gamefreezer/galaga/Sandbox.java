package com.gamefreezer.galaga;

public class Sandbox extends AllocGuard {

    private SpriteCache spriteCache;
    private Alien alien;
    Location start;
    @SuppressWarnings("unused")
    private Location target;
    private Screen screen;

    public Sandbox(SpriteCache spriteStore, Screen screen) {
	super();
	this.spriteCache = spriteStore;
	this.screen = screen;
	start = new Location(screen.inGameLeft(), screen.inGameTop() - 10);
	target = new Location(200, 200);
	alien = getAlien(start);
    }

    public void update(int delta) {
	alien.move(delta);
    }

    public void draw(@SuppressWarnings("unused") AbstractGraphics graphics) {
	// alien.draw(graphics);
	// graphics.drawRect(target.getX(), target.getY(), 1, 1);
    }

    public Alien getAlien(Location location) {
	int dx = 0;
	int dy = 0;
	int baseDx = 100;
	int baseDy = 100;
	String[] imgNames = new String[] { "alien_b.png" };
	int[] renderTimes = new int[] { 0 };
	int points = 10;
	int maxFrames = 5;

	AnimationSource animSrc = new AnimationSource(imgNames, renderTimes);
	Animation anim = new Animation(spriteCache, maxFrames, animSrc, false);
	return new Alien(anim, screen, location, dx, dy, baseDx, baseDy, points);
    }
}
