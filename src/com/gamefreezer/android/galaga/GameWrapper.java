package com.gamefreezer.android.galaga;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gamefreezer.galaga.Constants;
import com.gamefreezer.galaga.Game;
import com.gamefreezer.galaga.InputMessage;
import com.gamefreezer.galaga.Screen;

public class GameWrapper implements Runnable {

    public static final String DATA_DIR = "data";
    private Context appContext;
    private SurfaceHolder surfaceHolder;
    private boolean running;

    private Constants cfg;
    private AndroidGraphics androidGraphics;
    private AndroidLog log;
    private AndroidBitmapReader bitmapReader;
    private AndroidColor colorDecoder;
    private AndroidFileOpener fileOpener;
    private AndroidFileLister fileLister;
    private Game game;

    public GameWrapper(SurfaceView surfaceView, Context appContext) {
	Log.i("GALAGA", "MyGameWrapper(): constructor.");
	this.surfaceHolder = surfaceView.getHolder();
	this.appContext = appContext;
	fileOpener = new AndroidFileOpener(appContext);
	log = new AndroidLog();
	colorDecoder = new AndroidColor(Color.GREEN);
	this.cfg = new Constants(fileOpener, log, colorDecoder);
	initGame();
    }

    public void setRunning(boolean running) {
	this.running = running;
    }

    @Override
    public void run() {
	Canvas canvas = null;
	while (running) {
	    game.update();
	    canvas = surfaceHolder.lockCanvas();
	    if (canvas != null) {
		// canvas is free to use here: draw game on it
		androidGraphics.set(canvas);
		game.draw(androidGraphics);
		myDraw(canvas);
		surfaceHolder.unlockCanvasAndPost(canvas);
	    }
	    // sleep here
	    try {
		Thread.sleep(10);
	    } catch (InterruptedException e) {
		// NOP
	    }
	}
    }

    private void initGame() {
	androidGraphics = new AndroidGraphics();
	bitmapReader = new AndroidBitmapReader(appContext);
	colorDecoder = new AndroidColor(Color.GREEN);
	fileLister = new AndroidFileLister(appContext);
	log.i("GALAGA", "Game.log(msg): now available");

	log.i("GALAGA", "GameWrapper.initGame(): setting Screen.x dimensions");
	Screen.x = 0;
	Screen.y = 0;
	Screen.w = MainActivity.metrics.widthPixels;
	Screen.y = MainActivity.metrics.heightPixels;

	log.i("GALAGA", "GameWrapper.initGame(): about to init Game()");
	game = new Game(cfg, log, bitmapReader, colorDecoder, fileOpener,
		fileLister);
    }

    private Paint paint = new Paint();

    private void myDraw(Canvas canvas) {
	paint.setColor(Color.RED);
	canvas.drawCircle(0, 0, 5, paint);
	canvas.drawCircle(MainActivity.metrics.widthPixels, 0, 5, paint);
	canvas.drawCircle(MainActivity.metrics.widthPixels,
		MainActivity.metrics.heightPixels, 5, paint);
	canvas.drawCircle(0, MainActivity.metrics.heightPixels, 5, paint);
    }

    public boolean withinLeftButton(float x, float y) {
	return game.withinLeftButton(x, y);
    }

    public boolean withinRightButton(float x, float y) {
	return game.withinRightButton(x, y);
    }

    public boolean withinFireButton(float x, float y) {
	return game.withinFireButton(x, y);
    }

    public void feedInput(InputMessage message) {
	game.feedInput(message);

    }

}
