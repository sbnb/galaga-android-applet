package com.gamefreezer.galaga;

public class CartesianInt {
    public final int x;
    public final int y;

    public CartesianInt(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public CartesianInt(int[] values) {
	assert values != null : "values is null!";
	assert values.length == 2 : "values is not length 2!";
	this.x = values[0];
	this.y = values[1];
    }
}
