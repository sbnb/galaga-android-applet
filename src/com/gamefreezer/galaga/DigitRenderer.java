package com.gamefreezer.galaga;

public class DigitRenderer {

    private Sprite[] digits;
    private int digitSpace;
    private int commaSpace;

    public DigitRenderer(SpriteCache spriteCache, String[] digitFiles,
	    int digitSpace, int commaSpace) {
	this.digits = new Sprite[digitFiles.length];
	for (int idx = 0; idx < digitFiles.length; idx++) {
	    this.digits[idx] = spriteCache.get(digitFiles[idx]);
	}
	this.digitSpace = digitSpace;
	this.commaSpace = commaSpace;
    }

    // draw a number at specified location
    public void draw(AbstractGraphics graphics, int atX, int atY, int number) {
	int numDigits = numDigits(number);
	int idx = 1;
	int commaCount = 1;
	int x = atX + (digitSpace * numDigits) + (numDigits / 3 * commaSpace);
	int y = atY;

	while (numDigits > 0) {
	    digits[getDigitAt(idx, number)].draw(graphics, x, y);
	    x -= digitSpace;
	    if (commaCount % 3 == 0) {
		x -= commaSpace;
	    }
	    idx *= 10;
	    --numDigits;
	    commaCount++;
	}
    }

    private int getDigitAt(int idx, int number) {
	return (number % (idx * 10)) / idx;
    }

    private int numDigits(int number) {
	if (number == 0) {
	    return 1;
	}
	return (int) (Math.floor(Math.log10(number)) + 1);
    }

}
