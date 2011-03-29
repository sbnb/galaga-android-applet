package com.gamefreezer.galaga;

public class Sprite extends AllocGuard {

    private AbstractBitmap image;
    private final Dimension dimension;

    public Sprite(AbstractBitmap image) {
	super();
	this.image = image;
	dimension = new Dimension(image.getWidth(), image.getHeight());
    }

    public int getWidth() {
	// TODO return dimension.width directly
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

    public Dimension getDimensions() {
	return dimension;
    }
}
