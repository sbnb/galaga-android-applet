package com.gamefreezer.android.galaga;

import java.util.concurrent.ArrayBlockingQueue;

import com.gamefreezer.galaga.InputMessage;
import com.gamefreezer.galaga.Tools;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {

    private MySurfaceView mySurfaceView;
    private static final int INPUT_QUEUE_SIZE = 30;
    private ArrayBlockingQueue<InputMessage> inputMessagePool;
    public static DisplayMetrics metrics;

    public MainActivity() {
	super();
	Log.i("GALAGA", "MainActivity(): constructor.");
	createInputObjectPool();
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	try {
	    super.onCreate(savedInstanceState);
	    // Tools.log("MainActivity(): constructor.");
	    int flags = WindowManager.LayoutParams.FLAG_FULLSCREEN
		    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
	    getWindow().addFlags(flags);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

	    metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    Log.i("GALAGA", "metrics.width: " + metrics.widthPixels
		    + " metrics.height: " + metrics.heightPixels);

	    mySurfaceView = new MySurfaceView(this);
	    setContentView(mySurfaceView);
	    createInputObjectPool();

	} catch (Exception e) {
	    Log.e("GALAGA", "We have an exception: " + e);
	    String msg = e.getClass().getName() + " " + e.getMessage();
	    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG)
		    .show();
	}
    }

    private void createInputObjectPool() {
	inputMessagePool = new ArrayBlockingQueue<InputMessage>(
		INPUT_QUEUE_SIZE);
	for (int i = 0; i < INPUT_QUEUE_SIZE; i++) {
	    inputMessagePool.add(new InputMessage(inputMessagePool));
	}
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	// determine if is a touch down event
	if (event.getAction() == MotionEvent.ACTION_DOWN
		|| event.getAction() == MotionEvent.ACTION_UP) {
	    float x = event.getX();
	    float y = event.getY();
	    int eventType = -1;
	    if (mySurfaceView.withinAreaOfInterest(x, y)) {
		if (mySurfaceView.withinLeftButton(x, y)) {
		    eventType = event.getAction() == MotionEvent.ACTION_DOWN ? InputMessage.LEFT_ON
			    : InputMessage.LEFT_OFF;
		} else if (mySurfaceView.withinRightButton(x, y)) {
		    eventType = event.getAction() == MotionEvent.ACTION_DOWN ? InputMessage.RIGHT_ON
			    : InputMessage.RIGHT_OFF;
		} else if (mySurfaceView.withinFireButton(x, y)) {
		    eventType = event.getAction() == MotionEvent.ACTION_DOWN ? InputMessage.SHOOT_ON
			    : InputMessage.SHOOT_OFF;
		}

		// get a message from the pool
		if (!inputMessagePool.isEmpty()) {
		    try {
			InputMessage message = inputMessagePool.take();
			message.eventType = eventType;
			mySurfaceView.feedInput(message);
		    } catch (InterruptedException e) {
			Tools.log(e.toString());
		    }
		} else {
		    Tools.log("MainActivity.onTouchEvent(): "
			    + "message pool exhausted!");
		}
	    }
	}
	// don't allow more than 60 motion events per second
	try {
	    Thread.sleep(16);
	} catch (InterruptedException e) {
	    // NOP
	}
	return true;
    }
}