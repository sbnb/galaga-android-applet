package com.gamefreezer.galaga;

public class Sandbox extends AllocGuard {

    private SpriteCache spriteCache;
    private Alien alien;
    int rotation = 0;
    int rotationInterval = 15;
    Location start;
    @SuppressWarnings("unused")
    private Location target;
    private Screen screen;
    @SuppressWarnings("unused")
    private Animation rotatingSquare;
    private Location rsPoint;
    private long frames = 0;

    public Sandbox(SpriteCache spriteCache, Screen screen) {
	super();
	this.spriteCache = spriteCache;
	this.screen = screen;
	start = new Location(screen.inGameLeft(), screen.inGameTop() - 10);
	target = new Location(200, 200);

	RotationMapper rotationMapper = new RotationMapper(15f);
	RotationSprites rotationSprites = new RotationSprites(spriteCache,
		"smiley", ".png", rotationMapper);
	rotationSprites.preload(spriteCache);
	alien = getAlien(start);
	alien.setRotationSprites(rotationSprites);

	String[] squares = new String[] { "square_1.png", "square_2.png",
		"square_3.png", "square_4.png", "square_5.png", "square_6.png" };
	int fTime = 90;
	int[] squareTimes = new int[] { 5000, fTime, fTime, fTime, fTime, fTime };
	AnimationSource animSrc = new AnimationSource(squares, squareTimes);
	rotatingSquare = new Animation(spriteCache, 6, animSrc, false);
	rsPoint = new Location(screen.inGameLeft(), screen.inGameTop() - 10);
    }

    public void update(int delta) {
	alien.move(delta);
	alien.rotateSelf(rotation);

	if (frames % 100 == 0) {
	    rotation += rotationInterval;
	    rotation = rotation % 360;
	}
	rsPoint.moveBy(0.01f, 0.01f);
	frames++;
    }

    public void draw(AbstractGraphics graphics) {
	alien.draw(graphics);
	// graphics.drawRect(target.getX(), target.getY(), 1, 1);
	// rotatingSquare.draw(graphics, rsPoint);
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
	return new Alien(anim, screen, location, dx, dy, baseDx, baseDy, points);
    }
}
