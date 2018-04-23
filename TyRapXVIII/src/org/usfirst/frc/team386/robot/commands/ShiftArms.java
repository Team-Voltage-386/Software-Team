package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Shift the arms to their other position. For example, if the arms are
 * currently lowered, executing this command will raise them.
 */
public class ShiftArms extends InstantCommand {

    /**
     * Calls the shift arms method
     */
    public ShiftArms() {
	super();
	requires(Robot.armsSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.armsSubsystem.shiftArms();
    }

}
