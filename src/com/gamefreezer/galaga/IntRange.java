package com.gamefreezer.galaga;

public class IntRange {
    public final int min;
    public final int max;

    public IntRange(int min, int max) {
	assert min <= max : "min > max! " + min + "," + max;
	this.min = min;
	this.max = max;
    }

    public IntRange(int[] values) {
	assert values != null : "values is null!";
	assert values.length == 2 : "values is not length 2!";
	this.min = values[0];
	this.max = values[1];
	assert min <= max : "min > max! " + min + "," + max;
    }

    public int random() {
	return Util.getRandom(min, max);
    }

}
