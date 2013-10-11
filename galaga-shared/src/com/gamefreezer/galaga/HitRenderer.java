package com.gamefreezer.galaga;

public class HitRenderer extends AllocGuard {

    Location[] relToAlien;
    Animation[] hits;

    public HitRenderer(int size) {
	super();
	relToAlien = new Location[size];
	hits = new Animation[size];

	for (int idx = 0; idx < relToAlien.length; idx++) {
	    relToAlien[idx] = new Location();
	}
    }

    Location tmp = new Location();

    public void draw(AbstractGraphics graphics, Location alienLoc) {
	for (int idx = 0; idx < hits.length; idx++) {
	    if (hits[idx] != null) {
		tmp.moveTo(alienLoc);
		tmp.moveBy(relToAlien[idx]);
		hits[idx].draw(graphics, tmp);
		if (hits[idx].isFinished()) {
		    hits[idx].returnToPool();
		    hits[idx] = null;
		}
	    }
	}

    }

    public void registerHit(Animation hitAnim, Location alienLoc,
	    Location bulletLoc) {
	if (hitAnim == null) {
	    return;
	}

	for (int idx = 0; idx < hits.length; idx++) {
	    if (hits[idx] == null) {
		hits[idx] = hitAnim;
		relToAlien[idx].moveTo(bulletLoc);
		relToAlien[idx].minus(alienLoc);
		return;
	    }
	}

	// no slots, ignore this hit animation
	hitAnim.returnToPool();
    }

    public void clear() {
	for (int idx = 0; idx < hits.length; idx++) {
	    if (hits[idx] != null) {
		hits[idx].returnToPool();
	    }
	}
    }

    public int currentRenders() {
	int count = 0;
	for (int idx = 0; idx < hits.length; idx++) {
	    if (hits[idx] != null) {
		count++;
	    }
	}
	return count;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(getClass().getSimpleName() + ":\n");
	for (int idx = 0; idx < hits.length; idx++) {
	    sb.append("\t" + hits[idx] + "\n");
	}
	return sb.toString();
    }
}
