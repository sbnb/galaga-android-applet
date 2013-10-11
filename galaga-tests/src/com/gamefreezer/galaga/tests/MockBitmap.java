package com.gamefreezer.galaga.tests;

import com.gamefreezer.galaga.AbstractBitmap;

public class MockBitmap extends AbstractBitmap {

    private int width;
    private int height;

    public MockBitmap(int width, int height) {
	this.width = width;
	this.height = height;
    }

    @Override
    public int getHeight() {
	return height;
    }

    @Override
    public Object getNativeImage() {
	return null;
    }

    @Override
    public int getWidth() {
	return width;
    }

}
