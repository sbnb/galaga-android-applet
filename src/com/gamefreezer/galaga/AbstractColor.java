package com.gamefreezer.galaga;

/*
 * Wrapper for java.awt.Color -- Android version will change this class.
 */

public abstract class AbstractColor {
    public abstract AbstractColor decode(String hex);

    public abstract Object getNativeColor();
}
