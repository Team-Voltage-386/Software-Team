package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Execute this command to interrupt all executing robot commands.
 * 
 * Stops the drive subsystem. TODO: Stops the cube subsystem. TODO: Stops the
 * elevator subsystem.
 */
public class DisableRobot extends InstantCommand {

    public DisableRobot() {
	super();
	requires(Robot.driveSubsystem);
	requires(Robot.cubeSubsystem);
	requires(Robot.elevatorSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.stop();
    }

}
