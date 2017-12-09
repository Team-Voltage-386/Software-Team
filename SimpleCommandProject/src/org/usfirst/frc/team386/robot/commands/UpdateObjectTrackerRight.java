package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.vision.SimulatedObjectTracker;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class UpdateObjectTrackerRight extends InstantCommand {

    public UpdateObjectTrackerRight() {
	super();
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.objectTracker.isSimulated()) {
	    ((SimulatedObjectTracker) Robot.objectTracker).simulateTrackRight();
	}
    }

}
