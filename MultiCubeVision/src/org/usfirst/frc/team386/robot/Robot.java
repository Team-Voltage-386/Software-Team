/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team386.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
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
    // Servo xServo = new Servo(0);
    // Servo yServo = new Servo(0);
    CubeVisionThread cubeVision = new CubeVisionThread();
    WPI_TalonSRX frontLeft = new WPI_TalonSRX(1); /* device IDs here (1 of 2) */
    WPI_TalonSRX frontRight = new WPI_TalonSRX(4);

    /* extra talons for six motor drives */
    WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(2);
    WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(5);

    WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(3);
    WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(6);

    public static Joystick right = new Joystick(0);
    public static Joystick left = new Joystick(1);

    DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);
    public GearShift gearShift = new GearShift();
    Compressor compressor = new Compressor(0);

    @Override
    public void robotInit() {

	leftSlave1.follow(frontLeft);
	leftSlave2.follow(frontLeft);
	rightSlave1.follow(frontRight);
	rightSlave2.follow(frontRight);
	frontRight.configContinuousCurrentLimit(20, 0);
	frontLeft.configContinuousCurrentLimit(20, 0);
	frontRight.enableCurrentLimit(true);
	frontLeft.enableCurrentLimit(true);
	// frontRight.configOpenloopRamp(.1, 0);
	// frontLeft.configOpenloopRamp(.1, 0);
	cubeVision.start();
	// compressor.start();
	// gearShift.start();
    }

    @Override
    public void autonomousInit() {
	SmartDashboard.putNumber("Rect Choice", 0);
    }

    @Override
    public void autonomousPeriodic() {
	// System.out.println("Running");
	// cubeVision.setRectangleChoice((int) SmartDashboard.getNumber("Rect Choice",
	// 0));
	double error = cubeVision.getError();
	SmartDashboard.putNumber("error", error * .0005);
	drive.arcadeDrive(1, error * .0005);
	// HSVOutputStream.putFrame(cubeVision.outHSV);
	// rectOutputStream.putFrame(cubeVision.outRectMat);
    }

    @Override
    public void teleopPeriodic() {
	double leftY = left.getY();
	double rightY = right.getY();
	drive.tankDrive((-1 * deadBand(rightY, .1)), (-1 * deadBand(leftY, .1)));
    }

    @Override
    public void testPeriodic() {
    }

    public double deadBand(double in, double limit) {
	if (Math.abs(in) < limit) {
	    return 0;
	} else {
	    return in;
	}
    }
}
