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
import com.gamefreezer.galaga.Tools;

public class GameWrapper implements Runnable {

    public static final String DATA_DIR = "data";
    private Context appContext;
    private SurfaceHolder surfaceHolder;
    private boolean running;

    private AndroidGraphics androidGraphics;
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
	AndroidLog log = new AndroidLog();
	AndroidColor colorDecoder = new AndroidColor(Color.GREEN);
	AndroidFileOpener fileOpener = new AndroidFileOpener(appContext);
	AndroidBitmapReader bitmapReader = new AndroidBitmapReader(appContext);
	AndroidFileLister fileLister = new AndroidFileLister(appContext);
	Tools.setAbstractInterfaceVars(log, bitmapReader, colorDecoder,
		fileOpener, fileLister);
	Constants cfg = new Constants(fileOpener, log, colorDecoder);

	Tools.log("Tools.log(msg): now available");
	Tools.log("GameWrapper.initGame(): about to init Game()");
	game = new Game(cfg);
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

    public Game getGame() {
	return game;
    }

}
