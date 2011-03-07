package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.NUM_0;
import static com.gamefreezer.galaga.Constants.NUM_1;
import static com.gamefreezer.galaga.Constants.NUM_2;
import static com.gamefreezer.galaga.Constants.NUM_3;
import static com.gamefreezer.galaga.Constants.NUM_4;
import static com.gamefreezer.galaga.Constants.NUM_5;
import static com.gamefreezer.galaga.Constants.NUM_6;
import static com.gamefreezer.galaga.Constants.NUM_7;
import static com.gamefreezer.galaga.Constants.NUM_8;
import static com.gamefreezer.galaga.Constants.NUM_9;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Util {

    private static final Random randomGenerator = new Random();

    public static int getRandomX() {
	return getRandom(Screen.left() + 1, Screen.width() - 1);
    }

    public static int getRandomY() {
	return getRandom(Screen.bottom() + 1, Screen.playableTop() - 1);
    }

    public static int getRandom(int low, int high) {
	return randomGenerator.nextInt(high + 1 - low) + low;
    }

    // static function to return sprite width from imgUrl
    public static int widthFromSprite(String aImageUrl) {
	// TODO don't use List - it will need to be GC'd
	String firstImage = Util.getStringAsList(aImageUrl).get(0);
	Sprite aSprite = SpriteStore.instance().get(firstImage);
	return aSprite.getWidth();
    }

    // static function to return sprite height from imgUrl
    public static int heightFromSprite(String aImageUrl) {
	String firstImage = Util.getStringAsList(aImageUrl).get(0);
	Sprite aSprite = SpriteStore.instance().get(firstImage);
	return aSprite.getHeight();
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

    // TODO this is Applet specific and should therefore move into
    // AppletFileLister
    public static String[] getFileNamesInDir(FilenameFilter filter,
	    String aDirectory) {
	String[] fileNames = {};
	try {
	    URI uri = Util.class.getResource(aDirectory).toURI();
	    fileNames = new File(uri).list(filter);
	} catch (Exception e) {
	    throw new AssertionError("\nCan't read from dir: " + aDirectory
		    + "\n" + e);
	}
	return fileNames;
    }

    public static String[] getFileNamesInDir(String aDirectory) {
	return getFileNamesInDir(null, aDirectory);
    }

    public static List<String> getFilesAsList(String aDirectory) {
	return Arrays.asList(getFileNamesInDir(aDirectory));
    }

    // draw a number at specified location and spacing
    public static void drawNumber(AbstractGraphics graphics, int xStart,
	    int yStart, int digitSpace, int commaSpace, int number) {
	int numDigits = numDigits(number);
	int i = 1;
	int commaCount = 1;
	int x = xStart + (digitSpace * numDigits)
		+ (numDigits / 3 * commaSpace);
	int y = yStart;

	while (numDigits > 0) {
	    getNumeralSprite(getNumeral(i, number)).draw(graphics, x, y);
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

    public static Sprite getNumeralSprite(int n) {
	switch (n) {
	case 0:
	    return SpriteStore.instance().get(NUM_0);
	case 1:
	    return SpriteStore.instance().get(NUM_1);
	case 2:
	    return SpriteStore.instance().get(NUM_2);
	case 3:
	    return SpriteStore.instance().get(NUM_3);
	case 4:
	    return SpriteStore.instance().get(NUM_4);
	case 5:
	    return SpriteStore.instance().get(NUM_5);
	case 6:
	    return SpriteStore.instance().get(NUM_6);
	case 7:
	    return SpriteStore.instance().get(NUM_7);
	case 8:
	    return SpriteStore.instance().get(NUM_8);
	case 9:
	    return SpriteStore.instance().get(NUM_9);
	}
	assert false : "fell through switch - impossible. n: " + n;
	return SpriteStore.instance().get(NUM_0);
    }

    public static int numDigits(int number) {
	if (number == 0)
	    return 1;
	return (int) (Math.floor(Math.log10(number)) + 1);
    }

}
