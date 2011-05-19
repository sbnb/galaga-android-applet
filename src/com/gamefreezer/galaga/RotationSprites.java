package com.gamefreezer.galaga;

public class RotationSprites {

    private final String[] paddedNumbers;
    private SpriteCache spriteCache;
    private String prefix;
    private String suffix;
    private RotationMapper rotationMapper;

    public RotationSprites(SpriteCache spriteCache, String prefix,
	    String suffix, RotationMapper rotationMapper) {
	this.spriteCache = spriteCache;
	this.prefix = prefix;
	this.suffix = suffix;
	this.rotationMapper = rotationMapper;

	paddedNumbers = new String[360];
	for (int idx = 0; idx < paddedNumbers.length; idx++) {
	    if (idx < 10) {
		paddedNumbers[idx] = "00" + idx;
	    } else if (idx < 100) {
		paddedNumbers[idx] = "0" + idx;
	    } else {
		paddedNumbers[idx] = "" + idx;
	    }
	}

    }

    public void preload(SpriteCache spriteCache) {
	for (int idx = 0; idx < 360; idx++) {
	    spriteCache.get(mapRotationToName(idx));
	}
    }

    public String mapRotationToName(float rotation) {
	return prefix + paddedNumbers[rotationMapper.nearestRotation(rotation)]
		+ suffix;
    }

    public void drawCenteredAt(AbstractGraphics graphics, Location center,
	    float rotation) {
	final String name = mapRotationToName(rotation);
	Sprite sprite = spriteCache.get(name);
	int x = (int) (center.getXAsFloat() - sprite.width() / 2);
	int y = (int) (center.getYAsFloat() - sprite.height() / 2);
	sprite.draw(graphics, x, y);
    }

    public void draw(AbstractGraphics graphics, int x, int y, float rotation) {
	final String name = mapRotationToName(rotation);
	spriteCache.get(name).draw(graphics, x, y);
    }

}
