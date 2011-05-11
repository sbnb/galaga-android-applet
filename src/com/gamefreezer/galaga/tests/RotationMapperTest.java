package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.RotationMapper;

public class RotationMapperTest {

    private float interval;
    private RotationMapper rotationMapper;

    @Before
    public void setUp() {
	interval = 10;
	rotationMapper = new RotationMapper(interval);
    }

    @Test
    public void nearestRotation() {
	assertThat(rotationMapper.nearestRotation(0), is(0));
	assertThat(rotationMapper.nearestRotation(1), is(0));
	assertThat(rotationMapper.nearestRotation(4.9f), is(0));
	assertThat(rotationMapper.nearestRotation(5), is(10));
	assertThat(rotationMapper.nearestRotation(36), is(40));
	assertThat(rotationMapper.nearestRotation(22), is(20));
	assertThat(rotationMapper.nearestRotation(304), is(300));
	assertThat(rotationMapper.nearestRotation(354), is(350));
	assertThat(rotationMapper.nearestRotation(355.1f), is(0));
    }

    @Test
    public void nearestRotationWithFloatInterval() {
	interval = 12.5f;
	rotationMapper = new RotationMapper(interval);
	assertThat(rotationMapper.nearestRotation(0), is(0));
	assertThat(rotationMapper.nearestRotation(7), is(12));
	assertThat(rotationMapper.nearestRotation(13.5f), is(12));
	assertThat(rotationMapper.nearestRotation(18.75f), is(25));
    }

}
