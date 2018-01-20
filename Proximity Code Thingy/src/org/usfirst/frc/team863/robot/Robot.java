/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team863.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team863.robot.commands.ExampleCommand;
import org.usfirst.frc.team863.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
    Joystick leftJoystick = new Joystick(0);
    Joystick rightJoystick = new Joystick(1);
    WPI_TalonSRX leftTalonOne = new WPI_TalonSRX(1); //master left
    WPI_TalonSRX leftTalonTwo = new WPI_TalonSRX(2);
    WPI_TalonSRX leftTalonThree = new WPI_TalonSRX(3);
    WPI_TalonSRX rightTalonOne = new WPI_TalonSRX(4); //master right
    WPI_TalonSRX rightTalonTwo = new WPI_TalonSRX(5);
    WPI_TalonSRX rightTalonThree = new WPI_TalonSRX(6);
    DifferentialDrive drive = new DifferentialDrive(leftTalonOne, rightTalonOne);
    Ultrasonic ultra = new Ultrasonic(4,5);
	public static final ExampleSubsystem kExampleSubsystem
			= new ExampleSubsystem();
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() 
	{
	    leftTalonOne.follow(leftTalonOne);
	    leftTalonTwo.follow(leftTalonOne);
	    leftTalonThree.follow(leftTalonOne);
	    rightTalonOne.follow(leftTalonOne);
	    rightTalonTwo.follow(rightTalonOne);
	    rightTalonThree.follow(rightTalonOne);
	    leftTalonOne.configContinuousCurrentLimit(5,0);
	    rightTalonOne.configContinuousCurrentLimit(5,0);
	    leftTalonOne.enableCurrentLimit(true);
	    rightTalonOne.enableCurrentLimit(true);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
	    while(ultra.getRangeInches() > 15) //while the sensor is more than 15 inches away
		{
		drive.tankDrive(.5, .5);
		}
		drive.tankDrive(0, 0);
	}

	@Override
	public void teleopInit() {
		
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
