package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for right scale starting on the left side.
 */
public class LeftScaleAutoRight extends CommandGroup {

    public LeftScaleAutoRight() {
	// addSequential(new LowerIntake());
	addSequential(new DriveForward(208, 1));
	addSequential(new TurnRight(90));
	addSequential(new DriveForward(216, 1));
	addSequential(new TurnLeft(90));
	addSequential(new DriveForward(50, .7));
	addSequential(new TurnLeft(90));
	// TODO: check ultrasonic and adjust distance to scale by moving fwd or rev
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}
