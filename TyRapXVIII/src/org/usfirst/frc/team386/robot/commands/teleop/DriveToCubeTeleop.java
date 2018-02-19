package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToCubeTeleop extends Command {

    public DriveToCubeTeleop() {
	requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot.driveSubsystem.prepareDriveToCube();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.driveSubsystem.driveWithVision(Robot.oi.xboxControl.getRawAxis(RobotMap.driveLeftJoystickVertical));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	// This should return false as long as the button is pushed.
	return !(Robot.oi.xboxControl.getRawButton(RobotMap.driveToCubeButton)) && RobotState.isEnabled();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
