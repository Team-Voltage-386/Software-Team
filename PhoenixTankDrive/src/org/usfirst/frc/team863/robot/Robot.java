/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team863.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import Utility.AnalogUltrasonic;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	Encoder leftEncodee = new Encoder(0,2);
	Encoder rightEncodee = new Encoder(1,3);
	public GearShift gearShift = new GearShift();
	SmartDashboard smarty = new SmartDashboard();
	public final static AnalogUltrasonic ultra = new AnalogUltrasonic(0, 1.18, 10.3);
	//public Ultrasonic ultra2 = new Ultrasonic(0,1);
	public ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	@Override
	public void robotInit() {
		rightEncodee.reset();
		leftEncodee.reset();
		System.out.println("Started");
		leftSlave1.follow(frontLeft);
		leftSlave2.follow(frontLeft);
		rightSlave1.follow(frontRight);
		rightSlave2.follow(frontRight);
		frontRight.configContinuousCurrentLimit(20, 0);
		frontLeft.configContinuousCurrentLimit(20, 0);
		frontRight.enableCurrentLimit(true);
		frontLeft.enableCurrentLimit(true);
		
		frontRight.configOpenloopRamp(.1, 0);
		frontLeft.configOpenloopRamp(.11, 0);
		
		compressor.start();
		gearShift.start();
		
	}
	@Override
	public void teleopInit() {
		rightEncodee.reset();
		leftEncodee.reset();
	}
	public double deadBand(double in, double limit) {
		if(Math.abs(in) < limit) {
			return 0;
		}
		else {
			return in;
		}
	}

	
	
	
	@Override
	public void teleopPeriodic() {
		double leftY = left.getY();
		double rightY = right.getY();
		drive.tankDrive(deadBand(leftY, .1), deadBand(rightY, .1));
		SmartDashboard.putNumber("Left Encoder", leftEncodee.get()*-1);
		SmartDashboard.putNumber("Right Encoder", rightEncodee.get());
		
//		System.out.println("Right Encoder:"+rightEncodee.get());
//		System.out.println("Left Encoder:"+leftEncodee.get());
		
	}
	
//	public void shift() {
//		DoubleSolenoid piston = new DoubleSolenoid(0,1);
//		if(left.getRawButtonPressed(0)== true)
//		{
	
//			if(piston.)
//		}
//		
//	}
	public void autonomousInit()
	{
	    leftEncodee.reset();
	    rightEncodee.reset();
	}
	public void autonomousPeriodic()
	{
	    while(!isOperatorControl() && Math.abs(leftEncodee.getRaw()) < 80) //test thursday
	    {
		drive.tankDrive(.5, .5);
		SmartDashboard.putNumber("Encoder", leftEncodee.getRaw());
		SmartDashboard.putNumber("Ultra", ultra.getInches());
	    }
	    while(!isOperatorControl() && Math.abs(gyro.getAngle() )< 80)
	    {
		drive.tankDrive(-.5, .5);
	    }
	    drive.tankDrive(0,0);
	}
}
