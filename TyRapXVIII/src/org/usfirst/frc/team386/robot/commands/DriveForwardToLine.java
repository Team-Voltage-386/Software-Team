package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * A command to drive forward until a line sensor detects a line.
 */
public class DriveForwardToLine extends InstantCommand {

    public DriveForwardToLine() {
	super();
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.driveForwardToLine();
    }

}
