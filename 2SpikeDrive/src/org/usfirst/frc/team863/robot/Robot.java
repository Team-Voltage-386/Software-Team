/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team863.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	Talon left = new Talon(9);
	Talon right = new Talon(8);
	Joystick controler = new Joystick(0);
	final String leftName = "Left speed";
	final String rightName = "Right Speed";
	boolean on;
	@Override
	public void robotInit(){
		SmartDashboard.putNumber(leftName, 0);
		SmartDashboard.putNumber(rightName, 0);
	}
	@Override
	public void teleopInit() {
		on = false;
	}
	@Override
	public void teleopPeriodic() {
		if(controler.getRawButtonPressed(0)) {
			SmartDashboard.putNumber(leftName, -1 * SmartDashboard.getNumber(leftName, 0));
			SmartDashboard.putNumber(rightName, -1 * SmartDashboard.getNumber(rightName, 0));
		}
		if(controler.getRawButtonPressed(1)) {
			on = !on;
		}
		double leftOut = SmartDashboard.getNumber(leftName, 0);
		double rightOut = SmartDashboard.getNumber(rightName, 0);
		SmartDashboard.putNumber("Left out", leftOut);
		SmartDashboard.putNumber("Right out", rightOut);
		left.set(leftOut);
		right.set(rightOut);
	}
}
