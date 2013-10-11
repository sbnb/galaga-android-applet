package com.gamefreezer.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Profiler {

    private class ProfilerItem {
	private int numCalls;
	private long totalTime;
	private Stack<Long> currentTimers;

	public ProfilerItem() {
	    numCalls = 0;
	    totalTime = 0;
	    currentTimers = new Stack<Long>();
	}

	public int count() {
	    return numCalls;
	}

	public long totalTime() {
	    return totalTime;
	}

	public void addTime(long time) {
	    numCalls++;
	    totalTime += time;
	}

	public void start() {
	    currentTimers.push(System.currentTimeMillis());
	}

	public void end() {
	    if (currentTimers.size() == 0) {
		throw new AssertionError("No currently active timers!");
	    }
	    long startTime = currentTimers.pop();
	    addTime(System.currentTimeMillis() - startTime);
	}
    }

    private static Map<String, ProfilerItem> items = new HashMap<String, ProfilerItem>();

    public static void start(String name) {
	ProfilerItem item = items.get(name);

	if (item == null) {
	    item = new Profiler().new ProfilerItem();
	    items.put(name, item);
	}

	item.start();
    }

    public static void end(String name) {
	ProfilerItem item = items.get(name);
	if (item == null) {
	    throw new AssertionError("No timer named " + name
		    + " running - called end() before start()?");
	}
	item.end();
    }

    public static String results() {
	String result = "";
	for (Map.Entry<String, ProfilerItem> entry : items.entrySet()) {
	    result += entry.getKey() + ", " + entry.getValue().count() + ", "
		    + entry.getValue().totalTime() + ", "
		    + entry.getValue().totalTime()
		    / Math.max(entry.getValue().count(), 1) + "\n";
	}
	return result;
    }
}
