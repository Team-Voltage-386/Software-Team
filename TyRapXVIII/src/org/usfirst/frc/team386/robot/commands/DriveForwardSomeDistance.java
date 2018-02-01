package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.OI;
import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.CubeSubsystem;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * This command drives forward a fixed distance. It is intended to be used in
 * autonomous mode.
 */
public class DriveForwardSomeDistance extends InstantCommand {

    // The distance to drive forward in inches.
    public static final int DISTANCE = 168; //the distance to half of the switch
    public static final int ANGLE = 90;
    public static final int HALF_ROBOT_SIZE = 19; //the half of the robot's lenght

    public DriveForwardSomeDistance() {
	super();
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.moveForward(DISTANCE-HALF_ROBOT_SIZE);
	Robot.driveSubsystem.turnRight(ANGLE);
    }

}
