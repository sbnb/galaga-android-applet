package com.gamefreezer.galaga;

public class Buttons {
    private Constants cfg;
    private Screen screen;
    private Rectangle leftButton;
    private Rectangle rightButton;
    private Rectangle fireButton;

    public Buttons(Constants cfg) {
	this.cfg = cfg;
	this.screen = cfg.SCREEN;

	// TODO magic numbers for buttons
	int width = 30;
	int height = 60;
	int offset = 10;

	leftButton = new Rectangle(0, 0, width, height);
	leftButton.translate(offset, screen.height() - height - offset);
	rightButton = new Rectangle(0, 0, width, height);
	rightButton.translate(screen.width() - width - offset, screen.height()
		- height - offset);
	fireButton = new Rectangle(0, 0, width, height);
	fireButton.translate(offset, screen.height() - height * 2 - offset * 2);
    }

    public void draw(AbstractGraphics graphics) {
	graphics.setColor(cfg.BUTTON_COLOR);
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
