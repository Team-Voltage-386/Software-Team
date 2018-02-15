package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArmsSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    DoubleSolenoid arms = new DoubleSolenoid(RobotMap.armsForwardChannel, RobotMap.armsReverseChannel);

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
    }

    public void shiftArms() {
	if (arms.get() == DoubleSolenoid.Value.kReverse) {
	    arms.set(DoubleSolenoid.Value.kForward);
	    arms.set(DoubleSolenoid.Value.kForward);
	} else {
	    arms.set(DoubleSolenoid.Value.kReverse);
	    arms.set(DoubleSolenoid.Value.kReverse);
	}
    }
}
