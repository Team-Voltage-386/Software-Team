package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Break the chain in preparation for climbing.
 */
public class BreakChain extends InstantCommand {

    public BreakChain() {
	super();
	requires(Robot.elevatorSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	// TODO: only invoke if we are within 30 seconds of the end of the match
	Robot.elevatorSubsystem.breakChain();
    }

}
