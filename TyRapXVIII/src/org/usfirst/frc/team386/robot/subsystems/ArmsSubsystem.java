package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This subsystem is responsible for lowering and raising the intake arms.
 */
public class ArmsSubsystem extends Subsystem {

    public static final DoubleSolenoid.Value LOWERED = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value RAISED = DoubleSolenoid.Value.kReverse;

    DoubleSolenoid arms = new DoubleSolenoid(RobotMap.armsForwardChannel, RobotMap.armsReverseChannel);

    @Override
    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
    }

    /**
     * Shift the arms to the other position.
     */
    public void shiftArms() {
	if (arms.get() == RAISED) {
	    setArms(LOWERED);
	} else {
	    setArms(RAISED);
	}
    }

    /**
     * Set arms to specified position.
     * 
     * @param position
     *            The arm position (either LOWERED or RAISED).
     */
    public void setArms(DoubleSolenoid.Value position) {
	arms.set(position);
	// arms.set(position);
    }
}
