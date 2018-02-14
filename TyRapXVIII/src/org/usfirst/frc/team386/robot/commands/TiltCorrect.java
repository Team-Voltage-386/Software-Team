package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;

import org.usfirst.frc.team386.robot.Robot;
/**
 *
 */
public class TiltCorrect extends InstantCommand {

    public TiltCorrect() {
        super();
        requires(Robot.tiltSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.tiltSubsystem.tiltCorrection();
    }

}
