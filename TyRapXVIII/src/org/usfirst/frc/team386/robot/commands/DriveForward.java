package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    double ticksRequired; // the number of encoder ticks indicating how far to drive
    double distance; // the distance in inches
    // scaleFactor is used because the TESTBOT has different encoders than the real
    // bot
    double scaleFactor = .73; // TESTBOT, WILL BE 1 FOR REAL ROBOT

    double fastGearMultiplier = .75; // used to reduce the ticks to travel since fast gear overshoots the goal

    public DriveForward(int distance) {
	super();
	requires(Robot.driveSubsystem);
	this.speed = DriveSubsystem.FAST_AUTO_MODE_SPEED;
	// the next line isn't needed as ticksRequired is recalculated in initialize
	// ticksRequired = 6.36 * distance * 4;
	this.distance = distance * scaleFactor;
    }

    public DriveForward(int distance, double speed) {
	super();
	requires(Robot.driveSubsystem);
	this.speed = speed;
	// the next line isn't needed as ticksRequired is recalculated in initialize
	// ticksRequired = 6.36 * distance * 4;
	this.distance = distance * scaleFactor;
    }

    // Called once when the command executes
    protected void initialize() {

	Robot.driveSubsystem.resetGyro();
	Robot.driveSubsystem.resetEncoders();
	// reduce the required distance traveled when driving in fast gear because of
	// overshoot
	if (Robot.driveSubsystem.getGearState() == DriveSubsystem.FAST_GEAR) {
	    ticksRequired = fastGearMultiplier * 6.36 * distance * 4;
	} else {
	    ticksRequired = 6.36 * distance * 4;
	}
	SmartDashboard.putNumber("ticks required", ticksRequired);

    }

    @Override
    protected void execute() {
	Robot.driveSubsystem.arcadeDriveStraight(speed);
    }

    @Override

    public boolean isFinished() {
	return (Math.abs(Robot.driveSubsystem.getLeftEncoder()) > Math.abs(ticksRequired * scaleFactor));
    }

    @Override
    protected void end() {
	Robot.driveSubsystem.resetGyro();
	// TESTBOT CHANGES
	// Robot.driveSubsystem.resetEncoders();
	// ^^ UNCOMMENT THAT LINE
    }

}
