package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DriveReverseToWall extends InstantCommand {

    public DriveReverseToWall() {
	super();
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	// double distanceFromWall = 12;
	// Robot.driveSubsystem.reverseTillSensedDistance(distanceFromWall);
    }

}
