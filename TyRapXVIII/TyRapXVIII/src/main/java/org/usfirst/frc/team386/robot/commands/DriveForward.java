package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A command to drive forward a certain distance in inches.
 */
public class DriveForward extends Command {

    private double speed;

    double ticksRequired;
    double distance;
    double scaleFactor = 1;
    double fastGearMultiplier = .75;

    /**
     * Construct the drive forward command with the given distance.
     * 
     * @param distance
     *            The distance in inches
     */
    public DriveForward(int distance) {
	super();
	requires(Robot.driveSubsystem);
	this.speed = DriveSubsystem.FAST_AUTO_MODE_SPEED;
	ticksRequired = 6.36 * distance * 4;
	this.distance = distance * scaleFactor;
    }

    public DriveForward(double distance, double speed) {
	super();
	requires(Robot.driveSubsystem);
	this.speed = speed;
	ticksRequired = 6.36 * distance * 4;
	this.distance = distance * scaleFactor;
    }

    // Called once when the command executes
    protected void initialize() {

	Robot.driveSubsystem.resetGyro();
	Robot.driveSubsystem.resetEncoders();
	if (Robot.driveSubsystem.getGearState() == DriveSubsystem.FAST_GEAR)
	    ticksRequired = fastGearMultiplier * 6.36 * distance * 4;
	else {
	    ticksRequired = 6.36 * distance * 4;
	}
	// SmartDashboard.putNumber("ticks required", ticksRequired);
    }

    @Override
    protected void execute() {
	if (Robot.driveSubsystem.getGearState() == DriveSubsystem.FAST_GEAR)
	    ticksRequired = fastGearMultiplier * 6.36 * distance * 4;
	Robot.driveSubsystem.arcadeDriveStraight(speed);
    }

    @Override

    public boolean isFinished() {
	return (Math.abs(Robot.driveSubsystem.getLeftEncoder()) > Math.abs(ticksRequired * scaleFactor));
    }

    @Override
    protected void end() {
	Robot.driveSubsystem.resetGyro();
	Robot.driveSubsystem.resetEncoders();
    }

}
