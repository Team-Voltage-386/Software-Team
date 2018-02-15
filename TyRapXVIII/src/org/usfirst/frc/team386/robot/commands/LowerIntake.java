package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class LowerIntake extends InstantCommand {
    DoubleSolenoid lowerIntake = new DoubleSolenoid(4, 5); // find actual value

    public LowerIntake() {
	super();
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	lowerIntake.set(DoubleSolenoid.Value.kForward);
    }

}
