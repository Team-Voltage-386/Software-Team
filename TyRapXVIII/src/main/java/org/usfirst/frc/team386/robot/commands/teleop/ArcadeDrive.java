package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive command that works with an arcade-style controller. This is meant to be
 * run as the default command of the drive subsystem if the driver is driving in
 * arcade mode.
 */
public class ArcadeDrive extends Command {

    public ArcadeDrive() {
	requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.driveSubsystem.driveArcade(Robot.oi.xboxControl.getRawAxis(RobotMap.driveLeftJoystickVertical),
		Robot.oi.xboxControl.getRawAxis(RobotMap.driveRightJoystickHorizontal));
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
