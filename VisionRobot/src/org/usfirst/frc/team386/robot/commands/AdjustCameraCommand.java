package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AdjustCameraCommand extends InstantCommand {

    private double angle;

    public AdjustCameraCommand(double angle) {
	super();
	this.angle = angle;
	requires(Robot.cameraControlSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.cameraControlSubsystem.setAngle(angle);
    }

}
