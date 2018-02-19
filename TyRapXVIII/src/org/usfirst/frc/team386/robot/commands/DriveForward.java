package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command to drive forward a certain distance in inches.
 */
public class DriveForward extends Command {

    private double speed;

    /**
     * Construct the drive forward command with the given distance.
     * 
     * @param distance
     *            The distance in inches
     */
    double ticksRequired;

    public DriveForward(int distance) {
	super();
	requires(Robot.driveSubsystem);
	this.speed = DriveSubsystem.FAST_AUTO_MODE_SPEED;
	ticksRequired = 6.36 * distance * 4;
    }

    public DriveForward(int distance, double speed) {
	super();
	requires(Robot.driveSubsystem);
	this.speed = speed;
	ticksRequired = 6.36 * distance * 4;
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.gyro.reset();
	Robot.driveSubsystem.resetEncoders();
    }

    @Override
    protected void execute() {
	Robot.driveSubsystem.arcadeDriveStraight(speed);
    }

    @Override
    public boolean isFinished() {
	return (Math.abs(Robot.driveSubsystem.getLeftEncoder()) > ticksRequired) || !RobotState.isEnabled();
    }

}
