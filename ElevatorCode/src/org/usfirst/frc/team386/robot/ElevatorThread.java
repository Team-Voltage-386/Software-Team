package org.usfirst.frc.team386.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

public class ElevatorThread extends Thread {

    public Spark motor1;
    public Spark motor2;
    public DigitalInput limitSwitchBottom;

    public ElevatorThread() {
	motor1 = Robot.elevatorOne;
	motor2 = Robot.elevatorTwo;
	limitSwitchBottom = Robot.limit;
    }

    public void run() {
	while (!limitSwitchBottom.get()) { // test limit switch values when pressed
	    if (Robot.manip.getRawAxis(1) < 0) {
		motor1.set(Robot.manip.getRawAxis(1)); // might need to invert
		motor2.set(Robot.manip.getRawAxis(1));
	    } else if (Robot.manip.getRawAxis(1) == 0) {
		motor1.set(0);
		motor2.set(0);
	    } else if (Robot.manip.getRawAxis(1) > 0)

		motor1.set(-1 * Robot.manip.getRawAxis(1)); // might need to invert
	    motor2.set(-1 * Robot.manip.getRawAxis(1));
	}
	motor1.set(0);
	motor2.set(0);
    }

}
