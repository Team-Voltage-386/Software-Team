package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive forward to a cube using vision assist.
 */
public class DriveToCubeAuto extends Command {

    public DriveToCubeAuto() {
	super();
	requires(Robot.driveSubsystem);
	Robot.driveSubsystem.prepareDriveToCube();
    }

    protected void execute() {
	Robot.driveSubsystem.driveWithVision(DriveSubsystem.SLOW_AUTO_MODE_SPEED);
    }

    @Override
    public boolean isFinished() {
	return !(RobotState.isEnabled() || Robot.cubeSubsystem.hasCube());
    }
}
