package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class CameraCenterCommand extends InstantCommand {

    public CameraCenterCommand() {
        super();
        requires(Robot.cameracontrol);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.cameracontrol.center();
    }

}
