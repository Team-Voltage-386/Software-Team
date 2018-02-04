package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for left switch starting on the left side.
 */
public class LeftSwitchAutoLeft extends CommandGroup {

    public LeftSwitchAutoLeft() {
	// addSequential(new LowerIntake());
	addSequential(new DriveForward(140));
	addSequential(new TurnRight(90));
	addSequential(new DriveForward(18, 0.3));
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}
