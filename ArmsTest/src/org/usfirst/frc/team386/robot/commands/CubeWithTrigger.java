package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CubeWithTrigger extends Command {

    public CubeWithTrigger() {
	requires(Robot.cubeSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.cubeSubsystem.runCombined(
		Robot.oi.manipulator.getRawAxis(RobotMap.manipLeftTriggerAxis)
			- Robot.oi.manipulator.getRawAxis(RobotMap.manipRightTriggerAxis),
		Robot.oi.manipulator.getRawAxis(5), Robot.oi.manipulator.getRawAxis(1));
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
