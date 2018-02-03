package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.OI;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class CenterSwitchAuto extends InstantCommand {
    //gamedata = DriverStation.getInstance().getGameSpecificMessage();
    // (LLL), (LRL), (RRR), (RLR)
    //The logic is working but it wont run the actual motors

    public CenterSwitchAuto() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	if(OI.gamedata.equals("LLL") || OI.gamedata.equals("LRL")) {
	    new CenterSwitchAutoLeft().start();
	}
	else {
	    new CenterSwitchAutoRight().start();
	}
    }
}
