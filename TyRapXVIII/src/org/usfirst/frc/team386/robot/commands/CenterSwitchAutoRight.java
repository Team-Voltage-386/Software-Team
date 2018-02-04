package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for right switch starting from center.
 */
public class CenterSwitchAutoRight extends CommandGroup {

    public CenterSwitchAutoRight() {
	addSequential(new LowerIntake());
	addSequential(new DriveForward(18));
	addSequential(new TurnRight(45));
	addSequential(new DriveForward(75));
	addSequential(new TurnLeft(40));
	addSequential(new DriveForward(26));
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }

}
