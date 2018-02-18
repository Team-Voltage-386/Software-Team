package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Set the elevator to a certain position based on the number of encoder ticks.
 * The encoders MUST be zeroed while the elevator is at its lowest position, as
 * indicated by the lower limit switch.
 */
public class SetElevator extends Command {
    int ticks;

    public SetElevator(int ticksIn) {
	super();
	requires(Robot.elevatorSubsystem);
	ticks = ticksIn;
    }

    // Called once when the command executes
    protected void initialize() {

    }

    @Override
    protected void execute() {
	Robot.elevatorSubsystem.setHeight(ticks, SmartDashboard.getNumber("Elevator Speed", 0));
    }

    @Override
    protected boolean isFinished() {
	return (Robot.elevatorSubsystem.elevatorEncoder.get() < ticks
		|| Robot.elevatorSubsystem.lowerElevatorLimitSwitch.get()
		|| Robot.elevatorSubsystem.upperElevatorLimitSwitch.get());
    }

}
