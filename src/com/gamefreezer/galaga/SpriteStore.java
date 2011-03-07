package com.gamefreezer.galaga;

import java.util.HashMap;
import java.util.Map;

public class SpriteStore {

    // enforce Singleton pattern - no public instantiation allowed
    private SpriteStore() {
	// NOP
    }

    public static SpriteStore instance() {
	return myInstance;
    }

    // TODO optimization check how often this is being called, reduce if
    // possible
    public Sprite get(String name) {
	// Game.log("SpriteStore.get(): " + name);
	if (spriteIsInCache(name)) {
	    return spritesCache.get(name);
	}
	Sprite sprite = createSpriteFromName(name);
	storeSpriteInCache(name, sprite);
	return sprite;
    }

    public int size() {
	return spritesCache.size();
    }

    public void clear() {
	spritesCache.clear();
    }

    private boolean spriteIsInCache(String name) {
	return spritesCache.containsKey(name);
    }

    private void storeSpriteInCache(String name, Sprite sprite) {
	spritesCache.put(name, sprite);
	assert spritesCache.size() > 0 : "sprites cannot be empty";
    }

    private Sprite createSpriteFromName(String name) {
	AbstractBitmap image = Game.readBitmap(name);
	Sprite sprite = new Sprite(image);
	return sprite;
    }

    private static SpriteStore myInstance = new SpriteStore();
    private Map<String, Sprite> spritesCache = new HashMap<String, Sprite>();
}
