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
    DoubleSolenoid leftArm = new DoubleSolenoid(RobotMap.leftArmForwardChannel, RobotMap.leftArmReverseChannel);
    DoubleSolenoid rightArm = new DoubleSolenoid(RobotMap.rightArmForwardChannel, RobotMap.rightArmReverseChannel);

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
    }

    public void shiftArms() {
	if (leftArm.get() == DoubleSolenoid.Value.kReverse) {
	    leftArm.set(DoubleSolenoid.Value.kForward);
	    rightArm.set(DoubleSolenoid.Value.kForward);
	} else {
	    leftArm.set(DoubleSolenoid.Value.kReverse);
	    rightArm.set(DoubleSolenoid.Value.kReverse);
	}
    }
}
