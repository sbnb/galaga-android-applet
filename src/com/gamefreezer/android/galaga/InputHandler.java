package com.gamefreezer.android.galaga;

import java.util.concurrent.ArrayBlockingQueue;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;
import android.view.MotionEvent;

import com.gamefreezer.galaga.Game;
import com.gamefreezer.galaga.InputMessage;
import com.gamefreezer.galaga.Tools;

public class InputHandler {
    // TODO input handler should run in its own thread
    private Game game;
    private ArrayBlockingQueue<InputMessage> inputMessagePool;
    private static final int INPUT_QUEUE_SIZE = 30;

    public InputHandler(Game game) {
	this.game = game;
	createInputObjectPool();
    }

    private void createInputObjectPool() {
	inputMessagePool = new ArrayBlockingQueue<InputMessage>(
		INPUT_QUEUE_SIZE);
	for (int i = 0; i < INPUT_QUEUE_SIZE; i++) {
	    inputMessagePool.add(new InputMessage(inputMessagePool));
	}
    }

    public boolean handleTouchEvent(MotionEvent event) {
	// determine if is a touch down event
	if (event.getAction() == MotionEvent.ACTION_DOWN
		|| event.getAction() == MotionEvent.ACTION_UP) {
	    float x = event.getX();
	    float y = event.getY();
	    int eventType = -1;
	    if (game.withinAreaOfInterest(x, y)) {
		if (game.withinLeftButton(x, y)) {
		    eventType = event.getAction() == MotionEvent.ACTION_DOWN ? InputMessage.LEFT_ON
			    : InputMessage.LEFT_OFF;
		} else if (game.withinRightButton(x, y)) {
		    eventType = event.getAction() == MotionEvent.ACTION_DOWN ? InputMessage.RIGHT_ON
			    : InputMessage.RIGHT_OFF;
		} else if (game.withinFireButton(x, y)) {
		    eventType = event.getAction() == MotionEvent.ACTION_DOWN ? InputMessage.SHOOT_ON
			    : InputMessage.SHOOT_OFF;
		}
		sendMessage(eventType);
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

    final String tag = "GALAGA";
    final float stopThreshold = 0.5f;

    public void handleTiltEvent(SensorEvent event) {
	// TODO experiment with synchronized, may not be needed
	int eventType = -1;

	// synchronized (this) {
	if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
	} else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	    // values[0..2] are x, y, z axis (y is left/right tilt in
	    // landscape mode values[1]). Down right is positive.
	    if (Math.abs(event.values[1]) <= stopThreshold) {
		eventType = InputMessage.LEFT_RIGHT_OFF;
	    } else if (event.values[1] > stopThreshold) {
		eventType = InputMessage.RIGHT_ON;
	    } else if (event.values[1] < -stopThreshold) {
		eventType = InputMessage.LEFT_ON;
	    }
	    sendMessage(eventType);
	} else {
	    Log.d(tag, "Didn't catch sensor event: event.sensor.getType(): "
		    + event.sensor.getType());
	}
	// TODO experiment with sleeping to throttle events
	// }
	// don't allow more than 60 tilt events per second
	try {
	    Thread.sleep(16);
	} catch (InterruptedException e) {
	    // NOP
	}
    }

    private void sendMessage(int eventType) {
	if (!inputMessagePool.isEmpty()) {
	    try {
		InputMessage message = inputMessagePool.take();
		message.eventType = eventType;
		game.feedInput(message);
	    } catch (InterruptedException e) {
		Tools.log(e.toString());
	    }
	} else {
	    Tools.log("InputHandler.sendMessage(): "
		    + "message pool exhausted!");
	}
    }
}
