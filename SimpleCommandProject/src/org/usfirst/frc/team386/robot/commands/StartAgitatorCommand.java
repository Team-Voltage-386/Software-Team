package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class StartAgitatorCommand extends InstantCommand {

    public StartAgitatorCommand() {
	super();
	requires(Robot.agitatorSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.agitatorSubsystem.start();
    }

}