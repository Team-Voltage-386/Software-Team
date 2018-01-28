package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.commands.ArcadeDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The DriveSubsystem is responsible for all drive logic. It is intended to be
 * used in both autonomous as well as teleoperated mode.
 */
public class DriveSubsystem extends Subsystem {
    public static final DoubleSolenoid.Value LOW_GEAR = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value HIGH_GEAR = DoubleSolenoid.Value.kReverse;

    private static final double ENCODER_RATIO = 3.0;
    private static final double TICKS_PER_ROTATION = 256;

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

    /**
     * Construct a new DriveSubsystem.
     */
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

	solenoid.set(LOW_GEAR);
    }

    /**
     * Sets a default command that is run at start and whenever no other command is
     * running.
     */
    public void initDefaultCommand() {
	setDefaultCommand(new ArcadeDrive());
    }

    /**
     * Drive using arcade-style values: speed and rotation.
     * 
     * @param xSpeed
     *            The speed, forward (positive values) or reverse (negative values)
     * @param zRotation
     *            The rotation
     */
    public void drive(double speed, double rotation) {
	drive.arcadeDrive(deadBand(-1 * speed, .1), deadBand(rotation, .1));
    }

    private double deadBand(double in, double limit) {
	if (Math.abs(in) < limit) {
	    return 0;
	} else {
	    return in;
	}
    }

    /**
     * Shift gears. This method will shift to the opposite of the current gear.
     */
    public void shift() {
	if (solenoid.get() == HIGH_GEAR) {
	    solenoid.set(LOW_GEAR);
	} else {
	    solenoid.set(HIGH_GEAR);
	}
    }

    /**
     * Zero all drive encoders.
     */
    public void resetEncoders() {
	leftEncoder.reset();
	rightEncoder.reset();
    }

    /**
     * Move forward the specific number of inches.
     * 
     * @param inches
     *            Inches to move forward
     */
    public void moveForward(double inches) {
	double speed = 1.0;
	double wheelCircumference = 18.8;

	// This is the original algorithm pulled in from the PhoenixTankDrive project
	// double ticksRequired = (inches * TICKS_PER_ROTATION) / (wheelCircumference *
	// ENCODER_RATIO);

	double ticksRequired = ((TICKS_PER_ROTATION * ENCODER_RATIO) / wheelCircumference) * inches;
	while (Math.abs(leftEncoder.getRaw()) < ticksRequired) {
	    drive.tankDrive(speed, speed);
	}
	drive.tankDrive(0, 0); // stops driving forward
    }
}
