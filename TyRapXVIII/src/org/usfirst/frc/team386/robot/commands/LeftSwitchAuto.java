package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.OI;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class LeftSwitchAuto extends InstantCommand {

    public LeftSwitchAuto() {
	super();
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	if (OI.gamedata.equals("LLL") || OI.gamedata.equals("LRL")) {
	    new LeftSwitchAutoLeft().start();
	} else {
	    new LeftSwitchAutoRight().start();
	}

    }
}
