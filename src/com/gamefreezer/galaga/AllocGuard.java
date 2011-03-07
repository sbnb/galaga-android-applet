package com.gamefreezer.galaga;

/*
 * Allocation guard. All game objects should inherit from this class.
 * When guard is on any new allocation will be noted and output.
 * Inheriting game objects must call super() in all their constructors.
 */

public class AllocGuard {

    public static boolean guardOn = false;
    public static int objectCount = 0;

    public AllocGuard() {
	if (guardOn) {
	    System.out.println("Allocation warning: "
		    + this.getClass().getName() + " [objects: " + objectCount
		    + "]");
	}
	objectCount++;
    }
}
