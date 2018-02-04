package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.TurnLeft;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for left switch starting on the right side.
 */
public class RightSwitchAutoLeft extends CommandGroup {

    public RightSwitchAutoLeft() {
//	 addSequential(new LowerIntake());
	addSequential(new DriveForward(215));
	addSequential(new TurnLeft(90));
	addSequential(new DriveForward(183));
	addSequential(new TurnLeft(90));
	addSequential(new DriveForward(42));
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}
