package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.TurnLeft;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for right scale starting on the right side.
 */
public class RightScaleAutoRight extends CommandGroup {

    public RightScaleAutoRight() {
	// addSequential(new LowerIntake());
	addSequential(new DriveForward(292));
	addSequential(new TurnLeft(90));
	// TODO: check ultrasonic and adjust distance to scale by moving fwd or rev
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}