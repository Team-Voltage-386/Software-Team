package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetElevator extends InstantCommand {
    int ticks;

    public SetElevator(int ticksIn) {
	super();
	requires(Robot.elevatorSubsystem);
	ticks = ticksIn;
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.elevatorSubsystem.setHeight(ticks);
    }

}
