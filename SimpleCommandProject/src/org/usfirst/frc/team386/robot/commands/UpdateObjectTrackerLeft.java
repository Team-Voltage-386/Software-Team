package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class UpdateObjectTrackerLeft extends InstantCommand {

    public UpdateObjectTrackerLeft() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() { 
    	Robot.objectTracker.simulateTrackLeft();
    }

}
