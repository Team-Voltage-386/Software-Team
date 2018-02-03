package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSwitchAutoRight extends CommandGroup {
    // auto for right switch starting from left side

    public LeftSwitchAutoRight() {
	// addSequential(new LowerIntake());
	// addSequential(new ElevatorRaise());
	addSequential(new DriveForward(215));
	addSequential(new TurnRight(90));
	addSequential(new DriveForward(195));
	addSequential(new TurnRight(90));
	addSequential(new DriveForward(10));
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
