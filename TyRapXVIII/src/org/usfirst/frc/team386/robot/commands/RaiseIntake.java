package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class RaiseIntake extends InstantCommand {
    DoubleSolenoid raiseIntake = new DoubleSolenoid(4, 5);

    public RaiseIntake() {
	super();
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	raiseIntake.set(DoubleSolenoid.Value.kReverse);
    }

}
