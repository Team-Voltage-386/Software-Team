package org.usfirst.frc.team386.robot;

public class ObjectTracker {

	private int direction = 0;
	
	public int getDirection() {
		return direction;
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
