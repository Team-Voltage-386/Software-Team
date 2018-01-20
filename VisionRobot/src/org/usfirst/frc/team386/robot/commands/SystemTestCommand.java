package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Run through system tests.
 */
public class SystemTestCommand extends CommandGroup {

    public SystemTestCommand() {
	addSequential(new AdjustCameraCommand(0));
	addSequential(new WaitCommand(2));
	addSequential(new AdjustCameraCommand(180.0));
	addSequential(new WaitCommand(2));
	addSequential(new AdjustCameraCommand(90.0));
    }
}
