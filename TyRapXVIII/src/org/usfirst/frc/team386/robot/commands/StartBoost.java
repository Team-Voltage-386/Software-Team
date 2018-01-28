package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class StartBoost extends InstantCommand {

    public StartBoost() {
	super();
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.startBoost();
    }

}
