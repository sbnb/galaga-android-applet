package com.gamefreezer.applet.galaga;

import java.awt.image.BufferedImage;

import com.gamefreezer.galaga.AbstractBitmap;

public class AppletBitmap extends AbstractBitmap {
    private BufferedImage bufferedImage;

    public AppletBitmap(BufferedImage image) {
	bufferedImage = image;
    }

    @Override
    public int getWidth() {
	return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
	return bufferedImage.getHeight();
    }

    @Override
    public Object getNativeImage() {
	return bufferedImage;
    }

}
