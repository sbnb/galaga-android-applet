package com.gamefreezer.galaga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FormationsFactory {

    // return a list<Formation> scanned from a directory
    // containing files formatted: formation??properties.dat
    public static List<Formation> createFormations(SpriteCache spriteCache,
	    Constants cfg) {
	List<Formation> formations = new ArrayList<Formation>();
	List<String> names = Arrays.asList(Tools.listFiles());
	Collections.sort(names);

	MyProperties alienProps = new MyProperties(Tools
		.openFile(cfg.ALIEN_DETAILS_FILE));

	for (String name : names) {
	    if (fileNameOk(name)) {
		MyProperties levelProps = new MyProperties(Tools.openFile(name));
		formations.add(new Formation(spriteCache, cfg.SCREEN,
			cfg.MAX_FORMATION, cfg.AL_SPACING, cfg.AL_SPEEDS,
			levelProps, alienProps));
	    }
	}
	return formations;
    }

    private static boolean fileNameOk(String name) {
	return name.startsWith("formation") && name.indexOf("properties") > -1
		&& name.endsWith("dat");
    }

}
