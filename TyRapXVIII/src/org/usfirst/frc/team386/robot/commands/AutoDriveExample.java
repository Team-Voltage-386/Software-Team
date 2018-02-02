package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * An example command group that drives forward towards the scale and turns
 * right.
 */
public class AutoDriveExample extends CommandGroup {

    public AutoDriveExample() {
	int inchesToScale = 288;
	addSequential(new DriveForward(inchesToScale));
	addSequential(new TurnRight(90));
	// addSequential(new DriveForward(12));
    }
}
