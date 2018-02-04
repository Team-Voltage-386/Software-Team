package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.OI;
import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start right and win the switch.
 * 
 * This command will decide whether the robot should go to the left or right
 * switch depending on the game data.
 */
public class RightSwitchAuto extends InstantCommand {

    public RightSwitchAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (OI.gamedata.equals(Robot.LLL) || OI.gamedata.equals(Robot.LRL)) {
	    new RightSwitchAutoLeft().start();
	} else {
	    new RightSwitchAutoRight().start();
	}

    }
}