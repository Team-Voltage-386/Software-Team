package org.usfirst.frc.team863.robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SparkOnNegative extends Thread {
	public boolean sparkState;
	boolean previous = false;
	
	SparkOnNegative() {
		sparkState = false;
	}

	public void run() {
		while (!Thread.interrupted()) {
			if (RobotState.isOperatorControl()) {
				boolean buttonPressed =Robot.manipulator.getRawButton(1);
				//System.out.println("Thread running" + previous + buttonPressed);
				if (buttonPressed == true && previous == false) {
					if (!sparkState) {
						Robot.left.set(-1*SmartDashboard.getNumber("Speed", 0));
						Robot.right.set(-1*SmartDashboard.getNumber("Speed", 0));
						sparkState = true;
					} else {
						Robot.left.set(0);
						Robot.right.set(0);
						sparkState = false;
					}
				}
				previous = buttonPressed;
			}
		}
	}
}
