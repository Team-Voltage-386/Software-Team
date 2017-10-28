package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class StopShooterCommand extends InstantCommand {

    public StopShooterCommand() {
	super();
	requires(Robot.shootSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	System.out.println("Stop shooter wheel");
	Robot.shootSubsystem.turnOffShooterWheel();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
