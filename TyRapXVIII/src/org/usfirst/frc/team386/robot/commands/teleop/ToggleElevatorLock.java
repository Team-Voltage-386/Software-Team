package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Lock the elevator in place.
 */
public class ToggleElevatorLock extends InstantCommand {

    public ToggleElevatorLock() {
	super();
	requires(Robot.elevatorSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.oi.manipulator.getRawButton(RobotMap.prepForClimbButton2))
	    Robot.elevatorSubsystem.toggleElevatorLock();
    }

}
