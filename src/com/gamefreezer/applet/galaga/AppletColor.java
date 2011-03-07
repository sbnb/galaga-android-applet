package com.gamefreezer.applet.galaga;

import java.awt.Color;
import com.gamefreezer.galaga.AbstractColor;

/*
 * Wrapper for java.awt.Color -- Android version will provide different implementation of AbstractColor.
 */

public class AppletColor extends AbstractColor {
    private java.awt.Color color;

    public AppletColor(Color color) {
	this.color = color;
    }

    @Override
    public Object getNativeColor() {
	return color;
    }

    @Override
    public AbstractColor decode(String hex) {
	return new AppletColor(java.awt.Color.decode(hex));
    }
}
