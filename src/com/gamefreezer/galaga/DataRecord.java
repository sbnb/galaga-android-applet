package com.gamefreezer.galaga;

public class DataRecord {
    public int xLocation;
    public int yLocation;
    public final AnimationSource animationSource;
    public final int width;
    public final int height;
    public final int points;

    public DataRecord(int xLocation, int yLocation,
	    AnimationSource animationSource, int width, int height, int points) {
	this.xLocation = xLocation;
	this.yLocation = yLocation;
	this.animationSource = animationSource;
	this.width = width;
	this.height = height;
	this.points = points;
    }
}
