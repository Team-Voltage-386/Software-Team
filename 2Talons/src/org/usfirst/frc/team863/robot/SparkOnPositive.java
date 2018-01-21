package org.usfirst.frc.team863.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SparkOnPositive extends Thread{
	public boolean sparkState;
	boolean previous = false;
	
	SparkOnPositive() {
		sparkState = false;
	}

	public void run() {
		while (!Thread.interrupted()) {
			if (RobotState.isOperatorControl()) {
				boolean buttonPressed =Robot.manipulator.getRawButton(1);
				//System.out.println("Thread running" + previous + buttonPressed);
				while(!Robot.limit.get()) {
				if (buttonPressed == true && previous == false) {
					if (!sparkState) {
						Robot.left.set(SmartDashboard.getNumber("Speed", 0));
						Robot.right.set(SmartDashboard.getNumber("Speed", 0));
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
			Robot.left.set(0);
			Robot.right.set(0);
		}
	}

}
