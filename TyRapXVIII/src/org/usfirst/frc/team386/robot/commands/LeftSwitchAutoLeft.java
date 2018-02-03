package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSwitchAutoLeft extends CommandGroup {

    public LeftSwitchAutoLeft() {
	//addSequential(new LowerIntake());
	addSequential(new DriveForward(140));
	// insert decision
	addSequential(new TurnRight(90));
	addSequential(new DriveForward(12, 0.3));
	//addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}
