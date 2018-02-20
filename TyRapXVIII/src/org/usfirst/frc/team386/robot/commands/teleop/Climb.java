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
<<<<<<< HEAD
	    stop = true;
	    Robot.elevatorSubsystem.stop();
=======
	    Robot.elevatorSubsystem.stopDefaultCommand();
	    this.stop = true;
>>>>>>> 34daa1d4b41ec27300b648999aa1da403aa56345
	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
<<<<<<< HEAD
	return !Robot.oi.manipulator.getRawButton(RobotMap.climbButton);
=======
	if (stop) {
	    return true;
	} else {
	    return !Robot.oi.manipulator.getRawButton(RobotMap.climbButton);
	}
>>>>>>> 34daa1d4b41ec27300b648999aa1da403aa56345
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
