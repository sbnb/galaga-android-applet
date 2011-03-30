package com.gamefreezer.galaga;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Util {

    private static final Random randomGenerator = new Random();

    public static int getRandom(int low, int high) {
	return randomGenerator.nextInt(high + 1 - low) + low;
    }

    public static List<String> getStringAsList(String someNames) {
	String[] names = someNames.split(" ");
	return Arrays.asList(names);
    }

    public static int[] getStringAsIntArray(String rawString) {
	if (rawString.equals("")) {
	    return new int[] {};
	}
	int[] arrayOfInt = new int[rawString.split(" ").length];
	Scanner tokenize = new Scanner(rawString);
	int i = 0;

	while (tokenize.hasNextInt()) {
	    arrayOfInt[i] = tokenize.nextInt();
	    i++;
	}
	assert i == arrayOfInt.length : "Non-int in string: " + rawString;
	return arrayOfInt;
    }

    public static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    public static void log(String message) {
	System.out.println(format.format(new Date()) + ": " + message);
    }

    // draw a number at specified location and spacing
    public static void drawNumber(SpriteCache spriteStore, Constants cfg,
	    AbstractGraphics graphics, int xStart, int yStart, int digitSpace,
	    int commaSpace, int number) {
	int numDigits = numDigits(number);
	int i = 1;
	int commaCount = 1;
	int x = xStart + (digitSpace * numDigits)
		+ (numDigits / 3 * commaSpace);
	int y = yStart;

	while (numDigits > 0) {
	    getNumeralSprite(spriteStore, cfg, getNumeral(i, number)).draw(
		    graphics, x, y);
	    x -= digitSpace;
	    if (commaCount % 3 == 0) {
		x -= commaSpace;
	    }
	    i *= 10;
	    --numDigits;
	    commaCount++;
	}
    }

    public static int getNumeral(int i, int number) {
	return (number % (i * 10)) / i;
    }

    public static Sprite getNumeralSprite(SpriteCache spriteStore,
	    Constants cfg, int n) {
	switch (n) {
	case 0:
	    return spriteStore.get(cfg.NUM_0);
	case 1:
	    return spriteStore.get(cfg.NUM_1);
	case 2:
	    return spriteStore.get(cfg.NUM_2);
	case 3:
	    return spriteStore.get(cfg.NUM_3);
	case 4:
	    return spriteStore.get(cfg.NUM_4);
	case 5:
	    return spriteStore.get(cfg.NUM_5);
	case 6:
	    return spriteStore.get(cfg.NUM_6);
	case 7:
	    return spriteStore.get(cfg.NUM_7);
	case 8:
	    return spriteStore.get(cfg.NUM_8);
	case 9:
	    return spriteStore.get(cfg.NUM_9);
	}
	assert false : "fell through switch - impossible. n: " + n;
	return spriteStore.get(cfg.NUM_0);
    }

    public static int numDigits(int number) {
	if (number == 0)
	    return 1;
	return (int) (Math.floor(Math.log10(number)) + 1);
    }

}
