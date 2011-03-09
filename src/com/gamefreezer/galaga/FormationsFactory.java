package com.gamefreezer.galaga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FormationsFactory {

    // return a list<Formation> scanned from a directory
    // containing files formatted: formation??properties.dat
    public static List<Formation> createFormations(SpriteCache spriteStore,
	    Constants cfg) {
	List<Formation> formations = new ArrayList<Formation>();
	List<String> names = Arrays.asList(Game.listFiles());
	Collections.sort(names);

	for (String name : names) {
	    if (fileNameOk(name)) {
		formations.add(new Formation(spriteStore, cfg, name));
	    }
	}
	return formations;
    }

    private static boolean fileNameOk(String name) {
	return name.startsWith("formation") && name.indexOf("properties") > -1
		&& name.endsWith("dat");
    }

}
