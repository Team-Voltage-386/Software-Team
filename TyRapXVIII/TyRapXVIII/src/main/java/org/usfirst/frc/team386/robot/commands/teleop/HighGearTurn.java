package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class HighGearTurn extends Command {
    int angle;
    Timer timer = new Timer();

    public HighGearTurn(int angle) {
	super();
	requires(Robot.driveSubsystem);
	this.angle = angle;
    }

    // Called once when the command executes
    protected void initialize() {
	// Robot.driveSubsystem.shift(FAST_GEAR);
	Robot.driveSubsystem.resetPidTurn(angle, -1);
	timer.reset();
    }

    double previousTime = 0;

    @Override
    protected void execute() {
	// SmartDashboard.putNumber("Time elapsed", timer.get() - previousTime);
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
