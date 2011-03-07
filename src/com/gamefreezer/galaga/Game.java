package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.*;

import java.io.InputStream;
import java.util.List;

import com.gamefreezer.utilities.Profiler;

public class Game extends AllocGuard {

    private static AbstractLog log = null;
    private static AbstractBitmapReader bitmapReader = null;
    private static AbstractColor colorDecoder = null;
    private static AbstractFileOpener fileOpener = null;
    private static AbstractFileLister fileLister = null;

    public Game() {
	super();
	Game.log("Game(): constructor.");
	formations = FormationsFactory.createFormations();
	aliens = new Aliens();
	changeFormation();
	ship = new Ship(SHIP_START_LOCATION);
	playerBullets = new Bullets(BULLETS_ON_SCREEN, BULLET_IMAGE);
	alienBullets = new Bullets(ALIEN_BULLETS_ON_SCREEN, ALIEN_BULLET_IMAGE);
	cycles = 0;
	setStateTimer(LEVEL_DELAY);
	preloadImages();
	AllocGuard.guardOn = true;
	Game.log("SpriteStore.size(): " + SpriteStore.instance().size());
    }

    public static void setAbstractInterfaceVars(AbstractLog log,
	    AbstractBitmapReader bitmapReader, AbstractColor colorDecoder,
	    AbstractFileOpener fileOpener, AbstractFileLister fileLister) {
	Game.log = log;
	Game.bitmapReader = bitmapReader;
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

    public static AbstractBitmap readBitmap(String name) {
	assert bitmapReader != null : "Game.bitmapReader is null!";
	return bitmapReader.read(name);
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
	int timeDelta = getTimeDelta();

	updateState();

	Profiler.start("Game.update");

	if (state == PLAYING_STATE) {
	    updateAssetsThatCanBeFrozen(timeDelta);
	    updateAssetsThatCantBeFrozen(timeDelta);
	    collisionDetector.checkCollisions(aliens, ship, score,
		    playerBullets, alienBullets, killPoints);
	    sandbox.update(timeDelta);
	} else {
	    updateAssetsThatCantBeFrozen(timeDelta);
	}

	if (state == BONUS_PAYOUT_STATE && score.bonusRemaining()) {
	    score.transferSomeBonus();
	}

	killPoints.clear();

	Profiler.end("Game.update");
	cycles++;
	if (cycles % 1000 == 0) {
	    System.out.println(Profiler.results());
	}
    }

    private void updateState() {

	// BETWEEN_LIVES_STATE
	if (state == PLAYING_STATE && score.getHealth() == 0) {
	    state = BETWEEN_LIVES_STATE;
	    playerBullets.killOnscreenBullets();
	    alienBullets.killOnscreenBullets();
	    shipExplosion.reset();
	    setStateTimer(BETWEEN_LIVES_STATE_TIMER);
	    System.out.println("PLAYING_STATE ==> BETWEEN_LIVES_STATE");
	    // TODO aliens should move in BETWEEN_LIVES_STATE, but no shoot or
	    // collisions
	}

	// READY_STATE
	if (state == BETWEEN_LIVES_STATE && timeUpInState()) {
	    state = READY_STATE;
	    countDown.reset();
	    score.restoreHealth();
	    aliens.resetLivingAliens();
	    System.out.println("BETWEEN_LIVES_STATE ==> READY_STATE");
	}

	// WAIT_CLEAR_STATE
	if (state == PLAYING_STATE && aliens.levelCleared()) {
	    state = WAIT_CLEAR_STATE;
	    setStateTimer(500);
	    // or wait for bullets
	    System.out.println("PLAYING_STATE ==> WAIT_CLEAR_STATE");
	}

	// LEVEL_CLEARED_STATE
	if (state == WAIT_CLEAR_STATE && timeUpInState()) {
	    state = LEVEL_CLEARED_STATE;
	    textFx.reset();
	    setStateTimer(1000);
	    System.out.println("PLAYING_STATE ==> LEVEL_CLEARED_STATE");
	}

	// BONUS_MESSAGE_STATE
	if (state == LEVEL_CLEARED_STATE && timeUpInState()) {
	    state = BONUS_MESSAGE_STATE;
	    // score.addLevelBonus();
	    // score.clearLevelScore();
	    score.calculateBonus();
	    setStateTimer(3000);
	    System.out.println("LEVEL_CLEARED_STATE ==> BONUS_MESSAGE_STATE");
	}

	// BONUS_PAYOUT_STATE
	if (state == BONUS_MESSAGE_STATE && timeUpInState()) {
	    state = BONUS_PAYOUT_STATE;
	    setStateTimer(3000);
	    System.out.println("BONUS_MESSAGE_STATE ==> BONUS_PAYOUT_STATE");
	}

	// READY_STATE
	if (state == BONUS_PAYOUT_STATE && !score.bonusRemaining()
		&& timeUpInState()) {
	    state = READY_STATE;
	    score.clearLevelScore();
	    countDown.reset();
	    changeFormation();
	    System.out.println("BONUS_PAYOUT_STATE ==> READY_STATE");
	}

	// PLAYING_STATE
	if (state == READY_STATE && countDown.finished()) {
	    state = PLAYING_STATE;
	    System.out.println("READY_STATE ==> PLAYING_STATE");
	}

    }

    private boolean timeUpInState() {
	return System.currentTimeMillis() > stateTimer;
    }

    private void setStateTimer(int stateInterval) {
	stateTimer = System.currentTimeMillis() + stateInterval;
    }

    public void draw(AbstractGraphics graphics) {

	Profiler.start("Game.draw");

	Profiler.start("Game.drawBackground");
	drawBackground(graphics);
	Profiler.end("Game.drawBackground");

	Profiler.start("Ship.draw");
	if (state != BETWEEN_LIVES_STATE)
	    ship.draw(graphics);
	else {
	    // TODO sort out the translation of animations!
	    shipExplosion.draw(graphics, Screen.translateX(ship.getX()), Screen
		    .translateY(ship.getY() + ship.getHeight()));
	}
	Profiler.end("Ship.draw");

	Profiler.start("Killpoints.draw");
	killPoints.draw(graphics);
	Profiler.end("Killpoints.draw");

	Profiler.start("Bullets.draw");
	playerBullets.draw(graphics);
	alienBullets.draw(graphics);
	Profiler.end("Bullets.draw");

	Profiler.start("Aliens.draw");
	aliens.draw(graphics);
	Profiler.end("Aliens.draw");

	Profiler.start("Score_Health.draw");
	drawScoreAndHealth(graphics);
	drawBottomCover(graphics);
	Profiler.end("Score_Health.draw");

	// text messages drawn last based on state
	if (state == READY_STATE) {
	    // TODO magic string
	    SpriteStore.instance().get("text_get_ready.png").draw(graphics, 70,
		    200);
	    countDown.draw(graphics, 160, 270);
	}

	if (state == LEVEL_CLEARED_STATE || state == BONUS_MESSAGE_STATE) {
	    textFx.draw(graphics, 28, 100);
	}

	if (state == BONUS_MESSAGE_STATE || state == BONUS_PAYOUT_STATE) {
	    SpriteStore.instance().get("text_bonus_details.png").draw(graphics,
		    50, 160);
	    score.drawBonuses(graphics);
	}
	// sandbox.draw(graphics);

	Profiler.end("Game.draw");
    }

    public void keyPressed(int key) {
	switch (key) {
	case LEFT_ARROW:
	    ship.goingLeft();
	    break;
	case RIGHT_ARROW:
	    ship.goingRight();
	    break;
	case SPACE_BAR:
	    ship.fireModeOn();
	    break;
	}
    }

    public void keyReleased(int key) {
	switch (key) {
	case LEFT_ARROW:
	    ship.standingStill();
	    break;
	case RIGHT_ARROW:
	    ship.standingStill();
	    break;
	case SPACE_BAR:
	    ship.fireModeOff();
	    break;
	}
    }

    private void updateAssetsThatCantBeFrozen(int timeDelta) {
	if (state != BETWEEN_LIVES_STATE)
	    ship.move(timeDelta);
	playerBullets.move(timeDelta);
	alienBullets.move(timeDelta);
    }

    private void updateAssetsThatCanBeFrozen(int timeDelta) {
	if (ship.triggerDown()) {
	    ship.shoot(playerBullets, score);
	}
	aliens.shoot(alienBullets);
	aliens.update(timeDelta);
    }

    private void changeFormation() {
	aliens.newLevel(formations.get(formationsIndex++));
	if (formationsIndex == formations.size()) {
	    formationsIndex = 0;
	}
    }

    private int getTimeDelta() {
	int tDelta;
	if (lastTime == 0) {
	    tDelta = 0;
	} else {
	    // TODO throttle this to .1 second - see Chris Pruit reasoning
	    tDelta = (int) (System.currentTimeMillis() - lastTime);
	}
	lastTime = System.currentTimeMillis();
	return tDelta;
    }

    private void preloadImages() {
	AnimationFrames explosion = new AnimationFrames();
	explosion.reset(EXPL_IMGS, EXPL_TIMES, "", true);
	shipExplosion.reset(EXPL_IMGS, EXPL_TIMES, "", true);
	SpriteStore.instance().get(NUM_0);
	SpriteStore.instance().get(NUM_1);
	SpriteStore.instance().get(NUM_2);
	SpriteStore.instance().get(NUM_3);
	SpriteStore.instance().get(NUM_4);
	SpriteStore.instance().get(NUM_5);
	SpriteStore.instance().get(NUM_6);
	SpriteStore.instance().get(NUM_7);
	SpriteStore.instance().get(NUM_8);
	SpriteStore.instance().get(NUM_9);
	// TODO magic strings here are also hard-coded elsewhere, fix
	SpriteStore.instance().get("text_get_ready.png");
	SpriteStore.instance().get("text_level_complete.png");
	textFx.reset(LEVEL_COMPLETE_IMGS, LEVEL_COMPLETE_TIMES, "", true);
	countDown.reset(COUNTDOWN_IMGS, COUNTDOWN_TIMES, "", true);
	SpriteStore.instance().get("text_bonus_details.png");
	killPoints.preload();
    }

    private void drawBackground(AbstractGraphics graphics) {
	graphics.setColor(BACKGROUND);
	// graphics.fillRect(Screen.leftIndent(), Screen.topIndent(), Screen
	// .width() + 1, Screen.height() + 1);
	// TODO magic string reference
	// SpriteStore.instance().get("bg_starfield.png").draw(graphics,
	// Screen.leftIndent(), Screen.topIndent());
	graphics.fillScreen();

	graphics.setColor(BORDER);
	graphics.drawRect(Screen.leftIndent(), Screen.topIndent(), Screen
		.width() - 2, Screen.height() - 2);

    }

    private void drawScoreAndHealth(AbstractGraphics graphics) {
	score.draw(graphics);
	drawHealthBar(graphics);
    }

    private int healthBarX = Screen.leftIndent() + HEALTH_SIDE_INDENT;
    private int healthBarY = Screen.topIndent() + Screen.height()
	    - HEALTH_BOTTOM_INDENT;
    private int healthBarWidth = Screen.width() - (HEALTH_SIDE_INDENT * 2);

    private void drawHealthBar(AbstractGraphics graphics) {
	graphics.setColor(HEALTH_BAR_OUTLINE);
	graphics.drawRect(healthBarX, healthBarY, healthBarWidth,
		HEALTH_BAR_HEIGHT);

	int newHealthBarLength = healthBarWidth + 1;
	float percentageHealth = score.getHealth() / 100f;
	newHealthBarLength = (int) (newHealthBarLength * percentageHealth);

	graphics.setColor(HEALTH_BAR_HIGH);
	if (percentageHealth < HEALTH_COLOR_CHANGE) {
	    graphics.setColor(HEALTH_BAR_LOW);
	}
	graphics.fillRect(healthBarX, healthBarY, newHealthBarLength,
		HEALTH_BAR_HEIGHT + 1);
    }

    private void drawBottomCover(AbstractGraphics graphics) {
	graphics.setColor(BOTTOM_COVER);
	// TODO magic number
	// graphics.fillRect(Screen.leftIndent(), Screen.height() + 12, Screen
	// .width() + 1, BOTTOM_MASK_HEIGHT);
	graphics.fillRect(Screen.leftIndent(), Screen.height() + 1, Screen
		.width() + 1, BOTTOM_MASK_HEIGHT);
    }

    // objects used in the game
    private List<Formation> formations;
    private Ship ship = null;
    private Bullets playerBullets = null;
    private Bullets alienBullets = null;
    private Aliens aliens;
    private CollisionDetector collisionDetector = new CollisionDetector();
    private Sandbox sandbox = new Sandbox();
    private Score score = new Score();
    private long cycles = 0; // elapsed cycles during game
    private int formationsIndex = 0;
    private long lastTime = 0;
    AnimationFrames textFx = new AnimationFrames();
    AnimationFrames countDown = new AnimationFrames();
    AnimationFrames shipExplosion = new AnimationFrames();
    KillPoints killPoints = new KillPoints();

    private static final int LEFT_ARROW = 37;
    private static final int RIGHT_ARROW = 39;
    private static final int SPACE_BAR = 32;
    private int state = READY_STATE;

    private long stateTimer;
}
