package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for left scale starting on the right side.
 */
public class RightScaleAutoLeft extends CommandGroup {

    public RightScaleAutoLeft() {
	// addSequential(new LowerIntake());
	addSequential(new DriveForward(208));
	addSequential(new TurnLeft(90));
	addSequential(new DriveForward(216));
	addSequential(new TurnRight(90));
	addSequential(new DriveForward(50));
	addSequential(new TurnRight(90));
	// TODO: check ultrasonic and adjust distance to scale by moving fwd or rev
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}
