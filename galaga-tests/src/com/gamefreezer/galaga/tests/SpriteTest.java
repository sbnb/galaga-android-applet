package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.AbstractBitmap;
import com.gamefreezer.galaga.AbstractGraphics;
import com.gamefreezer.galaga.Sprite;

public class SpriteTest {

    AbstractBitmap mockBitmap;
    AbstractGraphics graphics;
    Sprite sprite;
    int width = 10;
    int height = 10;

    @Before
    public void setUp() throws Exception {
	mockBitmap = new MockBitmap(width, height);
	graphics = new MockGraphics();
	sprite = new Sprite(mockBitmap);
    }

    @Test
    public void dimensionsAreAsExpected() {
	assertThat(sprite.width(), is(width));
	assertThat(sprite.height(), is(height));
    }

    @Test
    public void canDraw() {
	assertThat(sprite.draw(graphics, 0, 0), is(true));
    }
}
