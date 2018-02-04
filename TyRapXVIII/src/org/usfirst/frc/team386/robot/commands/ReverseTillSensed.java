package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ReverseTillSensed extends InstantCommand {

    public ReverseTillSensed() {
	super();
	requires(Robot.driveSubsystem);
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	double dist = 12;
	Robot.driveSubsystem.reverseTillSensedDistance(dist);
    }

}
