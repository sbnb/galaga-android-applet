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

import com.gamefreezer.galaga.Constants;
import com.gamefreezer.galaga.Game;
import com.gamefreezer.utilities.Profiler;

@SuppressWarnings("serial")
public class SpaceWar extends Applet implements Runnable, KeyListener {

    public final static String DATA_DIR = "data";

    private Constants cfg;

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
	cfg = new Constants(fileOpener, log, colorDecoder);

	appletGraphics = new AppletGraphics(cfg);
	game = new Game(cfg, log, bitmapReader, colorDecoder, fileOpener,
		fileLister);
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
    public void keyPressed(KeyEvent e) {
	Profiler.start("SW.keyPressed");
	game.keyPressed(e.getKeyCode());
	Profiler.end("SW.keyPressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
	Profiler.start("SW.keyReleased");
	game.keyReleased(e.getKeyCode());
	Profiler.start("SW.keyReleased");
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
	// game.draw(offScreen);
	// copy the buffered image to screen
	g.drawImage(offScreenImage, 0, 0, this);
    }

    @Override
    public String getAppletInfo() {
	return ("SpaceWar - copyright 2000, Sean Noble [sean_noble@hotmail.com].");
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
