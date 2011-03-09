package com.gamefreezer.galaga;

public class Controller extends AllocGuard {

    private Screen screen;
    private int staySolo;

    public Controller(Screen screen, int staySolo) {
	super();
	this.screen = screen;
	this.staySolo = staySolo;
    }

    public void adjust(int delta, Alien alien, Location target) {
	if (atLeftEdge(alien) || atRightEdge(alien)) {
	    alien.flip(); // change direction
	}
	if (readyForTarget(alien, target)) {
	    alien.setTarget(target);
	} else if (alien.targetting()) {
	    alien.setTarget(target);
	    alien.move(delta);
	    int epsilon = 10;
	    if (nearEnoughToTarget(alien, target, epsilon)) {
		alien.moveTo(target);
		alien.setTarget(-1, -1);
		alien.setSolo(false);
		alien.setDiveComplete(false);
	    }
	} else {
	    alien.move(delta);
	}
    }

    private boolean nearEnoughToTarget(Alien alien, Location target, int epsilon) {
	return Math.abs(target.getXAsFloat() - alien.getXAsFloat()) < epsilon
		&& Math.abs(target.getYAsFloat() - alien.getYAsFloat()) < epsilon;
    }

    private boolean readyForTarget(Alien alien, Location target) {
	return alien.diveComplete && !alien.targetting()
		&& target.getYAsFloat() > staySolo;
    }

    private boolean atRightEdge(Alien alien) {
	return screen.right() == alien.rightEdge();
    }

    private boolean atLeftEdge(Alien alien) {
	return screen.left() == alien.getX();
    }
}
