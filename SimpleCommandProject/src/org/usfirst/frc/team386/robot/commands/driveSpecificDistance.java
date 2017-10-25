package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class driveSpecificDistance extends Command {

	public driveSpecificDistance(double distance) {
		requires(Robot.driveTrain);
	}
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// TBD - should probably be some initialization of the drive in here. Maybe set the motor speeds to zero?
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}

}
