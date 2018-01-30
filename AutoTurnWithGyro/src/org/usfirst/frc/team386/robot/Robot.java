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
    public ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    WPI_TalonSRX frontLeft = new WPI_TalonSRX(1); /* device IDs here (1 of 2) */
    WPI_TalonSRX frontRight = new WPI_TalonSRX(4);

    /* extra talons for six motor drives */
    WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(2);
    WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(5);

    WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(3);
    WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(6);
    DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);
    public int gyroAngle = (int) (gyro.getAngle());

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
	m_chooser.addDefault("Default Auto", kDefaultAuto);
	m_chooser.addObject("My Auto", kCustomAuto);
	SmartDashboard.putData("Auto choices", m_chooser);
	leftSlave1.follow(frontLeft);
	leftSlave2.follow(frontLeft);
	rightSlave1.follow(frontRight);
	rightSlave2.follow(frontRight);
	frontRight.configContinuousCurrentLimit(20, 0);
	frontLeft.configContinuousCurrentLimit(20, 0);
	frontRight.enableCurrentLimit(true);
	frontLeft.enableCurrentLimit(true);
	frontRight.configOpenloopRamp(.1, 0);
	frontLeft.configOpenloopRamp(.1, 0);
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable chooser
     * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
     * remove all of the chooser code and uncomment the getString line to get the
     * auto name from the text box below the Gyro
     *
     * <p>
     * You can add additional auto modes by adding additional comparisons to the
     * switch structure below with additional strings. If using the SendableChooser
     * make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
	m_autoSelected = m_chooser.getSelected();
	// autoSelected = SmartDashboard.getString("Auto Selector",
	// defaultAuto);
	System.out.println("Auto selected: " + m_autoSelected);
	gyro.reset();
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
	switch (m_autoSelected) {
	case kCustomAuto:
	    // Put custom auto code here
	    break;
	case kDefaultAuto:
	default:
	    while ((int) gyro.getAngle() < 45) { // turning right
		SmartDashboard.putNumber("gyro", gyroAngle);
		drive.tankDrive(.6, -.6);
	    }
	    break;
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
