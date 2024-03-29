package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Set the elevator to a certain position based on the number of encoder ticks.
 * The encoders MUST be zeroed while the elevator is at its lowest position, as
 * indicated by the lower limit switch.
 */
public class SetElevator extends Command {
    int ticks;

    /**
     * Set the elevator to a certain position based on the number of encoder ticks.
     * 
     * @param ticksIn
     *            the encoder ticks to set the elevator to
     */
    public SetElevator(int ticksIn) {
	super();
	requires(Robot.elevatorSubsystem);
	ticks = ticksIn;
    }

    boolean goingDown = false;

    // Called once when the command executes
    protected void initialize() {
	if (ticks < Robot.elevatorSubsystem.elevatorEncoder.get()) {
	    goingDown = false;
	} else {
	    goingDown = true;
	}
    }

    @Override
    protected void execute() {
	Robot.elevatorSubsystem.setHeight(ticks, goingDown);
    }

    @Override
    protected boolean isFinished() {
	if (!goingDown)
	    return (Robot.elevatorSubsystem.elevatorEncoder.get() < ticks
		    || !Robot.elevatorSubsystem.upperElevatorLimitSwitch.get());
	else
	    return ((Robot.elevatorSubsystem.elevatorEncoder.get() > ticks)
		    || !Robot.elevatorSubsystem.lowerElevatorLimitSwitch.get());
    }

    @Override
    protected void end() {
	Robot.elevatorSubsystem.stopElevator();
    }
}