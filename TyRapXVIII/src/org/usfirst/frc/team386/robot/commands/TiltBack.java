package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Command to correct tilt backwards.
 * 
 * This command is started automatically by the TiltDetection command.
 */
public class TiltBack extends InstantCommand {

    public TiltBack() {
	super();
	requires(Robot.tiltSubsystem);
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.tiltSubsystem.tiltBackwards();
    }

}
