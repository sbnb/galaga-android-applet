package com.gamefreezer.applet.galaga;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gamefreezer.galaga.AbstractGraphics;
import com.gamefreezer.galaga.AbstractColor;
import com.gamefreezer.galaga.AbstractBitmap;
import com.gamefreezer.galaga.Screen;
import static com.gamefreezer.galaga.Constants.*;

/*
 * Applet implementation of AbstractGraphics graphics -- 
 * this file will change based on version: Applet, 
 * Android, others?
 */

public class AppletGraphics extends AbstractGraphics {
    private Graphics graphics;

    @Override
    public void set(Object graphics) {
	this.graphics = (Graphics) graphics;
    }

    @Override
    public void drawImage(AbstractBitmap image, int x, int y) {
	graphics.drawImage((BufferedImage) image.getNativeImage(), x, y, null);
    }

    @Override
    public void setColor(AbstractColor color) {
	graphics.setColor((Color) color.getNativeColor());
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
	graphics.drawRect(x, y, width, height);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
	graphics.fillRect(x, y, width, height);

    }

    @Override
    public void fillScreen() {
	graphics.setColor(Color.BLACK);
	graphics.fillRect(0, 0, LEFT_INDENT + SCREEN_WIDTH + LEFT_INDENT,
		Screen.height());

    }

}
