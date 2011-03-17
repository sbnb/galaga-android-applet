package com.gamefreezer.android.galaga;

import com.gamefreezer.galaga.InputMessage;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

    public void feedInput(InputMessage message) {
	gameWrapper.feedInput(message);

    }

    public boolean withinAreaOfInterest(float x, float y) {
	return withinLeftButton(x, y) || withinRightButton(x, y)
		|| withinFireButton(x, y);
    }

    public boolean withinLeftButton(float x, float y) {
	return gameWrapper.withinLeftButton(x, y);
    }

    public boolean withinRightButton(float x, float y) {
	return gameWrapper.withinRightButton(x, y);
    }

    public boolean withinFireButton(float x, float y) {
	return gameWrapper.withinFireButton(x, y);
    }
}