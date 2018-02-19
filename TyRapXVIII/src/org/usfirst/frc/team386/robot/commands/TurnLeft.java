package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Turn left some angle. The angle is specified in the constructor.
 */
public class TurnLeft extends Command {

    public TurnLeft(int angle) {
	super();
	requires(Robot.driveSubsystem);
	Robot.driveSubsystem.resetPidTurn(angle, -1);
    }

    // Called once when the command executes
    protected void initialize() {

    }

    @Override
    protected void execute() {
	Robot.driveSubsystem.turnWithPid();
    }

    @Override
    protected boolean isFinished() {
	return !Robot.driveSubsystem.pidTurnDone() && RobotState.isEnabled();
    }

}
