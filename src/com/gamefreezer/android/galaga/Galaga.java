package com.gamefreezer.android.galaga;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.gamefreezer.galaga.Game;

public class Galaga extends Activity {

    public static final String DATA_DIR = "data";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	init();
	Game.log("Galaga.oncreate() START");
	setImageViewWithOnDraw();
	Game.log("Galaga.oncreate() END");
    }

    private void init() {
	androidGraphics = new AndroidGraphics();
	log = new AndroidLog();
	bitmapReader = new AndroidBitmapReader(this);
	colorDecoder = new AndroidColor(Color.GREEN);
	fileOpener = new AndroidFileOpener(this);
	fileLister = new AndroidFileLister(this);
	Game.setAbstractInterfaceVars(log, bitmapReader, colorDecoder,
		fileOpener, fileLister);
	game = new Game();
    }

    private void setImageViewWithOnDraw() {
	setContentView(new MyView(this));
    }

    private class MyView extends ImageView {

	public MyView(Context context) {
	    super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
	    canvas.drawBitmap((Bitmap) bitmapReader.read("bg_starfield.png")
		    .getNativeImage(), 0, 0, null);
	    canvas.drawBitmap((Bitmap) bitmapReader.read("alien_a.png")
		    .getNativeImage(), 0, 0, null);
	    canvas.drawBitmap((Bitmap) bitmapReader.read("alien_b.png")
		    .getNativeImage(), 30, 0, null);
	}
    }

    @SuppressWarnings("unused")
    private Game game;
    @SuppressWarnings("unused")
    private AndroidGraphics androidGraphics;
    private AndroidLog log;
    private AndroidBitmapReader bitmapReader;
    private AndroidColor colorDecoder;
    private AndroidFileOpener fileOpener;
    private AndroidFileLister fileLister;
}