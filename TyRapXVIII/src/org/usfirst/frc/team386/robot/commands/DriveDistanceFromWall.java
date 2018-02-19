package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive the robot a specific distance from a wall, as detected by a rear
 * ultrasonic.
 */
public class DriveDistanceFromWall extends Command {

    private double distanceFromWall;
    private boolean goingForward;

    /**
     * The distance from the wall, in millimeters.
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
    }

    @Override
    protected void execute() {
	Robot.driveSubsystem.moveDistanceFromWall(distanceFromWall, goingForward);
    }

    @Override
    protected boolean isFinished() {
	if (goingForward)
	    return (Robot.driveSubsystem.rearUltrasonic.getRangeMM() > distanceFromWall) || !RobotState.isEnabled();
	else
	    return (Robot.driveSubsystem.rearUltrasonic.getRangeMM() < distanceFromWall) || !RobotState.isEnabled();
    }

}
