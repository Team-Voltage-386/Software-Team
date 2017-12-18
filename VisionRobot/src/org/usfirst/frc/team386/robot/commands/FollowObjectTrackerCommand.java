package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command that runs constantly to poll the camera and move the camera subsystem
 * servos.
 */
public class FollowObjectTrackerCommand extends Command {
    public static double STEP_SIZE = 0.5;

    public FollowObjectTrackerCommand() {
	requires(Robot.cameraControlSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	System.out.println("Initializing the camera subsystem");
	Robot.objectTracker.clearDirection();
	Robot.cameraControlSubsystem.center();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	int direction = Robot.objectTracker.getDirection();
	if (direction == 1) {
	    Robot.cameraControlSubsystem.rotateRight(STEP_SIZE);
	} else if (direction == -1) {
	    Robot.cameraControlSubsystem.rotateLeft(STEP_SIZE);
	} else if (direction == 0) {
	    // Don't move
	} else {
	    throw new RuntimeException("Received unsupported value from object tracker: " + direction);
	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
