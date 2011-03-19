package com.gamefreezer.galaga;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.gamefreezer.utilities.Profiler;

public class Game extends AllocGuard {

    private static boolean PROFILING = true;

    // TODO investigate making this instance variables, not static
    private static AbstractLog log = null;
    private static AbstractColor colorDecoder = null;
    private static AbstractFileOpener fileOpener = null;
    private static AbstractFileLister fileLister = null;

    // objects used in the game
    private SpriteCache spriteCache;
    private State state;
    private List<Formation> formations;
    private Ship ship = null;
    private Bullets playerBullets = null;
    private Bullets alienBullets = null;
    private Aliens aliens;
    private CollisionDetector collisionDetector;
    private Sandbox sandbox;
    private Score score;
    private HealthBar healthBar;
    private long cycles = 0; // elapsed cycles during game
    private long lastTime = 0;
    private AnimationFrames textFx;
    private AnimationFrames countDown;
    private AnimationFrames shipExplosion;
    private Explosions explosions;
    private KillPoints killPoints;

    private Constants cfg;
    private Screen screen;

    private ArrayBlockingQueue<InputMessage> inputQueue = new ArrayBlockingQueue<InputMessage>(
	    InputMessage.INPUT_QUEUE_SIZE);
    private Buttons buttons;
    private Borders borders;
    private Object inputQueueMutex = new Object();

    public Game(Constants cfg, AbstractLog log,
	    AbstractBitmapReader bitmapReader, AbstractColor colorDecoder,
	    AbstractFileOpener fileOpener, AbstractFileLister fileLister) {
	super();
	this.cfg = cfg;
	screen = cfg.SCREEN;
	setAbstractInterfaceVars(log, colorDecoder, fileOpener, fileLister);

	log("Game(): constructor.");

	spriteCache = new SpriteCache(bitmapReader);
	explosions = new Explosions(spriteCache, cfg);
	collisionDetector = new CollisionDetector(cfg, explosions);
	sandbox = new Sandbox(spriteCache, cfg);
	score = new Score(spriteCache, cfg);
	healthBar = new HealthBar(cfg, score);
	textFx = new AnimationFrames(spriteCache);
	countDown = new AnimationFrames(spriteCache);
	shipExplosion = new AnimationFrames(spriteCache);
	killPoints = new KillPoints(spriteCache, cfg);

	formations = FormationsFactory.createFormations(spriteCache, cfg);
	final Speed targettingSpeed = new Speed(cfg.RETURN_SPEED_X,
		cfg.RETURN_SPEED_Y);
	aliens = new Aliens(spriteCache, cfg, targettingSpeed);
	playerBullets = new Bullets(spriteCache, screen, cfg.BULLETS_ON_SCREEN,
		cfg.BULLET_IMAGES, cfg.BULLET_TIMES);
	alienBullets = new Bullets(spriteCache, screen,
		cfg.ALIEN_BULLETS_ON_SCREEN, cfg.ALIEN_BULLET_IMAGES,
		cfg.ALIEN_BULLET_TIMES);
	state = new State(cfg, aliens, formations, score, playerBullets,
		alienBullets, shipExplosion, countDown, textFx);

	final Speed RIGHT_SPEED = new Speed(cfg.SHIP_MOVEMENT, 0);
	final Speed LEFT_SPEED = new Speed(-cfg.SHIP_MOVEMENT, 0);
	final Speed NO_SPEED = new Speed(0, 0);
	final Gun gun = new Gun(cfg.MIN_TIME_BETWEEN_BULLETS,
		cfg.BULLET_MOVEMENT);
	ship = new Ship(spriteCache, screen, cfg.SHIP_IMAGES, cfg.SHIP_TIMES,
		gun, RIGHT_SPEED, LEFT_SPEED, NO_SPEED);

	preloadImages();
	buttons = new Buttons(cfg);
	borders = new Borders(cfg);

	AllocGuard.guardOn = true;
	log("SpriteStore.size(): " + spriteCache.size());
    }

