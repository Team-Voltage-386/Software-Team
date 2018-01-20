package org.usfirst.frc.team863.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotState;

public class GearShift extends Thread {
	public boolean gearState;
	boolean previous = false;
	DoubleSolenoid solenoid = new DoubleSolenoid(0, 1);

	GearShift() {
		gearState = false;
		solenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void run() {
		while (!Thread.interrupted()) {
			if (RobotState.isOperatorControl()) {
				boolean buttonPressed = /*Robot.right.getRawButton(1)*/ Robot.manipulator.getRawButton(5);
				//System.out.println("Thread running" + previous + buttonPressed);
				if (buttonPressed == true && previous == false) {
					System.out.println(gearState);
					if (!gearState) {
						solenoid.set(DoubleSolenoid.Value.kForward);
						gearState = true;
					} else {
						solenoid.set(DoubleSolenoid.Value.kReverse);
						gearState = false;
					}
				}
				previous = buttonPressed;
			}
		}
	}
}
