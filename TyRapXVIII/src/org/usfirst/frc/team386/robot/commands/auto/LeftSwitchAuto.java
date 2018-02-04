package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start left and win the switch.
 * 
 * This command will decide whether the robot should go to the left or right
 * switch depending on the game data.
 */
public class LeftSwitchAuto extends InstantCommand {

    public LeftSwitchAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.gamedata.isSwitchLeft()) {
	    new LeftSwitchAutoLeft().start();
	} else {
	    new LeftSwitchAutoRight().start();
	}

    }
}
