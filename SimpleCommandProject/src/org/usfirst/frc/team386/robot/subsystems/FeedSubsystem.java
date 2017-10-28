package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Feeder wheel for delivering balls to the shooter wheel.
 */
public class FeedSubsystem extends Subsystem {

    private Spark trigger = new Spark(RobotMap.triggerMotor);

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Turn on the feeder wheel. Calling this method will always result in the wheel
     * moving in the correct direction.
     * 
     * @param speed
     *            Range from 0.0 to 1.0
     */
    public void turnOn(double speed) {
	turnOn(speed, false);
    }

    /**
     * Turn on the feeder wheel.
     * 
     * @param speed
     *            Range from 0.0 to 1.0
     * @param reverse
     *            Set to true to reverse the trigger wheel.
     */
    public void turnOn(double speed, boolean reverse) {
	if (reverse) {
	    trigger.setSpeed(speed);
	} else {
	    trigger.setSpeed(-speed);
	}
    }

    /**
     * Turn off the wheel by setting the speed to 0.
     */
    public void turnOff() {
	trigger.setSpeed(0);
    }
}
