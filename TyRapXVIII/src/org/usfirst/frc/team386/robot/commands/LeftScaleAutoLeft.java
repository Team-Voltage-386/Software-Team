package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftScaleAutoLeft extends CommandGroup {
    // auto mode for scale from left starting configuration
    //assumes scale is on left

    public LeftScaleAutoLeft() {
    //	addSequential(new LowerIntake());
    	addSequential(new DriveForward(292));
    	addSequential(new TurnRight(90)); //check ultrasonic to make sure our value is right
    //	addSequential(new ElevatorRaise());
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
