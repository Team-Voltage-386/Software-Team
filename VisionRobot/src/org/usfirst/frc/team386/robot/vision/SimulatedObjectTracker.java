package org.usfirst.frc.team386.robot.vision;

public class SimulatedObjectTracker implements ObjectTracker {

    private int direction = 0;

    @Override
    public void start() {
	// no op
    }

    @Override
    public void stop() {
	// no op
    }

    @Override
    public boolean isSimulated() {
	return true;
    }

    @Override
    public int getDirection() {
	return direction;
    }

    @Override
    public boolean isObjectPresent() {
	return true;
    }

    public void simulateTrackLeft() {
	this.direction = -1;
    }

    public void simulateTrackRight() {
	this.direction = 1;
    }

    public void simulateTrackCenter() {
	this.direction = 0;
    }

}
