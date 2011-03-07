package com.gamefreezer.android.galaga;

import com.gamefreezer.galaga.AbstractColor;
import android.graphics.Color;

/*
 * Android implementation of AbstractColor.
 */

public class AndroidColor extends AbstractColor {
    private int color;

    public AndroidColor(int color) {
	this.color = color;
    }

    @Override
    public Object getNativeColor() {
	return color;
    }

    @Override
    public AbstractColor decode(String hex) {
	return new AndroidColor(Color.parseColor(hex));
    }
}
