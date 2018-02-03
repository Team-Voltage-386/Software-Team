package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterSwitchAutoRight extends CommandGroup{
	
public CenterSwitchAutoRight() {
    /*	System.out.println("initialized");
	addSequential(new LowerIntake());
	System.out.println("lower intake");
	addSequential(new ElevatorRaise());
	System.out.println("elevator raise");*/
	addSequential(new DriveForward(18));
	System.out.println("drive forward");
	addSequential(new TurnRight(45));
	System.out.println("turn right");
	addSequential(new DriveForward(75));
	addSequential(new TurnLeft(40));
	addSequential(new DriveForward(26));
	    }
/*protected void initialize() {
    	System.out.println("initialized");
    	addSequential(new LowerIntake());
    	System.out.println("lower intake");
	addSequential(new ElevatorRaise());
	addSequential(new DriveForward(18));
	addSequential(new TurnRight(45));
	addSequential(new DriveForward(100));
	addSequential(new TurnLeft(45));
}*/

}
