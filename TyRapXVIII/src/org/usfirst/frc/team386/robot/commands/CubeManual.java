package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.OI;
import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CubeManual extends Command {

    public CubeManual() {
	// Use requires() here to declare subsystem dependencies
	requires(Robot.cubeSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	try {
	    Robot.cubeSubsystem.run(Robot.oi.manipulator.getRawAxis(1), Robot.oi.manipulator.getRawAxis(2));
	} catch (Exception e) {
	    OI.alertOnce(e);
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
