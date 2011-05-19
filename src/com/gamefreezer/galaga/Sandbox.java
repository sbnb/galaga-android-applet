package com.gamefreezer.galaga;

public class Sandbox extends AllocGuard {

    private SpriteCache spriteCache;
    private Alien alien;
    private int rotation = 0;
    private int rotationInterval = 15;
    private Location start;
    private Screen screen;
    private Location center;
    private long frames = 0;

    public Sandbox(SpriteCache spriteCache, Screen screen) {
	super();
	this.spriteCache = spriteCache;
	this.screen = screen;
	start = new Location(screen.inGameLeft(), screen.inGameTop() - 10);

	RotationMapper rotationMapper = new RotationMapper(15f);
	RotationSprites rotationSprites = new RotationSprites(spriteCache,
		"smiley", ".png", rotationMapper);
	rotationSprites.preload(spriteCache);
	alien = getAlien(start);
	alien.setRotationSprites(rotationSprites);
	center = new Location(screen.middleX(), screen.middleY());
	alien.moveCenterTo(center);
	alien.moveBy(50, 50);

    }

    float totRotRad = 0f;

    public void update(int delta) {
	// alien.move(delta);

	// if (frames % 100 == 0) {
	// rotation += rotationInterval;
	// rotation = rotation % 360;
	// // alien.rotate(center, rotationInterval);
	// alien.rotateSelf(rotation);
	//
	// }
	alien.rotate(center, 0.001f);
	totRotRad += 0.001f;
	if (totRotRad >= 2 * Math.PI) {
	    totRotRad = 0f;
	}
	alien.setRotation((float) Math.toDegrees(totRotRad));
	frames++;
    }

    public void draw(AbstractGraphics graphics) {
	alien.draw(graphics);
    }

    public Alien getAlien(Location location) {
	int dx = 0;
	int dy = 0;
	int baseDx = 100;
	int baseDy = 100;
	String[] imgNames = new String[] { "smiley000.png" };
	int[] renderTimes = new int[] { 0 };
	int points = 10;
	int maxFrames = 5;

	AnimationSource animSrc = new AnimationSource(imgNames, renderTimes);
	Animation anim = new Animation(spriteCache, maxFrames, animSrc, false);

	Alien a = new Alien(anim, screen, location, dx, dy, baseDx, baseDy,
		points);
	return a;
    }
}
