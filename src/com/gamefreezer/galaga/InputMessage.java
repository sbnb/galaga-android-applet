package com.gamefreezer.galaga;

import java.util.concurrent.ArrayBlockingQueue;

public class InputMessage {

    public static final int INPUT_QUEUE_SIZE = 30;
    public static final int EVENT_TYPE_KEY = 0;
    public static final int EVENT_TYPE_TOUCH = 1;

    public static final int LEFT_ON = 0;
    public static final int LEFT_OFF = 1;
    public static final int RIGHT_ON = 2;
    public static final int RIGHT_OFF = 3;
    public static final int SHOOT_ON = 4;
    public static final int SHOOT_OFF = 5;

    public int eventType;
    private ArrayBlockingQueue<InputMessage> pool;

    public InputMessage(ArrayBlockingQueue<InputMessage> pool) {
	this.pool = pool;
    }

    public void returnToPool() {
	pool.add(this);
    }

}
