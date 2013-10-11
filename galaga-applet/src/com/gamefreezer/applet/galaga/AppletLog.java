package com.gamefreezer.applet.galaga;

import com.gamefreezer.galaga.AbstractLog;

public class AppletLog extends AbstractLog {

    @Override
    public void i(String tag, String message) {
	System.out.println(tag + " " + message);
    }
}
