package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.GameData;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start left and win the scale.
 * 
 * This command will decide whether the robot should go to the left or right
 * scale depending on the game data.
 */
public class LeftScaleAuto extends InstantCommand {

    public LeftScaleAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (GameData.isScaleLeft()) {
	    new LeftScaleAutoLeft().start();
	} else {
	    new LeftScaleAutoRight().start();
	}
    }
}
