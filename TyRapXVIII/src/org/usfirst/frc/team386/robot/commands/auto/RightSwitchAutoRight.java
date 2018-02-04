package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.TurnLeft;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for right switch starting on the right side.
 */
public class RightSwitchAutoRight extends CommandGroup {

    public RightSwitchAutoRight() {
	// addSequential(new LowerIntake());
	addSequential(new DriveForward(140));
	addSequential(new TurnLeft(90));
	addSequential(new DriveForward(10));
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}
