package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Drive forward to a cube.
 */
public class DriveToCube extends InstantCommand {

    public DriveToCube() {
	super();
	requires(Robot.driveSubsystem);
    }

    protected void initialize() {
	if (RobotState.isOperatorControl()) {
	    Robot.driveSubsystem.driveToCubeTeleop();
	} else {
	    Robot.driveSubsystem.driveToCubeAuto();
	}
    }
}
