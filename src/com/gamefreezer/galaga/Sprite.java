package com.gamefreezer.galaga;

public class Sprite extends AllocGuard {

    public Sprite(AbstractBitmap image) {
	super();
	this.image = image;
    }

    public int getWidth() {
	return image.getWidth();
    }

    public int getHeight() {
	return image.getHeight();
    }

    public boolean draw(AbstractGraphics graphics, int x, int y) {
	graphics.drawImage(image, x, y);
	return true;
    }

    public boolean draw(AbstractGraphics graphics, CartesianInt point) {
	return draw(graphics, point.x, point.y);
    }

    private AbstractBitmap image;

}
