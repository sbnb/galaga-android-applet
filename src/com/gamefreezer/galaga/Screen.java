package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.*;

public class Screen {

    public static int x;
    public static int y;
    public static int w;
    public static int h;

    /*
     * Convert logical location to screen location - logical location 0,0 is
     * bottom left, screen location 0,0 is top left.
     */
    public static int translateX(int x) {
	return x + Screen.leftIndent();
    }

    public static int translateY(int y) {
	return Screen.height() - y + Screen.topIndent();
    }

    public static int left() {
	return LEFT_EDGE;
    }

    public static int right() {
	return LEFT_EDGE + SCREEN_WIDTH;
    }

    public static int bottom() {
	return BOTTOM_EDGE;
    }

    public static int width() {
	return SCREEN_WIDTH;
    }

    private static boolean firstTime = true;

    public static int height() {
	if (firstTime) {
	    Game.log("Screen.height() called");
	    firstTime = false;
	}
	return SCREEN_HEIGHT;
    }

    public static int playableTop() {
	return SCREEN_HEIGHT - INNER_TOP_INDENT;
    }

    public static int playableBottom() {
	return BOTTOM_EDGE + INNER_BOTTOM_INDENT;
    }

    public static int leftIndent() {
	return LEFT_INDENT;
    }

    public static int topIndent() {
	return TOP_INDENT;
    }

    public static int middleHorizontal() {
	return LEFT_EDGE + SCREEN_WIDTH / 2;
    }

    public static int middleX() {
	return LEFT_EDGE + SCREEN_WIDTH / 2;
    }
}
