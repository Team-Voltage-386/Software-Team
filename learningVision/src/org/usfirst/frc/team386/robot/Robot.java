package org.usfirst.frc.team386.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


public class Robot extends IterativeRobot {
	
	//Sets Talon Variables
	WPI_TalonSRX frontLeft = new WPI_TalonSRX(1);
	WPI_TalonSRX frontRight = new WPI_TalonSRX(4);
	
	WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(2);
	WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(5);
	WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(3);
	WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(6);
	
	//Set Differential Drive
	DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);
	
	//Sets Up Joysticks
	public static Joystick right = new Joystick(1);
	public static Joystick left = new Joystick(0);
	
	//Sets Ultrasonic Variable
	Ultrasonic ultra = new Ultrasonic(4,5);
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Ensures That Slaves Obey
		System.out.println("Started");
		leftSlave1.follow(frontLeft);
		leftSlave2.follow(frontLeft);
		rightSlave1.follow(frontRight);
		rightSlave2.follow(frontRight);
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		drive.tankDrive(left.getY(), right.getY());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

