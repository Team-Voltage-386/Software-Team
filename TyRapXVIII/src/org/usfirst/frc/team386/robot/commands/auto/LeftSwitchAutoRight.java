package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for left switch starting on the right side.
 */
public class LeftSwitchAutoRight extends CommandGroup {

    public LeftSwitchAutoRight() {
	// addSequential(new LowerIntake());
	addSequential(new DriveForward(215));
	addSequential(new TurnRight(90));
	addSequential(new DriveForward(183));
	addSequential(new TurnRight(90));
	addSequential(new DriveForward(42));
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}
