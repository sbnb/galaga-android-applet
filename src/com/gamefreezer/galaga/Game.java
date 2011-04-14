package com.gamefreezer.galaga;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.gamefreezer.galaga.FiniteStateMachine.State;
import com.gamefreezer.utilities.Profiler;

public class Game extends AllocGuard {

    private static boolean PROFILING = false;

    // objects used in the game
    private SpriteCache spriteCache;
    private FiniteStateMachine fsm;
    private List<Formation> formations;
    private Ship ship;
    private Bullets playerBullets;
    private Bullets alienBullets;
    private Aliens aliens;
    private CollisionDetector collisionDetector;
    private Sandbox sandbox;
    private Score score;
    private HealthBar healthBar;
    private long cycles = 0; // elapsed cycles during game
    private long lastTime = 0;

    private FixedAnimation countDown;
    private FixedAnimation getReady;
    private FixedAnimation bonusDetails;
    private FixedAnimation levelComplete;

    private Animation shipExplosion;
    private Explosions explosions;
    private KillPoints killPoints;

    private Constants cfg;
    private int timeDelta;

    private ArrayBlockingQueue<InputMessage> inputQueue = new ArrayBlockingQueue<InputMessage>(
	    InputMessage.INPUT_QUEUE_SIZE);
    private Buttons buttons;
    private BorderRenderer borderRenderer;
    private Object inputQueueMutex = new Object();

    public Game(Constants cfg) {
	super();
	assert Tools.initialised : "Tools not initialised!";

	this.cfg = cfg;
	final Screen screen = cfg.SCREEN;
	spriteCache = cfg.SPRITE_CACHE;
	explosions = cfg.EXPLOSIONS;

	// TODO don't pass cfg here and lower, pass what is needed
	collisionDetector = new CollisionDetector(explosions);
	sandbox = new Sandbox(spriteCache, cfg);
	score = new Score(spriteCache, cfg, cfg.HEALTH_HIT_LIGHT,
		cfg.HEALTH_HIT_SEVERE);
	healthBar = new HealthBar(cfg, score);
	countDown = cfg.COUNT_DOWN;
	getReady = cfg.GET_READY;
	bonusDetails = cfg.BONUS_DETAILS;
	levelComplete = cfg.LEVEL_COMPLETE;

	shipExplosion = new Animation(spriteCache);
	killPoints = new KillPoints(spriteCache, cfg);

	formations = FormationsFactory.createFormations(spriteCache, cfg);

	final Speed soloReturnSpeed = new Speed(cfg.SOLO_RETURN_SPEED.x,
		cfg.SOLO_RETURN_SPEED.y);
	final OldGun gun = new OldGun(cfg.ALIEN_FIRE_RATE,
		cfg.ALIEN_BULLET_MOVEMENT);

	final SoloController soloController = new SoloController(screen,
		cfg.STAY_SOLO);
	final SoloAliens soloAliens = new SoloAliens(cfg.SOLO_SPEED_RANGE,
		cfg.SOLO_RELEASE_RANGE, cfg.STATE_TIMES.LEVEL_DELAY,
		soloReturnSpeed, soloController);

	aliens = new Aliens(spriteCache, screen, gun, soloAliens,
		cfg.MAX_FORMATION, cfg.HIT_RENDERER_POOL_SIZE);

	playerBullets = new Bullets(spriteCache, screen,
		cfg.MAX_BULLETS_ON_SCREEN, new AnimationSource(
			cfg.BULLET_IMAGES, cfg.BULLET_TIMES));
	alienBullets = new Bullets(spriteCache, screen,
		cfg.ALIEN_BULLETS_ON_SCREEN, new AnimationSource(
			cfg.ALIEN_BULLET_IMAGES, cfg.ALIEN_BULLET_TIMES));
	fsm = new FiniteStateMachine(cfg.STATE_TIMES, aliens, formations,
		score, playerBullets, alienBullets, shipExplosion, countDown,
		levelComplete);

	final Speed RIGHT_SPEED = new Speed(cfg.SHIP_MOVEMENT, 0);
	final Speed LEFT_SPEED = new Speed(-cfg.SHIP_MOVEMENT, 0);
	final Speed NO_SPEED = new Speed(0, 0);
	ship = new Ship(spriteCache, screen, new AnimationSource(
		cfg.SHIP_IMAGES, cfg.SHIP_TIMES), cfg.GUNS, RIGHT_SPEED,
		LEFT_SPEED, NO_SPEED);

	preloadImages();
	buttons = new Buttons(cfg);
	borderRenderer = new BorderRenderer(screen, cfg.OUTER_BORDER_COLOR,
		cfg.INNER_BORDER_COLOR);

	AllocGuard.guardOn = true;
    }

    public void update() {
	startProfiler("Game.update");

	recordTimeDelta();
	fsm.update();
	processInput();

	if (fsm.currentState() == State.PLAYING) {
	    updateUnpausablePhysics();
	    updatePausablePhysics();
	    collisionDetector.checkCollisions(aliens, ship, score,
		    playerBullets, alienBullets, killPoints);
	    sandbox.update(timeDelta);
	} else {
	    updateUnpausablePhysics();
	}

	if (fsm.currentState() == State.BONUS_PAYOUT && score.bonusRemaining()) {
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
	killPoints.draw(graphics);
	aliens.draw(graphics);
	playerBullets.draw(graphics);
	if (fsm.currentState() != State.BETWEEN_LIVES)
	    ship.draw(graphics);
	else {
	    shipExplosion.draw(graphics, ship.getX(), ship.getY());
	}
	alienBullets.draw(graphics);
	explosions.draw(graphics);
	score.draw(graphics);
	healthBar.draw(graphics);
	sandbox.draw(graphics);
	// text messages drawn last based on state
	if (fsm.currentState() == State.READY) {
	    getReady.draw(graphics);
	    countDown.draw(graphics);
	}
	if (fsm.currentState() == State.LEVEL_CLEARED
		|| fsm.currentState() == State.BONUS_MESSAGE) {
	    levelComplete.draw(graphics);
	}
	if (fsm.currentState() == State.BONUS_MESSAGE
		|| fsm.currentState() == State.BONUS_PAYOUT) {
	    bonusDetails.draw(graphics);
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

    private void updateUnpausablePhysics() {
	if (fsm.currentState() != State.BETWEEN_LIVES)
	    ship.move(timeDelta);
	ship.coolGuns(timeDelta);
	playerBullets.move(timeDelta);
	alienBullets.move(timeDelta);
    }

    private void updatePausablePhysics() {
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
		    } else if (input.eventType == InputMessage.WEAPONS_DOWN) {
			ship.cycleWeaponDown();
		    } else if (input.eventType == InputMessage.WEAPONS_UP) {
			ship.cycleWeaponUp();
		    }
		    input.returnToPool();
		} catch (InterruptedException e) {
		    Tools.log(e.getMessage() + e);
		}
	    }
	}
    }

    private void recordTimeDelta() {
	if (lastTime == 0) {
	    timeDelta = 0;
	} else {
	    timeDelta = (int) (System.currentTimeMillis() - lastTime);
	    // prevent big jumps in case of massive frame rate degradation
	    timeDelta = timeDelta <= 100 ? timeDelta : 100;
	}
	lastTime = System.currentTimeMillis();
    }

    private void preloadImages() {
	for (int idx = 0; idx < cfg.DIGITS.length; idx++) {
	    spriteCache.get(cfg.DIGITS[idx]);
	}
	// TODO check fixed animations don't need preloading, or do it here
	killPoints.preload();
    }
}
