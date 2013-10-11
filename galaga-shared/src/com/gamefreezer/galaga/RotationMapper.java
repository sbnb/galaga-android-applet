package com.gamefreezer.galaga;

public class RotationMapper {

    private float interval;

    public RotationMapper(float interval) {
	this.interval = interval;
    }

    public int nearestRotation(float rotation) {
	float remainder = rotation % interval;
	int nearest;
	if (remainder >= interval / 2) {
	    nearest = (int) (rotation - remainder + interval);
	} else {
	    nearest = (int) (rotation - remainder);
	}
	return nearest % 360;
    }

}
