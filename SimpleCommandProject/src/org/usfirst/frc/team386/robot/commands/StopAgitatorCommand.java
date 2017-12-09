package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Stop the ball bucket agitator.
 */
public class StopAgitatorCommand extends InstantCommand {

	public StopAgitatorCommand() {
		super();
		requires(Robot.agitatorSubsystem);
	}

	// Called once when the command executes
	protected void initialize() {
		Robot.agitatorSubsystem.stop();
	}

}
