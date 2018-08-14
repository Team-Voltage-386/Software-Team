package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveSeconds extends Command {
    Timer timer = new Timer();
    double time;

    public DriveSeconds(double time) {
	super();
	requires(Robot.driveSubsystem);
	this.time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot.driveSubsystem.resetGyro();
	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.driveSubsystem.arcadeDriveStraight(DriveSubsystem.FAST_AUTO_MODE_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return timer.get() > time;
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
