package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Set the elevator to a certain position based on the number of encoder ticks.
 * The encoders MUST be zeroed while the elevator is at its lowest position, as
 * indicated by the lower limit switch.
 */
public class SetElevator extends InstantCommand {
    int ticks;

    public SetElevator(int ticksIn) {
	super();
	requires(Robot.elevatorSubsystem);
	ticks = ticksIn;
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.elevatorSubsystem.setHeight(ticks);
    }

}
