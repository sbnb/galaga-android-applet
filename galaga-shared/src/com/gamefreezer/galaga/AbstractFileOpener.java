package com.gamefreezer.galaga;

import java.io.InputStream;

public abstract class AbstractFileOpener {
    public abstract InputStream open(String name);
}
