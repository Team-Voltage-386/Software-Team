package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive command that works with two joysticks. This is meant to be run as the
 * default command of the drive subsystem if the driver is driving in tank mode.
 */
public class TankDrive extends Command {

    public TankDrive() {
	requires(Robot.driveSubsystem);
    }

    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.driveSubsystem.driveTank(Robot.oi.leftJoy.getY(), Robot.oi.rightJoy.getY());
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
