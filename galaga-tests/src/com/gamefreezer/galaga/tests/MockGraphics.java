package com.gamefreezer.galaga.tests;

import com.gamefreezer.galaga.AbstractBitmap;
import com.gamefreezer.galaga.AbstractColor;
import com.gamefreezer.galaga.AbstractGraphics;
import com.gamefreezer.galaga.Rectangle;

public class MockGraphics extends AbstractGraphics {

    public int x;
    public int y;

    public MockGraphics() {
    }

    @Override
    public void drawImage(AbstractBitmap image, int x, int y) {
	this.x = x;
	this.y = y;
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
    }

    @Override
    public void drawRect(Rectangle rect) {
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
    }

    @Override
    public void fillRect(Rectangle rect) {
    }

    @Override
    public void fillScreen() {
    }

    @Override
    public void set(Object graphics) {
    }

    @Override
    public void setColor(AbstractColor color) {
    }
}
