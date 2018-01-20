/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team863.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	WPI_TalonSRX frontLeft = new WPI_TalonSRX(1); //Designates master motors
	WPI_TalonSRX frontRight = new WPI_TalonSRX(4);
	
	WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(2);
	WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(3); //Designates slave motors
	WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(5);
	WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(6);
	
	Ultrasonic sonic = new Ultrasonic(4,5);
	
	DifferentialDrive drive = new DifferentialDrive(frontLeft,frontRight);
	
	Joystick leftJoystick = new Joystick(0); //Designates joysticks
	Joystick rightJoystick = new Joystick(1);

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		leftSlave1.follow(frontLeft);
		leftSlave2.follow(frontLeft); //Enslaves motors to master motors
		rightSlave1.follow(frontRight);
		rightSlave2.follow(frontRight);
		frontLeft.configContinuousCurrentLimit(20, 0);
		frontRight.configContinuousCurrentLimit(20, 0);
		
		frontLeft.enableCurrentLimit(true);
		frontRight.enableCurrentLimit(true);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		while(sonic.getRangeInches()>10) {  //Instructs robot to drive while ultrasonic is greater than 10 in. from something(?)
			drive.tankDrive(.4,.4);
		}
		drive.tankDrive(0, 0);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		drive.tankDrive(leftJoystick.getY(), rightJoystick.getY());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
