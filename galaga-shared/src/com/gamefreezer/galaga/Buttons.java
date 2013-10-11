package com.gamefreezer.galaga;

public class Buttons {
    private Rectangle leftButton;
    private Rectangle rightButton;
    private Rectangle fireButton;
    private AbstractColor buttonClr;

    public Buttons(Screen screen, AbstractColor buttonClr, int width,
	    int height, int offset) {
	this.buttonClr = buttonClr;

	leftButton = new Rectangle(0, 0, width, height);
	leftButton.translate(offset, screen.height() - height - offset);
	rightButton = new Rectangle(0, 0, width, height);
	rightButton.translate(screen.width() - width - offset, screen.height()
		- height - offset);
	fireButton = new Rectangle(0, 0, width, height);
	fireButton.translate(offset, screen.height() - height * 2 - offset * 2);
    }

    public void draw(AbstractGraphics graphics) {
	graphics.setColor(buttonClr);
	graphics.fillRect(leftButton.left, leftButton.top, leftButton.width(),
		leftButton.height());
	graphics.fillRect(rightButton.left, rightButton.top, rightButton
		.width(), rightButton.height());
	graphics.fillRect(fireButton.left, fireButton.top, fireButton.width(),
		fireButton.height());
    }

    public boolean withinLeftButton(float x, float y) {
	return leftButton.contains(x, y);
    }

    public boolean withinRightButton(float x, float y) {
	return rightButton.contains(x, y);
    }

    public boolean withinFireButton(float x, float y) {
	return fireButton.contains(x, y);
    }

}
