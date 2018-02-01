package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Turn right some angle. The angle is specified in the constructor.
 */
public class TurnRight extends InstantCommand {

    private int angle;

    public TurnRight(int angle) {
	super();
	requires(Robot.driveSubsystem);
	this.angle = angle;
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.driveSubsystem.turnRight(angle);
    }

}
