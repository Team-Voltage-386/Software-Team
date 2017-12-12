package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ShiftLoGearCommand extends InstantCommand {

    public ShiftLoGearCommand() {
	super();
	// Use requires() here to declare subsystem dependencies
	requires(Robot.driveGearSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveGearSubsystem.lo();
    }

}
