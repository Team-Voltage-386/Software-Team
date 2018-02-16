package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ShiftArms extends InstantCommand {

    public ShiftArms() {
	super();
	requires(Robot.armsSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.armsSubsystem.shiftArms();
    }

}
