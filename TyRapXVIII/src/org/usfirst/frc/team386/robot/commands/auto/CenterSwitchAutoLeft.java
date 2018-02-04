package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.LowerIntake;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for left switch starting from center.
 */
public class CenterSwitchAutoLeft extends CommandGroup {

    public CenterSwitchAutoLeft() {
	addSequential(new LowerIntake());
	addSequential(new DriveForward(18));
	addSequential(new TurnLeft(45));
	addSequential(new DriveForward(75));
	addSequential(new TurnRight(45));
	addSequential(new DriveForward(25));
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}
