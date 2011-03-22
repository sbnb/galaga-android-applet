package com.gamefreezer.galaga;

public class BorderRenderer {

    private final Screen screen;
    private Border outerBorder;
    private Border innerBorder;
    private AbstractColor outerBorderColor;
    private AbstractColor innerBorderColor;

    public BorderRenderer(Screen screen, AbstractColor outerBorderColor,
	    AbstractColor innerBorderColor) {
	this.screen = screen;
	this.outerBorder = screen.outerBorder;
	this.innerBorder = screen.innerBorder;
	this.outerBorderColor = outerBorderColor;
	this.innerBorderColor = innerBorderColor;
    }

    public void draw(AbstractGraphics graphics) {
	// left and right inner vertical borders
	graphics.setColor(innerBorderColor);
	graphics.fillRect(outerBorder.left, 0, innerBorder.left, screen
		.height());
	graphics.fillRect(screen.width() - outerBorder.right
		- innerBorder.right, 0, innerBorder.right, screen.height());

	// top and bottom inner horizontal borders
	graphics.fillRect(outerBorder.left, outerBorder.top, screen
		.drawableWidth(), innerBorder.top);
	graphics.fillRect(outerBorder.left, screen.height()
		- outerBorder.bottom - innerBorder.bottom, screen
		.drawableWidth(), innerBorder.bottom);

	// left and right outer vertical borders
	graphics.setColor(outerBorderColor);
	graphics.fillRect(0, 0, outerBorder.left, screen.height());
	graphics.fillRect(screen.width() - outerBorder.right, 0,
		outerBorder.right, screen.height());

	// top and bottom outer horizontal borders
	graphics.fillRect(0, 0, screen.width(), outerBorder.top);
	graphics.fillRect(0, screen.height() - outerBorder.bottom, screen
		.width(), outerBorder.bottom);

    }

}
