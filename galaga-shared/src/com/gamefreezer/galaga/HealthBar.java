package com.gamefreezer.galaga;

public class HealthBar {

    private Rectangle rect;
    private Score score;
    private AbstractColor outlineClr;
    private AbstractColor highClr;
    private AbstractColor lowClr;
    private float clrChange;

    public HealthBar(Rectangle rect, Score score, AbstractColor outlineClr,
	    AbstractColor highClr, AbstractColor lowClr, float clrChange) {
	this.rect = rect;
	this.score = score;
	this.outlineClr = outlineClr;
	this.highClr = highClr;
	this.lowClr = lowClr;
	this.clrChange = clrChange;
    }

    public void draw(AbstractGraphics graphics) {
	graphics.setColor(outlineClr);
	graphics.drawRect(rect);

	int newHealthBarWidth = rect.width() + 1;
	float percentageHealth = score.getHealth() / 100f;
	newHealthBarWidth = (int) (newHealthBarWidth * percentageHealth);

	graphics.setColor(highClr);
	if (percentageHealth < clrChange) {
	    graphics.setColor(lowClr);
	}
	graphics.fillRect(rect.left, rect.top, //
		newHealthBarWidth, rect.height());
    }

}
