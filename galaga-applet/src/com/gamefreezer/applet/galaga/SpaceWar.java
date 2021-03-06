package com.gamefreezer.applet.galaga;

/*
 SpaceWar.java : a Space Invaders applet.
 Author  : Sean Noble
 Created : 13 March, 2000
 Updated : 22 September, 2010
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ArrayBlockingQueue;

import com.gamefreezer.galaga.Config;
import com.gamefreezer.galaga.Game;
import com.gamefreezer.galaga.InputMessage;
import com.gamefreezer.galaga.Tools;
import com.gamefreezer.utilities.Profiler;

@SuppressWarnings("serial")
public class SpaceWar extends Applet implements Runnable, KeyListener {

    public final static String DATA_DIR = "data";
    private Config cfg;

    // Thread control variables.
    private volatile Thread loadThread;
    private volatile Thread loopThread;
    private long wakeUpTime;

    // onScreen info
    private Dimension onScreenDim = null;

    // offScreen info (double buffer)
    private Dimension offScreenDim = null;
    private Image offScreenImage = null;
    private Graphics offScreen = null;

    private Game game;
    private AppletGraphics appletGraphics;

    // timing & thread variables
    private long frameExeTimes[] = new long[1000];
    private long frameCount = 0;

    private ArrayBlockingQueue<InputMessage> inputMessagePool;
    private static boolean KEY_PRESS = true;
    private static boolean KEY_RELEASE = false;
    private static final int LEFT_ARROW = 37;
    private static final int RIGHT_ARROW = 39;
    private static final int SPACE_BAR = 32;
    private static final int LETTER_C = 67;
    private static final int LETTER_V = 86;

    @Override
    public void init() {
	addKeyListener(this);

	offScreen = null;
	onScreenDim = getSize();
	wakeUpTime = System.currentTimeMillis();

	AppletFileOpener fileOpener = new AppletFileOpener();
	AppletLog log = new AppletLog();
	AppletBitmapReader bitmapReader = new AppletBitmapReader();
	AppletColor colorDecoder = new AppletColor(Color.BLACK);
	AppletFileLister fileLister = new AppletFileLister();
	Tools.setAbstractInterfaceVars(log, bitmapReader, colorDecoder,
		fileOpener, fileLister);
	cfg = new Config(fileOpener, colorDecoder);
	appletGraphics = new AppletGraphics(cfg);
	game = new Game(cfg);
	createInputMessagePool();
    }

    @Override
    public void start() {
	if (loopThread == null) {
	    loopThread = new Thread(this);
	    loopThread.start();
	}
	if (loadThread == null) {
	    loadThread = new Thread(this);
	    loadThread.start();
	}
	// TODO implement a UI thread, sleep 16 millis limit inputs per second
    }

    @Override
    public void stop() {
	loopThread = null;
	loadThread = null;
    }

    public void run() {
	Thread thisThread = Thread.currentThread();

	while (loopThread == thisThread) {

	    long start = System.currentTimeMillis();
	    game.update();
	    repaint();
	    recordFrameTimes(start);

	    try {
		wakeUpTime += cfg.DELAY;
		Thread.sleep(Math.max(0, sleepInterval()));
	    } catch (InterruptedException e) {
		System.out.println("SpaceWar.run():  " + e);
		break;
	    }
	}
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
	Profiler.start("SW.keyPressed");
	convertToMyInput(KEY_PRESS, keyEvent);
	Profiler.end("SW.keyPressed");
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
	Profiler.start("SW.keyReleased");
	convertToMyInput(KEY_RELEASE, keyEvent);
	Profiler.start("SW.keyReleased");
    }

    private void convertToMyInput(boolean keyPressed, KeyEvent keyEvent) {
	if (inputMessagePool.size() == 0) {
	    Tools.log("SpaceWar.convertToMyInput(): message "
		    + "pool is exhausted, can't send messages.");
	    return;
	}
	try {
	    InputMessage message;
	    switch (keyEvent.getKeyCode()) {
	    case LEFT_ARROW:
		message = inputMessagePool.take();
		message.eventType = keyPressed ? InputMessage.LEFT_ON
			: InputMessage.LEFT_OFF;
		game.feedInput(message);
		break;
	    case RIGHT_ARROW:
		message = inputMessagePool.take();
		message.eventType = keyPressed ? InputMessage.RIGHT_ON
			: InputMessage.RIGHT_OFF;
		game.feedInput(message);
		break;
	    case SPACE_BAR:
		message = inputMessagePool.take();
		message.eventType = keyPressed ? InputMessage.SHOOT_ON
			: InputMessage.SHOOT_OFF;
		game.feedInput(message);
		break;
	    case LETTER_C:
		if (keyPressed) {
		    message = inputMessagePool.take();
		    message.eventType = InputMessage.WEAPONS_DOWN;
		    game.feedInput(message);
		}
		break;
	    case LETTER_V:
		if (keyPressed) {
		    message = inputMessagePool.take();
		    message.eventType = InputMessage.WEAPONS_UP;
		    game.feedInput(message);
		}
		break;
	    default:
		// Tools.log("keyCode: " + keyEvent.getKeyCode());
		break;
	    }

	} catch (InterruptedException e) {
	    Tools.log(e.toString());
	}
    }

    @Override
    public void keyTyped(KeyEvent e) {
	// NOP
    }

    @Override
    public void paint(Graphics g) {
	update(g);
    }

    @Override
    public void update(Graphics g) {
	onScreenDim = this.getSize();
	createOffScreenGraphicsContext();
	appletGraphics.set(offScreen);
	game.draw(appletGraphics);
	// draw applet specific bottom cover
	appletGraphics.setColor(cfg.BOTTOM_COLOR);
	appletGraphics.fillRect(0, cfg.SCREEN.height(), cfg.SCREEN.width(), 20);
	// copy the buffered image to screen
	g.drawImage(offScreenImage, 0, 0, this);
    }

    @Override
    public String getAppletInfo() {
	return ("SpaceWar - copyright 2000, Sean Noble [sean_noble@hotmail.com].");
    }

    private void createInputMessagePool() {
	inputMessagePool = new ArrayBlockingQueue<InputMessage>(
		InputMessage.INPUT_QUEUE_SIZE);
	for (int i = 0; i < InputMessage.INPUT_QUEUE_SIZE; i++) {
	    inputMessagePool.add(new InputMessage(inputMessagePool));
	}
    }

    private long sleepInterval() {
	return wakeUpTime - System.currentTimeMillis();
    }

    private void createOffScreenGraphicsContext() {
	// Create the offScreen graphics context, if needed.
	if (offScreen == null || !dimensionsMatch(onScreenDim, offScreenDim)) {
	    offScreenDim = onScreenDim;
	    offScreenImage = createImage(offScreenDim.width,
		    offScreenDim.height);
	    offScreen = offScreenImage.getGraphics();
	}
    }

    private boolean dimensionsMatch(Dimension dimA, Dimension dimB) {
	return (dimA.width == dimB.width && dimA.height == dimB.height);
    }

    private void recordFrameTimes(long start) {
	if (frameCount < frameExeTimes.length) {
	    frameExeTimes[(int) frameCount] = System.currentTimeMillis()
		    - start;
	}

	if (frameCount == frameExeTimes.length) {
	    // System.out.println(java.util.Arrays.toString(frameExeTimes));
	}
	frameCount++;
    }
}
