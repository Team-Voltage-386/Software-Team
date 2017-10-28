package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class StopTriggerCommand extends InstantCommand {

    public StopTriggerCommand() {
	super();
	// Use requires() here to declare subsystem dependencies
	requires(Robot.feedSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.feedSubsystem.turnOffTriggerWheel();
    }

}
