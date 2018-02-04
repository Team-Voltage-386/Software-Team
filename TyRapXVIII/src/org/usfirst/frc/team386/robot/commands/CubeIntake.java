package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class CubeIntake extends InstantCommand {

    public CubeIntake() {
	// Use requires() here to declare subsystem dependencies
	requires(Robot.cubeSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
}
