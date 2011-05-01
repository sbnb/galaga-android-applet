package com.gamefreezer.galaga;

import java.util.Random;

public class Util {

    private static final Random randomGenerator = new Random();

    public static int getRandom(int low, int high) {
	return randomGenerator.nextInt(high + 1 - low) + low;
    }
}
