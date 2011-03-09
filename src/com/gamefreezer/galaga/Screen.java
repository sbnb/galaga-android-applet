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

    /* Absolute screen width in pixels. */
    public int width() {
	return cfg.SCREEN_WIDTH;
    }

    /* Width excluding outer vertical borders. */
    public int drawableWidth() {
	return width() - outerVerticalBorderWidth() * 2;
    }

    /* Width excluding inner and outer vertical borders, where entities roam */
    public int inGameWidth() {
	return width() - verticalBorderWidths() * 2;
    }

    /* Absolute screen height in pixels. */
    public int height() {
	return cfg.SCREEN_HEIGHT;
    }

    /* Width excluding outer vertical borders. */
    public int drawableHeight() {
	return height() - outerHorizontalBorderWidth() * 2;
    }

    /* Height excluding inner and outer horizontal borders, where entities roam */
    public int inGameHeight() {
	return height() - horizontalBorderWidths() * 2;
    }

    /* first y pixel from top of screen in game */
    public int inGameTop() {
	return horizontalBorderWidths();
    }

    /* first y pixel from bottom of screen in game */
    public int inGameBottom() {
	return horizontalBorderWidths() + inGameHeight();
    }

    /* first x pixel from left of screen in game */
    public int inGameLeft() {
	return verticalBorderWidths();
    }

    /* first x pixel from right of screen in game */
    public int inGameRight() {
	return verticalBorderWidths() + inGameWidth();
    }

    /* x pixel in middle of screen */
    public int middleX() {
	return width() / 2;
    }

    /* y pixel in middle of screen */
    public int middleY() {
	return height() / 2;
    }

    /* Combined inner and outer vertical border widths (one side) */
    public int verticalBorderWidths() {
	return cfg.OUTER_VERTICAL_BORDER + cfg.INNER_VERTICAL_BORDER;
    }

    public int outerVerticalBorderWidth() {
	return cfg.OUTER_VERTICAL_BORDER;
    }

    public int innerVerticalBorderWidth() {
	return cfg.INNER_VERTICAL_BORDER;
    }

    /* Combined inner and outer horizontal widths (top or bottom) */
    public int horizontalBorderWidths() {
	return cfg.OUTER_HORIZONTAL_BORDER + cfg.INNER_HORIZONTAL_BORDER;
    }

    public int outerHorizontalBorderWidth() {
	return cfg.OUTER_HORIZONTAL_BORDER;
    }

    public int innerHorizontalBorderWidth() {
	return cfg.INNER_HORIZONTAL_BORDER;
    }

    public int bottomMaskHeight() {
	return cfg.BOTTOM_MASK_HEIGHT;
    }
}
