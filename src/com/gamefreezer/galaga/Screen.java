package com.gamefreezer.galaga;

public class Screen {

    public static int x;
    public static int y;
    public static int w;
    public static int h;

    private Constants cfg;

    public Screen(Constants cfg) {
	this.cfg = cfg;
    }

    /*
     * Convert logical location to screen location - logical location 0,0 is
     * bottom left, screen location 0,0 is top left.
     */
    public int translateX(int x) {
	return x + leftIndent();
    }

    public int translateY(int y) {
	return height() - y + topIndent();
    }

    public int left() {
	return cfg.LEFT_EDGE;
    }

    public int right() {
	return cfg.LEFT_EDGE + cfg.SCREEN_WIDTH;
    }

    public int bottom() {
	return cfg.BOTTOM_EDGE;
    }

    public int width() {
	return cfg.SCREEN_WIDTH;
    }

    private boolean firstTime = true;

    public int height() {
	if (firstTime) {
	    Game.log("Screen.height() called");
	    firstTime = false;
	}
	return cfg.SCREEN_HEIGHT;
    }

    public int playableTop() {
	return cfg.SCREEN_HEIGHT - cfg.INNER_TOP_INDENT;
    }

    public int playableBottom() {
	return cfg.BOTTOM_EDGE + cfg.INNER_BOTTOM_INDENT;
    }

    public int leftIndent() {
	return cfg.LEFT_INDENT;
    }

    public int topIndent() {
	return cfg.TOP_INDENT;
    }

    public int middleHorizontal() {
	return cfg.LEFT_EDGE + cfg.SCREEN_WIDTH / 2;
    }

    public int middleX() {
	return cfg.LEFT_EDGE + cfg.SCREEN_WIDTH / 2;
    }

    public int middleY() {
	return playableTop() - playableBottom();
    }

    public int centerImageX(int width) {
	return middleX() - width / 2;
    }

    public int centerImageY(int height) {
	return middleY() - height / 2;
    }

    public int bottomMaskHeight() {
	return cfg.BOTTOM_MASK_HEIGHT;
    }
}
