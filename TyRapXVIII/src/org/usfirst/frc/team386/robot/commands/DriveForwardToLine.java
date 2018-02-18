package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command to drive forward until a line sensor detects a line.
 */
public class DriveForwardToLine extends Command {

    public DriveForwardToLine() {
	super();
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.arcadeDriveStraight(DriveSubsystem.FAST_AUTO_MODE_SPEED);
	;
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
	return !(Robot.driveSubsystem.linesensor.get() || RobotState.isEnabled());
    }

}
