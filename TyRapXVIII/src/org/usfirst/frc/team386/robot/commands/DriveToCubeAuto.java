package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.CubeVisionThread;
import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive forward to a cube using vision assist.
 */
public class DriveToCubeAuto extends Command {

    CubeVisionThread.SelectorType type;

    public DriveToCubeAuto(CubeVisionThread.SelectorType type) {
	super();
	requires(Robot.driveSubsystem);
	Robot.driveSubsystem.prepareDriveToCube();
	this.type = type;
    }

    @Override
    protected void initialize() {
	// Robot.cubeVision.setSelectionMethod(type);
    }

    protected void execute() {
	Robot.driveSubsystem.driveWithVision(-DriveSubsystem.SLOW_AUTO_MODE_SPEED);
    }

    @Override
    public boolean isFinished() {
	return !RobotState.isEnabled();
	// return (Robot.cubeSubsystem.hasCube());
    }

    @Override
    protected void end() {
	Robot.cubeVision.setSelectionMethod(CubeVisionThread.SelectorType.bottom);
    }
}
