package com.gamefreezer.android.galaga;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gamefreezer.galaga.Game;

public class MySurfaceView extends SurfaceView implements
	SurfaceHolder.Callback {
    private GameWrapper gameWrapper;
    private Thread gameThread;

    public MySurfaceView(Context context) {
	super(context);
	Log.i("GALAGA", "MySurfaceView(): constructor.");
	getHolder().addCallback(this);
	gameWrapper = new GameWrapper(this, context.getApplicationContext());
	gameThread = new Thread(gameWrapper);
    }

    @Override
    public void onDraw(Canvas canvas) {
	// NOP
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
	    int height) {
	// NOP
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
	if (!gameThread.isAlive()) {
	    gameThread = new Thread(gameWrapper);
	    gameWrapper.setRunning(true);
	    gameThread.start();
	}
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
	if (gameThread.isAlive()) {
	    gameWrapper.setRunning(false);
	}
    }

    public Game getGame() {
	return gameWrapper.getGame();
    }
}