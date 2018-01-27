package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AutoMode1 extends InstantCommand {

    public AutoMode1() {
	super();
	// Use requires() here to declare subsystem dependencies
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.moveForward(360);
    }

}
