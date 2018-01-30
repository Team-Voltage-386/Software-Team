package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * This command drives forward a fixed distance. It is intended to be used in
 * autonomous mode.
 */
public class DriveForwardSomeDistance extends InstantCommand {

    // The distance to drive forward in inches.
    public static final int DISTANCE = 60;

    public DriveForwardSomeDistance() {
	super();
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.moveForward(DISTANCE);
	System.out.println("completed drive forward");
	System.out.println("left:" + Robot.driveSubsystem.leftEncoder.getRaw());
	System.out.println("right:" + Robot.driveSubsystem.rightEncoder.getRaw());
    }

}
