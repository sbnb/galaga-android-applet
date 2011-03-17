package com.gamefreezer.galaga;

public class Rectangle {
    public int left;
    public int right;
    public int top;
    public int bottom;

    public Rectangle(int left, int top, int width, int height) {
	this.left = left;
	this.right = left + width;
	this.top = top;
	this.bottom = top + height;
    }

    public void translate(int x, int y) {
	left += x;
	right += x;
	top += y;
	bottom += y;
    }

    public int width() {
	return right - left;
    }

    public int height() {
	return bottom - top;
    }

    public boolean contains(float x, float y) {
	return left <= x && x <= right && top <= y && y <= bottom;
    }
}
