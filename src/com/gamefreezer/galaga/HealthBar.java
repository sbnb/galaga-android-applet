package com.gamefreezer.galaga;

public class HealthBar {

    private Constants cfg;
    private Screen screen;
    private int healthBarX;
    private int healthBarY;
    private int healthBarWidth;
    private Score score;

    public HealthBar(Constants cfg, Score score) {
	this.cfg = cfg;
	screen = cfg.SCREEN;
	this.score = score;
	// healthBarX = screen.inGameLeft() + cfg.HEALTH_SIDE_INDENT;
	// healthBarY = screen.inGameBottom() + cfg.HEALTH_BOTTOM_INDENT;
	// healthBarWidth = screen.inGameWidth() - (cfg.HEALTH_SIDE_INDENT * 2);

	healthBarX = screen.inGameRight() - cfg.HEALTH_BAR_WIDTH
		- cfg.HEALTH_SIDE_INDENT;
	healthBarY = screen.inGameTop() + cfg.HEALTH_BOTTOM_INDENT;
	healthBarWidth = cfg.HEALTH_BAR_WIDTH;
    }

    public void draw(AbstractGraphics graphics) {
	graphics.setColor(cfg.HEALTH_BAR_OUTLINE);
	graphics.drawRect(healthBarX, healthBarY, healthBarWidth + 1,
		cfg.HEALTH_BAR_HEIGHT);

	int newHealthBarWidth = healthBarWidth + 1;
	float percentageHealth = score.getHealth() / 100f;
	newHealthBarWidth = (int) (newHealthBarWidth * percentageHealth);

	graphics.setColor(cfg.HEALTH_BAR_HIGH);
	if (percentageHealth < cfg.HEALTH_COLOR_CHANGE) {
	    graphics.setColor(cfg.HEALTH_BAR_LOW);
	}
	graphics.fillRect(healthBarX + 1, healthBarY + 1,
		newHealthBarWidth - 1, cfg.HEALTH_BAR_HEIGHT - 1);
    }

}
