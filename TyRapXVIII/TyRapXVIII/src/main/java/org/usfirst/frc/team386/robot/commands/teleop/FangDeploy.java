package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class FangDeploy extends TimedCommand {

    public FangDeploy() {
	super(1.0);
    }

    @Override
    protected void end() {
	Robot.elevatorSubsystem.toggleElevatorLock();
    }

}
