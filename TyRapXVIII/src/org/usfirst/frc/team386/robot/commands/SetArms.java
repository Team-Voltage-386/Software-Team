package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Calls the set arms method
 */
public class SetArms extends InstantCommand {
    DoubleSolenoid.Value value;

    /**
     * Calls the set arms method
     */
    public SetArms(DoubleSolenoid.Value value) {
	super();
	this.value = value;
	requires(Robot.armsSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.armsSubsystem.setArms(value);
    }

}
