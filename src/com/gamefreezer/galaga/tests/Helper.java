package com.gamefreezer.galaga.tests;

import com.gamefreezer.applet.galaga.AppletBitmapReader;
import com.gamefreezer.galaga.Animation;
import com.gamefreezer.galaga.AnimationSource;
import com.gamefreezer.galaga.Border;
import com.gamefreezer.galaga.Screen;
import com.gamefreezer.galaga.SpriteCache;

public class Helper {
    public static SpriteCache buildSpriteCache() {
	AppletBitmapReader bitmapReader = new AppletBitmapReader();
	return new SpriteCache(bitmapReader);
    }

    public static Screen buildScreen() {
	return new Screen(400, 200, new Border(new int[] { 2, 2, 2, 2 }),
		new Border(new int[] { 2, 2, 2, 2 }));
    }

    public static AnimationSource buildAnimationSource() {
	String[] names = new String[] { "bullet_1.png", "bullet_2.png" };
	int[] times = new int[] { 0, 0 };
	return new AnimationSource(names, times);
    }

    public static Animation buildAnimation(boolean oneShot) {
	AnimationSource src = Helper.buildAnimationSource();
	SpriteCache spriteCache = Helper.buildSpriteCache();
	Animation anim = new Animation(spriteCache, src, oneShot);
	return anim;
    }

}
