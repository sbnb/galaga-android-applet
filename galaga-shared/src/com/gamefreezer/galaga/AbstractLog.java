package com.gamefreezer.galaga;

public abstract class AbstractLog {
    protected static final String TAG = "GALAGA";

    public abstract void i(String tag, String message);

    public void i(String message) {
	i(TAG, message);
    }
}
