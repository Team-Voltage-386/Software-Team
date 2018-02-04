package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DriveReverseToWall extends InstantCommand {

    private double distanceFromWall;

    public DriveReverseToWall(double distanceFromWall) {
	super();
	requires(Robot.driveSubsystem);
	this.distanceFromWall = distanceFromWall;
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.reverseToWall(distanceFromWall);
    }

}
