package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is run continuously as the default command in the tilt
 * subsystem. It decides if tilt correction should be applied.
 */
public class TiltDetection extends Command {

    public TiltDetection() {
	requires(Robot.tiltSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	if (Robot.tiltSubsystem.pitch() > Robot.tiltSubsystem.pitchLeeway)
	    new TiltBack().start();
	if (Robot.tiltSubsystem.pitch() < -1 * Robot.tiltSubsystem.pitchLeeway)
	    new TiltForward().start();
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
