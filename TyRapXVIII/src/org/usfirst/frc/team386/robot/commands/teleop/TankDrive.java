package org.usfirst.frc.team386.robot.commands.teleop;

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
	Robot.driveSubsystem.driveTank(squareKeepSign(Robot.oi.leftJoy.getY()),
		squareKeepSign(Robot.oi.rightJoy.getY()));
    }

    /**
     * Reduce the sensitivity of the joysticks to make tank driving easier.
     * 
     * @param in
     *            The speed.
     * @return The altered speed.
     */
    protected double squareKeepSign(double in) {
	if (in < 0) {
	    return in * in * -1;
	} else {
	    return in * in;
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
