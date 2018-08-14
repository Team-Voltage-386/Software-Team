package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CubeWithUltrasonics extends Command {

    public CubeWithUltrasonics() {
	requires(Robot.cubeSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.cubeSubsystem.runWithUltrasonics(.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return !Robot.oi.manipulator.getRawButton(RobotMap.autoCubeIntakeButton) && RobotState.isEnabled();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
