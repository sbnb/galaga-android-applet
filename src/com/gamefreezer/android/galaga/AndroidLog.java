package com.gamefreezer.android.galaga;

import android.util.Log;

import com.gamefreezer.galaga.AbstractLog;

public class AndroidLog extends AbstractLog {

    @Override
    public void i(String tag, String message) {
	Log.i(TAG, message);
    }
}
