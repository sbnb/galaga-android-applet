package com.gamefreezer.applet.galaga;

import com.gamefreezer.galaga.AbstractFileLister;
import com.gamefreezer.galaga.Util;

public class AppletFileLister extends AbstractFileLister {

    @Override
    public String[] list() {
	return Util.getFileNamesInDir("data");
    }

}
