package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftScaleAutoRight extends CommandGroup {

    public LeftScaleAutoRight() {
	addSequential(new DriveForward(208, 1));
	addSequential(new TurnRight(90));
	addSequential(new DriveForward(216, 1));
	addSequential(new TurnLeft(90));
	addSequential(new DriveForward(50, .7));
	addSequential(new TurnLeft(90));
	addSequential(new ElevatorRaise());

	addSequential(new CubeRelease());
	// Add Commands here:
	// e.g. addSequential(new Command1());
	// addSequential(new Command2());
	// these will run in order.

	// To run multiple commands at the same time,
	// use addParallel()
	// e.g. addParallel(new Command1());
	// addSequential(new Command2());
	// Command1 and Command2 will run in parallel.

	// A command group will require all of the subsystems that each member
	// would require.
	// e.g. if Command1 requires chassis, and Command2 requires arm,
	// a CommandGroup containing them would require both the chassis and the
	// arm.
    }
}
