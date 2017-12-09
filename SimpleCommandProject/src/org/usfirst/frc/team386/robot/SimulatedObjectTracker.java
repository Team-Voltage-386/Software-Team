package org.usfirst.frc.team386.robot;

public class SimulatedObjectTracker implements ObjectTracker {

    private int direction = 0;

    /**
     * Return true if the object tracking implementation is simulated.
     * 
     * @return True if tracking is simulated.
     */
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
