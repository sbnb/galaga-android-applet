package com.gamefreezer.android.galaga;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetManager;

import com.gamefreezer.galaga.AbstractFileLister;
import com.gamefreezer.galaga.Game;

public class AndroidFileLister extends AbstractFileLister {

    private AssetManager assetManager;

    public AndroidFileLister(Context context) {
	assetManager = context.getAssets();
    }

    @Override
    public String[] list() {
	String[] files = null;
	try {
	    files = assetManager.list(Galaga.DATA_DIR);
	} catch (IOException e) {
	    Game.log(e.getMessage());
	}
	return files;
    }

}
