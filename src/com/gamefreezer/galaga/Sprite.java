package com.gamefreezer.galaga;

public class Sprite extends AllocGuard {

    private AbstractBitmap image;
    private final Dimension dimension;

    public Sprite(AbstractBitmap image) {
	super();
	this.image = image;
	dimension = new Dimension(image.getWidth(), image.getHeight());
    }

    public Dimension getDimensions() {
	return dimension;
    }

    public int height() {
	return dimension.height;
    }

    public int width() {
	return dimension.width;
    }

    public boolean draw(AbstractGraphics graphics, int x, int y) {
	graphics.drawImage(image, x, y);
	return true;
    }

    public boolean draw(AbstractGraphics graphics, CartesianInt point) {
	return draw(graphics, point.x, point.y);
    }
}
