package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.CubeVisionThread;
import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive forward to a cube using vision assist.
 */
public class DriveToCubeAuto extends Command {

    CubeVisionThread.SelectorType type;
    double time;

    public DriveToCubeAuto(CubeVisionThread.SelectorType type, double time) {
	super();
	requires(Robot.driveSubsystem);
	Robot.driveSubsystem.prepareDriveToCube();
	this.type = type;
	this.time = time;
    }

    Timer timer = new Timer();

    @Override
    protected void initialize() {
	timer.start();
	Robot.cubeVision.setSelectionMethod(type);
    }

    protected void execute() {
	Robot.driveSubsystem.driveWithVision(-DriveSubsystem.FAST_AUTO_MODE_SPEED);
    }

    @Override
    public boolean isFinished() {
	return !RobotState.isEnabled() || timer.get() > time;
	// return (Robot.cubeSubsystem.hasCube());
    }

    @Override
    protected void end() {
	Robot.cubeVision.setSelectionMethod(CubeVisionThread.SelectorType.bottom);
    }
}
