package com.gamefreezer.galaga;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.SortedMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConfigTest {

	@Before
	public void setUp() throws Exception {
		//NOP
	}

	@After
	public void tearDown() throws Exception {
		//NOP
	}

	@Test
	public void parsingASortedMap() {
		String line = "100:1 90:2 50:3 10:4";
		SortedMap<Integer, Integer> smap = MyProperties.parseSortedMap(line);
		assertThat("size is 100", smap.size(), is(100));
		assertThat("100->1", smap.get(100), is(1));
		assertThat("91->1", smap.get(91), is(1));
		assertThat("90->2", smap.get(90), is(2));
		assertThat("11->3", smap.get(11), is(3));
		assertThat("10->4", smap.get(10), is(4));
		assertThat("1->4", smap.get(1), is(4));
	}
}
