package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnLeftWithoutPid extends Command {
    int angle;

    public TurnLeftWithoutPid(int angle) {
	requires(Robot.driveSubsystem);
	this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot.driveSubsystem.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.driveSubsystem.turnWithoutPid(DriveSubsystem.LEFT);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return Math.abs(Robot.driveSubsystem.gyro.getAngle()) > angle;
    }

    // Called once after isFinished returns true
    protected void end() {
	Robot.driveSubsystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
