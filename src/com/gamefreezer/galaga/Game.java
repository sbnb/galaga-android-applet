package com.gamefreezer.galaga;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.gamefreezer.utilities.Profiler;
import com.gamefreezer.galaga.State.States;

public class Game extends AllocGuard {

    private static boolean PROFILING = true;

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
    private BorderRenderer borderRenderer;
    private Object inputQueueMutex = new Object();

    public Game(Constants cfg) {
	super();
	assert Tools.initialised : "Tools not initialised!";
	Tools.log("Game(): constructor.");

	this.cfg = cfg;
	screen = cfg.SCREEN;
	spriteCache = new SpriteCache(Tools.bitmapReader);
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
	final Speed targettingSpeed = new Speed(cfg.SOLO_RETURN_SPEED.x,
		cfg.SOLO_RETURN_SPEED.y);
	aliens = new Aliens(spriteCache, cfg, targettingSpeed);
	playerBullets = new Bullets(spriteCache, screen, cfg.BULLETS_ON_SCREEN,
		cfg.BULLET_IMAGES, cfg.BULLET_TIMES);
	alienBullets = new Bullets(spriteCache, screen,
		cfg.ALIEN_BULLETS_ON_SCREEN, cfg.ALIEN_BULLET_IMAGES,
		cfg.ALIEN_BULLET_TIMES);
	state = new State(cfg.STATE_TIMES, aliens, formations, score,
		playerBullets, alienBullets, shipExplosion, countDown, textFx);

	final Speed RIGHT_SPEED = new Speed(cfg.SHIP_MOVEMENT, 0);
	final Speed LEFT_SPEED = new Speed(-cfg.SHIP_MOVEMENT, 0);
	final Speed NO_SPEED = new Speed(0, 0);
	final Gun gun = new Gun(cfg.MIN_TIME_BETWEEN_BULLETS,
		cfg.BULLET_MOVEMENT);
	ship = new Ship(spriteCache, screen, cfg.SHIP_IMAGES, cfg.SHIP_TIMES,
		gun, RIGHT_SPEED, LEFT_SPEED, NO_SPEED);

	preloadImages();
	buttons = new Buttons(cfg);
	borderRenderer = new BorderRenderer(screen, cfg.OUTER_BORDER_COLOR,
		cfg.INNER_BORDER_COLOR);

	AllocGuard.guardOn = true;
	Tools.log("SpriteStore.size(): " + spriteCache.size());
    }

    public void update() {
	startProfiler("Game.update");

	int timeDelta = getTimeDelta();
	state.update();
	processInput();

	if (state.current() == State.PLAYING_STATE) {
	    updatePausablePhysics(timeDelta);
	    updateUnpausablePhysics(timeDelta);
	    collisionDetector.checkCollisions(aliens, ship, score,
		    playerBullets, alienBullets, killPoints);
	    sandbox.update(timeDelta);
	} else {
	    updateUnpausablePhysics(timeDelta);
	}

	if (state.current() == State.BONUS_PAYOUT_STATE
		&& score.bonusRemaining()) {
	    score.transferSomeBonus();
	}

	killPoints.clear();
	cycles++;
	endProfiler("Game.update");

	if (PROFILING && cycles % 5000 == 0) {
	    Tools.log(Profiler.results());
	}

    }

    public void draw(AbstractGraphics graphics) {
	startProfiler("Game.draw");
	graphics.fillScreen();
	if (state.current() != State.BETWEEN_LIVES_STATE)
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
	sandbox.draw(graphics);
	// text messages drawn last based on state
	if (state.current() == State.READY_STATE) {
	    // TODO better placement of imgs using relative values
	    // spriteCache.get("text_get_ready.png").draw(graphics, 70, 200);
	    spriteCache.get(cfg.GET_READY_IMG).draw(graphics,
		    cfg.GET_READY_TOPLEFT);
	    // TODO magic numbers
	    countDown.draw(graphics, 160, 270);
	}
	if (state.current() == State.LEVEL_CLEARED_STATE
		|| state.current() == State.BONUS_MESSAGE_STATE) {
	    textFx.draw(graphics, 28, 100);
	}
	if (state.current() == State.BONUS_MESSAGE_STATE
		|| state.current() == State.BONUS_PAYOUT_STATE) {
	    // TODO keep a copy of the Sprite (in Game, final)
	    spriteCache.get(cfg.BONUS_DETAILS_IMG).draw(graphics,
		    cfg.BONUS_DETAILS_TOPLEFT);
	    score.drawBonuses(graphics);
	}
	borderRenderer.draw(graphics);
	buttons.draw(graphics);
	endProfiler("Game.draw");
    }

    public void feedInput(InputMessage input) {
	synchronized (inputQueueMutex) {
	    if (inputQueue.size() == InputMessage.INPUT_QUEUE_SIZE) {
		Tools.log("Game.feedInput(): Ignoring message, queue is full!");
		return;
	    }
	    try {
		inputQueue.put(input);
	    } catch (InterruptedException e) {
		Tools.log(e.getMessage() + e);
	    }
	}
    }

    public boolean withinAreaOfInterest(float x, float y) {
	return withinLeftButton(x, y) || withinRightButton(x, y)
		|| withinFireButton(x, y);
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
	if (state.current() != State.BETWEEN_LIVES_STATE)
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
		    } else if (input.eventType == InputMessage.LEFT_RIGHT_OFF) {
			ship.standingStill();
		    } else if (input.eventType == InputMessage.SHOOT_OFF) {
			ship.fireModeOff();
		    }
		    input.returnToPool();
		} catch (InterruptedException e) {
		    Tools.log(e.getMessage() + e);
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
	spriteCache.get(cfg.GET_READY_IMG);
	textFx.reset(cfg.LEVEL_COMPLETE_IMGS, cfg.LEVEL_COMPLETE_TIMES, true);
	countDown.reset(cfg.COUNTDOWN_IMGS, cfg.COUNTDOWN_TIMES, true);
	spriteCache.get(cfg.BONUS_DETAILS_IMG);
	killPoints.preload();
    }
}
