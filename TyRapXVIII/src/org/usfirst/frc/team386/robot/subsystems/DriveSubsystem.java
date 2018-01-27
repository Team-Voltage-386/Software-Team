package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.commands.ArcadeDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    WPI_TalonSRX frontLeft = new WPI_TalonSRX(1); /* device IDs here (1 of 2) */
    WPI_TalonSRX frontRight = new WPI_TalonSRX(4);

    /* extra talons for six motor drives */
    WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(2);
    WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(5);
    WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(3);
    WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(6);
    DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);
    Compressor compressor = new Compressor(0);
    DoubleSolenoid solenoid = new DoubleSolenoid(0, 1);
    Encoder leftEncoder = new Encoder(0, 1);
    Encoder rightEncoder = new Encoder(2, 3);

    public DriveSubsystem() {
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
	solenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void drive(double xSpeed, double zRotation) {
	// drive.arcadeDrive(xSpeed, zRotation);
	drive.arcadeDrive(deadBand(-1 * xSpeed, .1), deadBand(zRotation, .1));
    }

    private double deadBand(double in, double limit) {
	if (Math.abs(in) < limit) {
	    return 0;
	} else {
	    return in;
	}
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	setDefaultCommand(new ArcadeDrive());
    }

    public void shift() {
	// boolean buttonPressed = /* Robot.right.getRawButton(1) */
	// Robot.manipulator.getRawButton(5);

	if (solenoid.get() == DoubleSolenoid.Value.kReverse) {
	    solenoid.set(DoubleSolenoid.Value.kForward);
	} else {
	    solenoid.set(DoubleSolenoid.Value.kReverse);
	}

    }

    public void resetEncoders() {
	leftEncoder.reset();
	rightEncoder.reset();
    }

    public void moveForward(double inches) {
	double cir = 18.8;
	double encoderRatio = 3;
	double ticksPerRotation = 256;
	// double ticksRequired = (inches * ticksPerRotation) / (cir * encoderRatio);
	double ticksRequired = ((ticksPerRotation * encoderRatio) / cir) * inches;
	while (Math.abs(leftEncoder.getRaw()) < ticksRequired) {
	    drive.tankDrive(1.0, 1.0);
	}
	drive.tankDrive(0, 0); // drives forward at 0 speed
    }
}
