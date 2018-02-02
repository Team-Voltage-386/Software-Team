package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.OI;
import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {
    public TankDrive() {
	requires(Robot.driveSubsystem);
    }

    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	try {
	    Robot.driveSubsystem.driveTank(Robot.oi.leftJoy.getY(), Robot.oi.rightJoy.getY());
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
