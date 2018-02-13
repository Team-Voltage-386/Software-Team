/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team863.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import Utility.AnalogUltrasonic;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends IterativeRobot {

    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    WPI_TalonSRX frontLeft = new WPI_TalonSRX(1); /* device IDs here (1 of 2) */
    WPI_TalonSRX frontRight = new WPI_TalonSRX(4);

    /* extra talons for six motor drives */
    WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(2);
    WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(5);

    // WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(3);
    // WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(6);
    DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);
    Compressor compressor = new Compressor(0);
    public static Joystick right = new Joystick(0);
    public static Joystick left = new Joystick(1);
    public static Joystick manipulator = new Joystick(2);
    Encoder leftEncodee = new Encoder(0, 1);
    Encoder rightEncodee = new Encoder(2, 3);
    public GearShift gearShift = new GearShift();
    SmartDashboard smarty = new SmartDashboard();
    public final static AnalogUltrasonic ultra = new AnalogUltrasonic(0, 1.18, 10.3);
    // public Ultrasonic ultra2 = new Ultrasonic(0,1);
    public ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    double pigeonYPR[] = new double[3];
    PigeonIMU pigeon = new PigeonIMU(0);
<<<<<<< HEAD

=======
    public static final double defaultSpeed = 0.5;
    
>>>>>>> 511d021340fea294c0609b24e790d3417314459a
    @Override
    public void robotInit() {
	rightEncodee.reset();
	leftEncodee.reset();
	System.out.println("Started");
	leftSlave1.follow(frontLeft);
	// leftSlave2.follow(frontLeft);
	rightSlave1.follow(frontRight);
	// rightSlave2.follow(frontRight);
	frontRight.configContinuousCurrentLimit(20, 0);
	frontLeft.configContinuousCurrentLimit(20, 0);
	frontRight.enableCurrentLimit(true);
	frontLeft.enableCurrentLimit(true);
	frontRight.configOpenloopRamp(.1, 0);
	frontLeft.configOpenloopRamp(.11, 0);

	compressor.start();
	gearShift.start();

	pigeon.getYawPitchRoll(pigeonYPR);
    }

    @Override
    public void teleopInit() {
	rightEncodee.reset();
	leftEncodee.reset();
	// SmartDashboard.putNumber("pitch", pigeonYPR[1]);
    }

    public double deadBand(double in, double limit) {
	if (Math.abs(in) < limit) {
	    return 0;
	} else {
	    return in;
	}
    }

    @Override
    public void teleopPeriodic() {
	// leftEncodee.reset();
	// rightEncodee.reset();
	SmartDashboard.putBoolean("Its Alive!", isAutonomous());
	SmartDashboard.putBoolean("Its Alive!2", RobotState.isAutonomous());
	SmartDashboard.putBoolean("mind control!!", isOperatorControl());
	SmartDashboard.putBoolean("mind control!!", RobotState.isOperatorControl());
	double leftY = left.getY();
	double rightY = right.getY();
	drive.tankDrive((-1 * deadBand(rightY, .1)), (-1 * deadBand(leftY, .1)));
	// drive.arcadeDrive(deadBand(-1 * manipulator.getRawAxis(1), .1),
	// deadBand(manipulator.getRawAxis(2), .1)); // on
	// newer
	// dark
	// blue
	// controller
	// axis
	// is
	// #4
	SmartDashboard.putNumber("y", manipulator.getRawAxis(1));
	SmartDashboard.putNumber("z", manipulator.getRawAxis(2));
	SmartDashboard.putNumber("Left Encoder", leftEncodee.get() * -1);
	SmartDashboard.putNumber("Right Encoder", rightEncodee.get());
	// System.out.println("Right Encoder:"+rightEncodee.get());
	// System.out.println("Left Encoder:"+leftEncodee.get());
	pigeon.getYawPitchRoll(pigeonYPR);
	SmartDashboard.putNumber("pitch", pigeonYPR[1]);
	// SmartDashboard.putNumber("compass", pigeon.getAbsoluteCompassHeading());
	SmartDashboard.putNumber("yaw", pigeonYPR[0]);
	tiltPrevention();
    }
    public void tiltPrevention() {
	if (pitch() > 1) {
	    drive.tankDrive((defaultSpeed * 1.25), (defaultSpeed * 1.25));
	    Timer.delay(1.5);
	} else if (pitch() < -1) {
	    drive.tankDrive(-(defaultSpeed * 1.25), -(defaultSpeed * 1.25));
	    Timer.delay(1.5);
	}
    }
    public double pitch() {
	double[] pig = new double[3];
	pigeon.getYawPitchRoll(pig);
	return pig[1];
    }

    public void moveForward(double xinch) {
	double cir = 18.8;
	double encodeeRatio = 3;
	double revs = ((xinch * 256) / (cir * encodeeRatio));
	while (RobotState.isAutonomous()
		&& Math.abs(leftEncodee.getRaw()) < revs) /*
							   * || (Math.abs(rightEncodee.getRaw()) < 256)
							   */ {
	    SmartDashboard.putNumber("Left Encodee Number :D ", leftEncodee.getRaw());
	    // System.out.println("Left Encodee Number :D
	    // "+leftEncodee.getRaw());
	    SmartDashboard.putNumber("the always Right num: ", rightEncodee.getRaw());
	    // System.out.println("the always Right num:
	    // "+rightEncodee.getRaw());
	    drive.tankDrive(defaultSpeed, defaultSpeed);
	}
	drive.tankDrive(0, 0); // drives forward at 0 speed
    }

    /*
     * public void autonomousInit(){ leftEncodee.reset(); rightEncodee.reset();
     * SmartDashboard.putBoolean("Its Alive!", isAutonomous());
     * SmartDashboard.putBoolean("Its Alive!2", RobotState.isAutonomous());
     * SmartDashboard.putBoolean("mind control!!", isOperatorControl());
     * SmartDashboard.putBoolean("mind control!!2", RobotState.isOperatorControl());
     * moveForward(5); }
     */
    /*
     * public void shift() { DoubleSolenoid piston = new DoubleSolenoid(0,1);
     * if(left.getRawButtonPressed(0)== true) {
     * 
     * if(piston.) }
     * 
     * }
     */
    public void autonomousInit() {
	leftEncodee.reset();
	rightEncodee.reset();
	// hello world
    }

    public void autonomousPeriodic() {
	while (!isOperatorControl() && Math.abs(leftEncodee.getRaw()) < 80) // test
									    // thursday
	{
	    drive.tankDrive(defaultSpeed, defaultSpeed);
	    SmartDashboard.putNumber("Encoder", leftEncodee.getRaw());
	    SmartDashboard.putNumber("Ultra", ultra.getInches());
	}
	while (!isOperatorControl() && Math.abs(gyro.getAngle()) < 80) {
	    drive.tankDrive(-defaultSpeed, defaultSpeed);
	}
	drive.tankDrive(0, 0);
    }
}
