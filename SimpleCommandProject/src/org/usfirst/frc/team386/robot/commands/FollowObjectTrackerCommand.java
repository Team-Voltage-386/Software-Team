package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FollowObjectTrackerCommand extends Command {

    public FollowObjectTrackerCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.cameracontrol);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int direction = Robot.objectTracker.getDirection();
    	if (direction == 1) {
    		Robot.cameracontrol.panright();
    	} else if (direction == -1) {
    		Robot.cameracontrol.panleft();
    	} else if (direction == 0) {
    		Robot.cameracontrol.center();
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
