package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive the robot a specific distance from a wall, as detected by a rear
 * ultrasonic.
 */
public class DriveDistanceFromWall extends Command {

    private double distanceFromWall;
    private boolean goingForward;

    /**
     * Drive the robot a specific distance from a wall, as detected by a rear
     * ultrasonic.
     * 
     * @param distanceFromWall
     *            Distance in millimeters
     */
    public DriveDistanceFromWall(double distanceFromWall) {
	super();
	requires(Robot.driveSubsystem);
	this.distanceFromWall = distanceFromWall;
    }

    // Called once when the command executes
    protected void initialize() {
	goingForward = Robot.driveSubsystem.rearUltrasonic.getRangeMM() < distanceFromWall;
	Robot.driveSubsystem.resetGyro();
    }

    @Override
    protected void execute() {
	if (goingForward)
	    Robot.driveSubsystem.arcadeDriveStraight(DriveSubsystem.SLOW_AUTO_MODE_SPEED);
	else
	    Robot.driveSubsystem.arcadeDriveStraight(-1 * DriveSubsystem.SLOW_AUTO_MODE_SPEED);
    }

    @Override
    protected boolean isFinished() {
	if (goingForward)
	    return (Robot.driveSubsystem.rearUltrasonic.getRangeMM() > distanceFromWall);
	else
	    return (Robot.driveSubsystem.rearUltrasonic.getRangeMM() < distanceFromWall);
    }

}
