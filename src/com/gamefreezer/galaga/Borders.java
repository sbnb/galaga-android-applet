package com.gamefreezer.galaga;

public class Borders {

    private final Constants cfg;
    private final Screen screen;

    public Borders(Constants cfg) {
	this.cfg = cfg;
	this.screen = cfg.SCREEN;
    }

    public void draw(AbstractGraphics graphics) {
	// left and right outer vertical borders
	graphics.setColor(cfg.OUTER_VERT_BORDER_COLOR);
	graphics.fillRect(0, 0, screen.outerVerticalBorderWidth(), screen
		.height());
	graphics.fillRect(screen.width() - screen.outerVerticalBorderWidth(),
		0, screen.outerVerticalBorderWidth(), screen.height());

	// left and right inner vertical borders
	graphics.setColor(cfg.INNER_VERT_BORDER_COLOR);
	graphics.fillRect(screen.outerVerticalBorderWidth(), 0, screen
		.innerVerticalBorderWidth(), screen.height());
	graphics.fillRect(screen.width() - screen.verticalBorderWidths(), 0,
		screen.innerVerticalBorderWidth(), screen.height());

	// top and bottom outer horizontal borders
	graphics.setColor(cfg.OUTER_HORIZ_BORDER_COLOR);
	graphics.fillRect(0, 0, screen.width(), screen
		.outerHorizontalBorderWidth());
	graphics.fillRect(0, screen.height()
		- screen.outerHorizontalBorderWidth(), screen.width(), screen
		.outerHorizontalBorderWidth());

	// top and bottom inner horizontal borders
	graphics.setColor(cfg.INNER_HORIZ_BORDER_COLOR);
	graphics.fillRect(screen.outerVerticalBorderWidth(), screen
		.outerHorizontalBorderWidth(), screen.drawableWidth(), screen
		.innerHorizontalBorderWidth());
	graphics.fillRect(screen.outerVerticalBorderWidth(), screen.height()
		- screen.horizontalBorderWidths(), screen.drawableWidth(),
		screen.innerHorizontalBorderWidth());
    }

}