    public static void setAbstractInterfaceVars(AbstractLog log,
	    AbstractColor colorDecoder, AbstractFileOpener fileOpener,
	    AbstractFileLister fileLister) {
	Game.log = log;
	Game.colorDecoder = colorDecoder;
	Game.fileOpener = fileOpener;
	Game.fileLister = fileLister;
    }

    public static String[] listFiles() {
	assert fileLister != null : "Game.fileLister is null!";
	return fileLister.list();
    }

    public static InputStream openFile(String name) {
	assert fileOpener != null : "Game.fileOpener is null!";
	return fileOpener.open(name);
    }

    public static AbstractColor decodeColor(String property) {
	assert colorDecoder != null : "Game.colorDecoder is null!";
	return colorDecoder.decode(property);
    }

    public static void log(String tag, String message) {
	assert Game.log != null : "Game.log is null!";
	Game.log.i(tag, message);
    }

    public static void log(String message) {
	assert Game.log != null : "Game.log is null!";
	Game.log.i(message);
    }

    public void update() {
	startProfiler("Game.update");

	int timeDelta = getTimeDelta();
	state.update();
	processInput();

	if (state.current() == cfg.PLAYING_STATE) {
	    updatePausablePhysics(timeDelta);
	    updateUnpausablePhysics(timeDelta);
	    collisionDetector.checkCollisions(aliens, ship, score,
		    playerBullets, alienBullets, killPoints);
	    sandbox.update(timeDelta);
	} else {
	    updateUnpausablePhysics(timeDelta);
	}

	if (state.current() == cfg.BONUS_PAYOUT_STATE && score.bonusRemaining()) {
	    score.transferSomeBonus();
	}

	killPoints.clear();
	cycles++;
	endProfiler("Game.update");

	if (PROFILING && cycles % 5000 == 0) {
	    Game.log(Profiler.results());
	}

    }

    public void draw(AbstractGraphics graphics) {
	startProfiler("Game.draw");
	graphics.fillScreen();
	borders.drawBorders(graphics);
	buttons.draw(graphics);
	if (state.current() != cfg.BETWEEN_LIVES_STATE)
	    ship.draw(graphics);
	else {
	    shipExplosion.draw(graphics, ship.getX(), ship.getY());
	}
	killPoints.draw(graphics);
	playerBullets.draw(graphics);
	alienBullets.draw(graphics);
	aliens.draw(graphics);
	explosions.draw(graphics);
	score.draw(graphics);
	healthBar.draw(graphics);
	// text messages drawn last based on state
	if (state.current() == cfg.READY_STATE) {
	    // TODO better placement of imgs using relative values
	    // spriteCache.get("text_get_ready.png").draw(graphics, 70, 200);
	    spriteCache.get(cfg.GET_READY).draw(graphics, cfg.GET_READY_X,
		    cfg.GET_READY_Y);
	    // TODO magic numbers
	    countDown.draw(graphics, 160, 270);
	}
	if (state.current() == cfg.LEVEL_CLEARED_STATE
		|| state.current() == cfg.BONUS_MESSAGE_STATE) {
	    textFx.draw(graphics, 28, 100);
	}
	if (state.current() == cfg.BONUS_MESSAGE_STATE
		|| state.current() == cfg.BONUS_PAYOUT_STATE) {
	    // TODO keep a copy of the Sprite (in Game, final)
	    spriteCache.get(cfg.BONUS_DETAILS).draw(graphics,
		    cfg.BONUS_DETAILS_X, cfg.BONUS_DETAILS_Y);
	    score.drawBonuses(graphics);
	}
	endProfiler("Game.draw");
    }

