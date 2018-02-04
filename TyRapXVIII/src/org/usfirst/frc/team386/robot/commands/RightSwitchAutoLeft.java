package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for left switch starting on the right side.
 */
public class RightSwitchAutoLeft extends CommandGroup {

    public RightSwitchAutoLeft() {
	// addSequential(new LowerIntake());
	addSequential(new DriveForward(215));
	addSequential(new TurnLeft(90));
	addSequential(new DriveForward(183));
	addSequential(new TurnLeft(90));
	addSequential(new DriveForward(42));
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}
