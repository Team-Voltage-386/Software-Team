package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetElevatorToVault extends InstantCommand {

    public SetElevatorToVault() {
	super();
	requires(Robot.elevatorSubsystem);
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.elevatorSubsystem.setHeight(200/* set acutal ticks later */);
    }

}
