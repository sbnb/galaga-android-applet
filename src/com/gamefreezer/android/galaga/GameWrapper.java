package com.gamefreezer.android.galaga;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gamefreezer.galaga.Game;
import com.gamefreezer.galaga.Screen;

public class GameWrapper implements Runnable {

    private Context appContext;
    private SurfaceHolder surfaceHolder;
    private boolean running;
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
	log = new AndroidLog();
	bitmapReader = new AndroidBitmapReader(appContext);
	colorDecoder = new AndroidColor(Color.GREEN);
	fileOpener = new AndroidFileOpener(appContext);
	fileLister = new AndroidFileLister(appContext);
	Game.setAbstractInterfaceVars(log, bitmapReader, colorDecoder,
		fileOpener, fileLister);
	Game.log("Game.log(msg): now available");

	Game.log("GameWrapper.initGame(): setting Screen.x dimensions");
	Screen.x = 0;
	Screen.y = 0;
	Screen.w = MainActivity.metrics.widthPixels;
	Screen.y = MainActivity.metrics.heightPixels;

	Game.log("GameWrapper.initGame(): about to init Game()");
	game = new Game();
    }

    private Paint paint = new Paint();

    private void myDraw(Canvas canvas) {
	// TODO access metrics here (temporarily for now)
	paint.setColor(Color.RED);
	canvas.drawCircle(0, 0, 5, paint);
	canvas.drawCircle(MainActivity.metrics.widthPixels, 0, 5, paint);
	canvas.drawCircle(MainActivity.metrics.widthPixels,
		MainActivity.metrics.heightPixels, 5, paint);
	canvas.drawCircle(0, MainActivity.metrics.heightPixels, 5, paint);
    }

}
