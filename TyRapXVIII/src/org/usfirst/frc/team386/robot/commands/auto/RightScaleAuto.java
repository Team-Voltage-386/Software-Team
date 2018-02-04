package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.OI;
import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start right and win the scale.
 * 
 * This command will decide whether the robot should go to the left or right
 * scale depending on the game data.
 */
public class RightScaleAuto extends InstantCommand {

    public RightScaleAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (OI.gamedata.equals(Robot.RRR) || OI.gamedata.equals(Robot.LRL)) {
	    new RightScaleAutoRight().start();
	} else {
	    new RightScaleAutoLeft().start();
	}
    }

}
