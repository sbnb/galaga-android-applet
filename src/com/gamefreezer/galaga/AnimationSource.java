package com.gamefreezer.galaga;

public class AnimationSource {
    public final String[] names;
    public final int[] times;

    public AnimationSource(String[] names, int[] times) {
	this.names = names;
	this.times = times;
    }

    public int width(SpriteCache spriteCache) {
	enforcePreconditions(spriteCache);
	return spriteCache.get(names[0]).width();
    }

    public int height(SpriteCache spriteCache) {
	enforcePreconditions(spriteCache);
	return spriteCache.get(names[0]).height();
    }

    private void enforcePreconditions(SpriteCache spriteCache) {
	assert spriteCache != null : "spriteCache is null!";
	assert names != null : "names is null!";
	assert names.length != 0 : "names is empty!";
    }
}