    public void feedInput(InputMessage input) {
	synchronized (inputQueueMutex) {
	    if (inputQueue.size() == InputMessage.INPUT_QUEUE_SIZE) {
		log("Game.feedInput(): Ignoring message, queue is full!");
		return;
	    }
	    try {
		inputQueue.put(input);
	    } catch (InterruptedException e) {
		log(e.getMessage() + e);
	    }
	}
    }

    public boolean withinLeftButton(float x, float y) {
	return buttons.withinLeftButton(x, y);
    }

    public boolean withinRightButton(float x, float y) {
	return buttons.withinRightButton(x, y);
    }

    public boolean withinFireButton(float x, float y) {
	return buttons.withinFireButton(x, y);
    }

    private void updateUnpausablePhysics(int timeDelta) {
	if (state.current() != cfg.BETWEEN_LIVES_STATE)
	    ship.move(timeDelta);
	playerBullets.move(timeDelta);
	alienBullets.move(timeDelta);
    }

    private void updatePausablePhysics(int timeDelta) {
	if (ship.triggerDown()) {
	    ship.shoot(playerBullets, score);
	}
	aliens.shoot(alienBullets);
	aliens.update(timeDelta);
    }

    public static void startProfiler(String name) {
	if (PROFILING)
	    Profiler.start(name);
    }

    public static void endProfiler(String name) {
	if (PROFILING)
	    Profiler.end(name);
    }

    private void processInput() {
	synchronized (inputQueueMutex) {
	    ArrayBlockingQueue<InputMessage> inputQueue = this.inputQueue;
	    while (!inputQueue.isEmpty()) {
		try {
		    InputMessage input = inputQueue.take();
		    if (input.eventType == InputMessage.LEFT_ON) {
			ship.goingLeft();
		    } else if (input.eventType == InputMessage.RIGHT_ON) {
			ship.goingRight();
		    } else if (input.eventType == InputMessage.SHOOT_ON) {
			ship.fireModeOn();
		    } else if (input.eventType == InputMessage.LEFT_OFF) {
			ship.standingStill();
		    } else if (input.eventType == InputMessage.RIGHT_OFF) {
			ship.standingStill();
		    } else if (input.eventType == InputMessage.SHOOT_OFF) {
			ship.fireModeOff();
		    }
		    input.returnToPool();
		} catch (InterruptedException e) {
		    log(e.getMessage() + e);
		}
	    }
	}
    }

    private int getTimeDelta() {
	int tDelta;
	if (lastTime == 0) {
	    tDelta = 0;
	} else {
	    tDelta = (int) (System.currentTimeMillis() - lastTime);
	    // prevent big jumps in case of massive frame rate degradation
	    tDelta = tDelta <= 100 ? tDelta : 100;
	}
	lastTime = System.currentTimeMillis();
	return tDelta;
    }

    private void preloadImages() {
	AnimationFrames explosion = new AnimationFrames(spriteCache);
	explosion.reset(cfg.EXPL_IMGS, cfg.EXPL_TIMES, true);
	shipExplosion.reset(cfg.EXPL_IMGS, cfg.EXPL_TIMES, true);
	spriteCache.get(cfg.NUM_0);
	spriteCache.get(cfg.NUM_1);
	spriteCache.get(cfg.NUM_2);
	spriteCache.get(cfg.NUM_3);
	spriteCache.get(cfg.NUM_4);
	spriteCache.get(cfg.NUM_5);
	spriteCache.get(cfg.NUM_6);
	spriteCache.get(cfg.NUM_7);
	spriteCache.get(cfg.NUM_8);
	spriteCache.get(cfg.NUM_9);
	spriteCache.get(cfg.GET_READY);
	textFx.reset(cfg.LEVEL_COMPLETE_IMGS, cfg.LEVEL_COMPLETE_TIMES, true);
	countDown.reset(cfg.COUNTDOWN_IMGS, cfg.COUNTDOWN_TIMES, true);
	spriteCache.get(cfg.BONUS_DETAILS);
	killPoints.preload();
    }
}
