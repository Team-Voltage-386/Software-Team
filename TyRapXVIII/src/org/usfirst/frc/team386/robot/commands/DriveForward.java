package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * A command to drive forward a certain distance in inches.
 */
public class DriveForward extends InstantCommand {

    private int distance;

    /**
     * Construct the drive forward command with the given distance.
     * 
     * @param distance
     *            The distance in inches
     */
    public DriveForward(int distance) {
	super();
	requires(Robot.driveSubsystem);
	this.distance = distance;
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.moveForward(distance);
    }

}
