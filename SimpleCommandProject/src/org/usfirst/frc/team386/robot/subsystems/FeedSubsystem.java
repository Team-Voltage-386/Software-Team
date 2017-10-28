package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FeedSubsystem extends Subsystem {

    private Spark trigger = new Spark(RobotMap.triggerMotor);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
    }

    public void turnOnTriggerWheel(double speed) {
	trigger.setSpeed(speed);
    }

    public void turnOffTriggerWheel() {
	trigger.setSpeed(0);
    }
}
