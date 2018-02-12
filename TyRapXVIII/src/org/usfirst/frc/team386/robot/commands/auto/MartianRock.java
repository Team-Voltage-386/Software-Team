package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.Stop;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
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
