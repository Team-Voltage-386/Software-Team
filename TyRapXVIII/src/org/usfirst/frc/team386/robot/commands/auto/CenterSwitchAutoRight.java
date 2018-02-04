package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.LowerIntake;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;

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
