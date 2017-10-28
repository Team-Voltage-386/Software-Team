package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Stop the trigger wheel.
 */
public class StopTriggerCommand extends InstantCommand {

    public StopTriggerCommand() {
	super();
	requires(Robot.feedSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.feedSubsystem.turnOff();
    }

}
