package com.gamefreezer.galaga;

public class Border {

    public final int top;
    public final int right;
    public final int bottom;
    public final int left;

    public Border(int[] values) {
	assert values != null : "values is null!";
	assert values.length == 4 : "values.length is not 4!";

	top = values[0];
	right = values[1];
	bottom = values[2];
	left = values[3];
    }
}
