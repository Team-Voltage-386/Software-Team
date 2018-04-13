package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn left some angle. The angle is specified in the constructor.
 */
public class TurnLeft extends Command {
    int angle;
    Timer timer = new Timer();

    public TurnLeft(int angle) {
	super();
	requires(Robot.driveSubsystem);
	this.angle = angle;
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.resetPidTurn(angle, -1);
	timer.reset();
    }

    double previousTime = 0;

    @Override
    protected void execute() {
	SmartDashboard.putNumber("Time elapsed", timer.get() - previousTime);
	previousTime = timer.get();
	Robot.driveSubsystem.turnWithPid();
    }

    @Override
    protected boolean isFinished() {
	return Robot.driveSubsystem.pidTurnDone();
    }

    @Override
    protected void end() {
	Robot.driveSubsystem.resetEncoders();
	Robot.driveSubsystem.resetGyro();
    }

}
