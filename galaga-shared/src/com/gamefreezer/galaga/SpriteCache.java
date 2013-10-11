package com.gamefreezer.galaga;

import java.util.HashMap;
import java.util.Map;

public class SpriteCache {

    private Map<String, Sprite> cache = new HashMap<String, Sprite>();
    private AbstractBitmapReader bitmapReader;

    public SpriteCache(AbstractBitmapReader bitmapReader) {
	this.bitmapReader = bitmapReader;
    }

    public Sprite get(String name) {
	if (spriteIsInCache(name)) {
	    return cache.get(name);
	}
	Sprite sprite = createSpriteFromName(name);
	storeSpriteInCache(name, sprite);
	return sprite;
    }

    public int size() {
	return cache.size();
    }

    public void clear() {
	cache.clear();
    }

    private boolean spriteIsInCache(String name) {
	return cache.containsKey(name);
    }

    private void storeSpriteInCache(String name, Sprite sprite) {
	cache.put(name, sprite);
	assert cache.size() > 0 : "sprites cannot be empty";
    }

    private Sprite createSpriteFromName(String name) {
	assert bitmapReader != null : "SpriteStore.bitmapReader is null!";
	AbstractBitmap image = bitmapReader.read(name);
	Sprite sprite = new Sprite(image);
	return sprite;
    }
}
