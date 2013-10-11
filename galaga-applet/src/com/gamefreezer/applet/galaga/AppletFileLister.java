package com.gamefreezer.applet.galaga;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;

import com.gamefreezer.galaga.AbstractFileLister;

public class AppletFileLister extends AbstractFileLister {

    @Override
    public String[] list() {
	return getFileNamesInDir(SpaceWar.DATA_DIR);
    }

    public static String[] getFileNamesInDir(FilenameFilter filter,
	    String aDirectory) {
	String[] fileNames = {};
	try {
	    URI uri = AppletFileLister.class.getResource(aDirectory).toURI();
	    fileNames = new File(uri).list(filter);
	} catch (Exception e) {
	    throw new AssertionError("\nCan't read from dir: " + aDirectory
		    + "\n" + e);
	}
	return fileNames;
    }

    public static String[] getFileNamesInDir(String aDirectory) {
	return getFileNamesInDir(null, aDirectory);
    }

}
