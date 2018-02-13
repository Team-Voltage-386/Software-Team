package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.Stop;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Autonomous mode for sitting still.
 * 
 * This should only be used if our drive system is completely broken and yet we
 * still need to be on the field for autonomous period.
 */
public class MartianRock extends InstantCommand {

    public MartianRock() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	new Stop().start();
    }

}
