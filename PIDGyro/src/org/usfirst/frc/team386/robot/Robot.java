/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team386.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	WPI_TalonSRX frontLeft = new WPI_TalonSRX(1); /* device IDs here (1 of 2) */
	WPI_TalonSRX frontRight = new WPI_TalonSRX(4);

	/* extra talons for six motor drives */
	WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(2);
	WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(5);

	WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(3);
	WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(6);

	public ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		leftSlave1.follow(frontLeft);
		leftSlave2.follow(frontLeft);
		rightSlave1.follow(frontRight);
		rightSlave2.follow(frontRight);
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
	Timer timer = new Timer();
	//SendableChooser<Boolean> chooser = new SendableChooser<Boolean>();
	@Override
	public void autonomousInit() {
		timer.start();
		previousError = gyro.getAngle() - 90;
		SmartDashboard.putNumber("KP", -.03);
		SmartDashboard.putNumber("KI", -.02);
		SmartDashboard.putNumber("KD", -.015);
		/*chooser.addDefault("off", true);
		chooser.addObject("On", false);*/
		SmartDashboard.putNumber("Reset", 1);
		gyro.reset();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	double integral = 0, previousError = 0, previousTime, derivative = 0;
	@Override
	public void autonomousPeriodic() {
		while(RobotState.isAutonomous()) {
			if(SmartDashboard.getNumber("Reset", 1) == 1 ) {
				gyro.reset();
				integral = 0;//.03
				previousError = 0;//.02
				previousTime = 0;//.015
				timer.reset();
				SmartDashboard.putString("Done?", "not done");
			}
			else if(Math.abs(-1*gyro.getAngle()-90) > 0 || derivative > .01){
				double time = timer.get();
				double error = ((int)gyro.getAngle() + 90);
				derivative = (error-previousError)/(time-previousTime);
				integral = integral + error * (time-previousTime);
				frontLeft.set(SmartDashboard.getNumber("KP", 0) * error + SmartDashboard.getNumber("KI", 0) * integral + SmartDashboard.getNumber("KD", 0) * derivative);
				frontRight.set(SmartDashboard.getNumber("KP", 0) * error + SmartDashboard.getNumber("KI", 0) * integral + SmartDashboard.getNumber("KD", 0) * derivative);
				SmartDashboard.putNumber("Error", error);
				SmartDashboard.putNumber("proportional", SmartDashboard.getNumber("KP", 0) * error);
				SmartDashboard.putNumber("integral", SmartDashboard.getNumber("KI", 0) * integral);
				SmartDashboard.putNumber("derivative", SmartDashboard.getNumber("KD", 0) * derivative);
				SmartDashboard.putNumber("Gyro", (int)gyro.getAngle());
				previousTime = time;
				previousError = error;
			}
			else {
				SmartDashboard.putString("Done?", "done");
			}
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
