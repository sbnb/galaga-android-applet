package com.gamefreezer.galaga.tests;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.gamefreezer.galaga.Border;
import com.gamefreezer.galaga.Screen;

public class ScreenTest {

    private Screen screen;
    private int width = 400;
    private int height = 200;
    private int outerTop = 10;
    private int outerRight = 9;
    private int outerBottom = 8;
    private int outerLeft = 7;
    private int innerTop = 6;
    private int innerRight = 5;
    private int innerBottom = 4;
    private int innerLeft = 3;
    private Border outerBorder = new Border(new int[] { outerTop, outerRight,
	    outerBottom, outerLeft });
    private Border innerBorder = new Border(new int[] { innerTop, innerRight,
	    innerBottom, innerLeft });

    @Before
    public void setUp() {
	screen = new Screen(width, height, outerBorder, innerBorder);
    }

    @Test
    public void drawableWidthOk() {
	assertThat(screen.drawableWidth(), is(width - outerLeft - outerRight));
    }

    @Test
    public void drawableHeightOk() {
	assertThat(screen.drawableHeight(), is(height - outerTop - outerBottom));
    }

    @Test
    public void inGameWidthOk() {
	assertThat(screen.inGameWidth(), is(width - outerLeft - outerRight
		- innerLeft - innerRight));
    }

    @Test
    public void inGameHeightOk() {
	assertThat(screen.inGameHeight(), is(height - outerTop - outerBottom
		- innerTop - innerBottom));
    }

    @Test
    public void inGameTopOk() {
	assertThat(screen.inGameTop(), is(outerTop + innerTop));
    }

    @Test
    public void inGameBottomOk() {
	assertThat(screen.inGameBottom(),
		is(height - outerBottom - innerBottom));
    }

    @Test
    public void inGameLeftOk() {
	assertThat(screen.inGameLeft(), is(outerLeft + innerLeft));
    }

    @Test
    public void inGameRightOk() {
	assertThat(screen.inGameRight(), is(width - outerRight - innerRight));
    }
}
