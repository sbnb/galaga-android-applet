package com.gamefreezer.galaga;

import java.io.InputStream;

public class Tools {
    public static AbstractBitmapReader bitmapReader = null;
    public static boolean initialised = false;
    private static AbstractLog log = null;
    private static AbstractColor colorDecoder = null;
    private static AbstractFileOpener fileOpener = null;
    private static AbstractFileLister fileLister = null;

    public static void setAbstractInterfaceVars(AbstractLog log,
	    AbstractBitmapReader bitmapReader, AbstractColor colorDecoder,
	    AbstractFileOpener fileOpener, AbstractFileLister fileLister) {
	Tools.log = log;
	Tools.bitmapReader = bitmapReader;
	Tools.colorDecoder = colorDecoder;
	Tools.fileOpener = fileOpener;
	Tools.fileLister = fileLister;
	Tools.initialised = true;
    }

    public static String[] listFiles() {
	assert fileLister != null : "Tools.fileLister is null!";
	return fileLister.list();
    }

    public static InputStream openFile(String name) {
	assert fileOpener != null : "Tools.fileOpener is null!";
	return fileOpener.open(name);
    }

    public static AbstractColor decodeColor(String property) {
	assert colorDecoder != null : "Tools.colorDecoder is null!";
	return colorDecoder.decode(property);
    }

    public static void log(String tag, String message) {
	assert Tools.log != null : "Tools.log is null!";
	Tools.log.i(tag, message);
    }

    public static void log(String message) {
	assert Tools.log != null : "Tools.log is null!";
	Tools.log.i(message);
    }

}
