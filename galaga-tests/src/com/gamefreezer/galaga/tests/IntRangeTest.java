package com.gamefreezer.galaga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.gamefreezer.galaga.IntRange;

public class IntRangeTest {

    IntRange range;
    int min = 0;
    int max = 10;

    @Before
    public void setUp() {
	range = new IntRange(min, max);
    }

    @Test
    public void rangeIsSetCorrectly() {
	assertThat(range.min, is(min));
	assertThat(range.max, is(max));
    }

    @Test
    public void randomWorksAsExpected() {
	int length = max - min + 1;
	int[] samples = new int[length];
	for (int i = 0; i < samples.length; i++) {
	    samples[i] = 0;
	}

	int count = 100000;
	for (int i = 0; i < count; i++) {
	    int sample = range.random();
	    assertTrue(sample <= max);
	    assertTrue(sample >= min);
	    samples[sample]++;
	}

	int expected = count / length;
	float epsilon = 0.1f;
	for (int i = 0; i < samples.length; i++) {
	    float error = (float) Math.abs(samples[i] - expected) / expected;
	    assertTrue(error <= epsilon);
	}

    }
}
