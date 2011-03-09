package com.gamefreezer.android.galaga;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

import com.gamefreezer.galaga.AbstractFileOpener;
import com.gamefreezer.galaga.Game;

public class AndroidFileOpener extends AbstractFileOpener {

    private AssetManager assetManager;

    public AndroidFileOpener(Context context) {
	assetManager = context.getAssets();
    }

    @Override
    public InputStream open(String name) {
	InputStream inputStream = null;
	try {
	    inputStream = assetManager.open(GameWrapper.DATA_DIR + "/" + name);
	} catch (IOException e) {
	    Game.log(e.getMessage());
	}
	return inputStream;
    }

}
