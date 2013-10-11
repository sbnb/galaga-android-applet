package com.gamefreezer.galaga;

public class CartesianIntRange extends AllocGuard {

    public final IntRange x;
    public final IntRange y;

    public CartesianIntRange(int xMin, int xMax, int yMin, int yMax) {
	super();
	x = new IntRange(xMin, xMax);
	y = new IntRange(yMin, yMax);
    }

    public CartesianIntRange(int[] values) {
	super();
	assert values != null : "values is null!";
	assert values.length == 4 : "values is not length 4!";
	x = new IntRange(values[0], values[1]);
	y = new IntRange(values[2], values[3]);
    }

}
