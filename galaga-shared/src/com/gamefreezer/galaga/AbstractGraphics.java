package com.gamefreezer.galaga;

/*
 * Wrapper for graphics -- this file will change based on version: Applet, Android, others?
 */

public abstract class AbstractGraphics {

    public abstract void set(Object graphics);

    public abstract void drawImage(AbstractBitmap image, int x, int y);

    public abstract void setColor(AbstractColor color);

    public abstract void drawRect(int x, int y, int width, int height);

    public abstract void drawRect(Rectangle rect);

    public abstract void fillRect(int x, int y, int width, int height);

    public abstract void fillRect(Rectangle rect);

    public abstract void fillScreen();
}
