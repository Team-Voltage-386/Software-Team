package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterSwitchAutoLeft extends CommandGroup {
    // auto mode for switch starting from center
	//(lll), (rrr), (lrl), (rlr)
	
    public CenterSwitchAutoLeft() {
    		//addSequential(new LowerIntake()); 
    		//addSequential(new ElevatorRaise());
    		addSequential(new DriveForward(18));
    	//	System.out.println("drive forward for left");
    		addSequential(new TurnLeft(45));
    		//System.out.println("turn left");
    		addSequential(new DriveForward(75));
    		addSequential(new TurnRight(45));
    		addSequential(new DriveForward(25));
    		//lowers arm mechanism, drives forward a bit so we can turn, the turns, drives forward to auto line, then squares up
    	
    	
//    		addSequential(new LowerIntake());
//    		addSequential(new ElevatorRaise());
//    		addSequential(new DriveForward(18));
//    		addSequential(new TurnRight(45));
//    		addSequential(new DriveForward(100));
//    		addSequential(new TurnLeft(45));
    	
    }
    		
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
