package com.gamefreezer.android.galaga;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.gamefreezer.galaga.AbstractGraphics;
import com.gamefreezer.galaga.AbstractColor;
import com.gamefreezer.galaga.AbstractBitmap;

/*
 * Applet implementation of AbstractGraphics graphics -- 
 * this file will change based on version: Applet, 
 * Android, others?
 */

public class AndroidGraphics extends AbstractGraphics {
    private Canvas canvas;
    private Paint paint = new Paint();

    @Override
    public void set(Object canvas) {
	this.canvas = (Canvas) canvas;
    }

    @Override
    public void drawImage(AbstractBitmap image, int x, int y) {
	canvas.drawBitmap((Bitmap) image.getNativeImage(), (float) x,
		(float) y, null);
    }

    @Override
    public void setColor(AbstractColor color) {
	paint.setColor(color.getNativeColorAsInt());
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
	paint.setStyle(Style.STROKE);
	canvas.drawRect(x, y, (x + width), (y + height), paint);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
	paint.setStyle(Style.FILL);
	canvas.drawRect(x, y, (x + width), (y + height), paint);
    }

    @Override
    public void fillScreen() {
	canvas.drawColor(Color.BLACK);
    }

}
