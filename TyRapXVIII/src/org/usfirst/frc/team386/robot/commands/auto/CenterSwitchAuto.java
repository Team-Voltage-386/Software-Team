package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.OI;
import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start center and win the switch.
 * 
 * This command will decide whether the robot should go to the left or right
 * switch depending on the game data.
 */
public class CenterSwitchAuto extends InstantCommand {

    public CenterSwitchAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (OI.gamedata.equals(Robot.LLL) || OI.gamedata.equals(Robot.LRL)) {
	    new CenterSwitchAutoLeft().start();
	} else {
	    new CenterSwitchAutoRight().start();
	}
    }
}
