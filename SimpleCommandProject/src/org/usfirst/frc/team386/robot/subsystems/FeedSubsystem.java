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
     * Turn on the wheel.
     * 
     * @param speed
     *            Range from -1.0 to 1.0
     */
    public void turnOnTriggerWheel(double speed) {
	trigger.setSpeed(speed);
    }

    /**
     * Turn off the wheel by setting the speed to 0.
     */
    public void turnOffTriggerWheel() {
	trigger.setSpeed(0);
    }
}
