package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.RotationMapper;
import com.gamefreezer.galaga.RotationSprites;

public class RotationSpritesTest {

    private RotationSprites rotationSprites;
    private float interval = 10f;
    private RotationMapper rotationMapper;

    @Before
    public void setUp() {
	rotationMapper = new RotationMapper(interval);
	rotationSprites = new RotationSprites(null, "s", ".png", rotationMapper);
    }

    @Test
    public void mappingRotationToSpriteNames() {
	assertThat(rotationSprites.mapRotationToName(0), is("s000.png"));
	assertThat(rotationSprites.mapRotationToName(1), is("s000.png"));
	assertThat(rotationSprites.mapRotationToName(5), is("s010.png"));
	assertThat(rotationSprites.mapRotationToName(46), is("s050.png"));
	assertThat(rotationSprites.mapRotationToName(354.9f), is("s350.png"));
	assertThat(rotationSprites.mapRotationToName(355), is("s000.png"));
    }

}
