/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team863.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends IterativeRobot {
	WPI_TalonSRX frontLeft = new WPI_TalonSRX(1); 		/* device IDs here (1 of 2) */
	WPI_TalonSRX frontRight = new WPI_TalonSRX(4);

	/* extra talons for six motor drives */
	WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(2);
	WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(5);
	WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(3);
	WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(6);
	DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);
	Compressor compressor = new Compressor(0);
	public static Joystick right = new Joystick(0);
	public static Joystick left = new Joystick(1);
	public GearShift gearShift = new GearShift();
	@Override
	public void robotInit() {
		System.out.println("Started");
		leftSlave1.follow(frontLeft);
    	leftSlave2.follow(frontLeft);
    	rightSlave1.follow(frontRight);
    	rightSlave2.follow(frontRight);
		frontRight.configContinuousCurrentLimit(10, 0);
		
		frontLeft.configContinuousCurrentLimit(10, 0);
		
		frontRight.enableCurrentLimit(true);
		
		frontLeft.enableCurrentLimit(true);
//		rearRight.configOpenloopRamp(2, 0);
//		midRight.configOpenloopRamp(2, 0);
//		frontRight.configOpenloopRamp(2, 0);
//		rearLeft.configOpenloopRamp(2, 0);
//		midLeft.configOpenloopRamp(2, 0);
//		frontRight.configOpenloopRamp(2, 0);
		
		compressor.start();
		gearShift.start();
		
	}
	@Override
	public void teleopInit() {
		
	}

	
	
	
	@Override
	public void teleopPeriodic() {
		drive.tankDrive(left.getY(), right.getY());	
	}
	
//	public void shift() {
//		DoubleSolenoid piston = new DoubleSolenoid(0,1);
//		if(left.getRawButtonPressed(0)== true)
//		{
//			if(piston.)
//		}
//		
//	}
}
