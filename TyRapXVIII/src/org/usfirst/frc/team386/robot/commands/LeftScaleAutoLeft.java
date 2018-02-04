package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Auto mode for left scale starting on the left side.
 */
public class LeftScaleAutoLeft extends CommandGroup {

    public LeftScaleAutoLeft() {
	// addSequential(new LowerIntake());
	addSequential(new DriveForward(292));
	addSequential(new TurnRight(90));
	// TODO: check ultrasonic and adjust distance to scale by moving fwd or rev
	addSequential(new ElevatorRaise());
	addSequential(new CubeRelease());
    }
}
