package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.DriveForward;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AutoLine extends InstantCommand {

    public AutoLine() {
	super();
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.chooserPosition.getSelected() == "Left") {
	    new DriveForward(120).start();
	} else if (Robot.chooserPosition.getSelected() == "Center") {
	    // need to make
	} else if (Robot.chooserPosition.getSelected() == "Right") {
	    new DriveForward(120).start();
	}
    }

}
