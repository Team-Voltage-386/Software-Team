package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.DriveForward;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Autonomous mode for crossing the auto line.
 */
public class AutoLine extends InstantCommand {

    public AutoLine() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	switch (Robot.chooserPosition.getSelected()) {
	case Robot.LEFT:
	case Robot.RIGHT:
	    new DriveForward(120).start();
	    break;
	case Robot.CENTER:
	    new SwitchAuto().start();
	}
    }

}
