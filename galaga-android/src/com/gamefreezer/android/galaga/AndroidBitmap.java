package com.gamefreezer.android.galaga;

import android.graphics.Bitmap;

import com.gamefreezer.galaga.AbstractBitmap;

public class AndroidBitmap extends AbstractBitmap {
    private Bitmap image;

    public AndroidBitmap(Bitmap image) {
	this.image = image;
    }

    @Override
    public int getWidth() {
	return image.getWidth();
    }

    @Override
    public int getHeight() {
	return image.getHeight();
    }

    @Override
    public Object getNativeImage() {
	return image;
    }
    
    @Override
    public String toString() {
        return "Bitmap: height: "+image.getHeight()+" width: " + image.getWidth();
    }

}
