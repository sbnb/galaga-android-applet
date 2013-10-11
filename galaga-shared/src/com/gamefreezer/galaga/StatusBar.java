package com.gamefreezer.galaga;

import com.gamefreezer.galaga.Rectangle;

public class StatusBar {
    private Rectangle outline;
    private Rectangle filled;
    private float value;
    private AbstractColor outlineColor;
    private AbstractColor primaryFillColor;
    private AbstractColor secondaryFillColor;

    private enum MODE {
	PRIMARY, SECONDARY
    };

    private MODE mode;

    public StatusBar(Rectangle outline, float value,
	    AbstractColor outlineColor, AbstractColor primaryFillColor,
	    AbstractColor secondaryFillColor) {
	this.outline = outline;
	filled = new Rectangle(outline);
	this.value = value;
	this.outlineColor = outlineColor;
	this.primaryFillColor = primaryFillColor;
	this.secondaryFillColor = secondaryFillColor;
	mode = MODE.PRIMARY;
    }

    public void setValue(float value) {
	this.value = value;
	calculateSolidRectangle();
    }

    public void setPrimaryMode() {
	mode = MODE.PRIMARY;
    }

    public void setSecondaryMode() {
	mode = MODE.SECONDARY;
    }

    public void draw(AbstractGraphics graphics) {
	graphics.setColor(outlineColor);
	graphics.drawRect(outline);
	if (mode == MODE.PRIMARY)
	    graphics.setColor(primaryFillColor);
	else
	    graphics.setColor(secondaryFillColor);
	graphics.fillRect(filled);
    }

    private void calculateSolidRectangle() {
	final float percentage = value / 100f;
	filled.setWidth((int) (outline.width() * percentage));
	// Tools.log(tag, "value: " + value + " percentage: " + percentage
	// + " width: " + (outline.width() * percentage) + " ");
    }
}
