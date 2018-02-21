package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Climb extends Command {
    private boolean stop = false;

    public Climb() {
	requires(Robot.elevatorSubsystem);
	stop = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	this.stop = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	if (!stop)
	    Robot.elevatorSubsystem.climb();
	if (!Robot.elevatorSubsystem.latchLimitSwitch.get()) {
	    Robot.elevatorSubsystem.lock(ElevatorSubsystem.LOCKED);
	    stop = true;
	    Robot.elevatorSubsystem.stopSubsystem();
	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return !Robot.oi.manipulator.getRawButton(RobotMap.climbButton);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
