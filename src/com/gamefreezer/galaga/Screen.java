package com.gamefreezer.galaga;

public class Screen {

    public final Border outerBorder;
    public final Border innerBorder;
    private int width;
    private int height;

    public Screen(int width, int height, Border outerBorder, Border innerBorder) {
	this.width = width;
	this.height = height;
	this.outerBorder = outerBorder;
	this.innerBorder = innerBorder;
    }

    /* Absolute screen width in pixels. */
    public int width() {
	return width;
    }

    /* Width excluding outer vertical borders. */
    public int drawableWidth() {
	return width() - (outerBorder.left + outerBorder.right);
    }

    /* Width excluding inner and outer vertical borders, where entities roam */
    public int inGameWidth() {
	return drawableWidth() - (innerBorder.left + innerBorder.right);
    }

    /* Absolute screen height in pixels. */
    public int height() {
	return height;
    }

    /* Width excluding outer vertical borders. */
    public int drawableHeight() {
	return height() - (outerBorder.top + outerBorder.bottom);
    }

    /* Height excluding inner and outer horizontal borders, where entities roam */
    public int inGameHeight() {
	return drawableHeight() - (innerBorder.top + innerBorder.bottom);
    }

    /* first y pixel from top of screen in game */
    public int inGameTop() {
	return outerBorder.top + innerBorder.top;
    }

    /* first y pixel from bottom of screen in game */
    public int inGameBottom() {
	return inGameTop() + inGameHeight();
    }

    /* first x pixel from left of screen in game */
    public int inGameLeft() {
	return outerBorder.left + innerBorder.left;
    }

    /* first x pixel from right of screen in game */
    public int inGameRight() {
	return inGameLeft() + inGameWidth();
    }

    /* x pixel in middle of screen */
    public int middleX() {
	return width() / 2;
    }

    /* y pixel in middle of screen */
    public int middleY() {
	return height() / 2;
    }
}
