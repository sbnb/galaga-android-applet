package com.gamefreezer.applet.galaga;

import java.io.InputStream;

import com.gamefreezer.galaga.AbstractFileOpener;

public class AppletFileOpener extends AbstractFileOpener {

    @Override
    public InputStream open(String name) {
	String fullPath = SpaceWar.DATA_DIR + "/" + name;
	InputStream in = AppletFileOpener.class.getResourceAsStream(fullPath);
	assert in != null : "Could not open file at " + fullPath;
	return in;
    }

}
