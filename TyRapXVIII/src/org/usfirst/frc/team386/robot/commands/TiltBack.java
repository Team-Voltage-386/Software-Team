package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to correct tilt backwards.
 * 
 * This command is started automatically by the TiltDetection command.
 */
public class TiltBack extends Command {

    public TiltBack() {
	super();
	requires(Robot.tiltSubsystem);
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {

    }

    @Override
    protected void execute() {
	Robot.driveSubsystem.driveTank(-.5, -.5);
    }

    @Override
    protected boolean isFinished() {
	return (Robot.tiltSubsystem.pitch() < Robot.tiltSubsystem.pitchLeeway / 2);
    }

}
