package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * An example command group that drives forward towards the switch, turns right,
 * and then drives forward another 12 inches.
 */
public class AutoDriveExample extends CommandGroup {

    public AutoDriveExample() {
	int inchesToSwitch = 288;
	addSequential(new DriveForward(inchesToSwitch));
	addSequential(new TurnRight(90));
	// addSequential(new DriveForward(12));
    }
}
