package com.gamefreezer.applet.galaga;

import java.io.InputStream;

import com.gamefreezer.galaga.AbstractFileOpener;

public class AppletFileOpener extends AbstractFileOpener {

    @Override
    public InputStream open(String name) {
	InputStream in = AppletFileOpener.class.getResourceAsStream("data/"
		+ name);
	assert in != null : "Could not open file at data/" + name;
	return in;
    }

}
