package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to correct tilt forward.
 * 
 * This command is started automatically by the TiltDetection command.
 */
public class TiltForward extends Command {

    public TiltForward() {
	super();
	requires(Robot.tiltSubsystem);
	requires(Robot.driveSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {

    }

    @Override
    protected void execute() {
	Robot.driveSubsystem.driveTank(.5, .5);
    }

    @Override
    protected boolean isFinished() {
	// TODO Auto-generated method stub
	return (Robot.tiltSubsystem.pitch() < Robot.tiltSubsystem.pitchLeeway / 2) || !RobotState.isEnabled();
    }

}
