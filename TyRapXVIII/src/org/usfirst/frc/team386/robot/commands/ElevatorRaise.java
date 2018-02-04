package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ElevatorRaise extends InstantCommand {

    public ElevatorRaise() {
	// Use requires() here to declare subsystem dependencies
	requires(Robot.elevatorSubsystem);
    }

    double percentage = 0;

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot.elevatorSubsystem.raiseElevatorTo(percentage);
    }
}
