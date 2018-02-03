package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.OI;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class RightSwitchAuto extends InstantCommand {

    public RightSwitchAuto() {
	super();
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	if (OI.gamedata.equals("LLL") || OI.gamedata.equals("LRL")) {
	    new RightSwitchAutoLeft().start();
	} else {
	    new RightSwitchAutoRight().start();
	}

    }
}