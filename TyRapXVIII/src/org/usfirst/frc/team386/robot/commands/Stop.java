package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Stop (aka martian rock).
 */
public class Stop extends InstantCommand {

    /**
     * Calls DriveSubsystem's stop method
     */
    public Stop() {
	super();
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.stop();
    }

}
