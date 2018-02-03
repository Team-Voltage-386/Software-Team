package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.OI;
import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.ArcadeDrive;
import org.usfirst.frc.team386.robot.commands.TankDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The DriveSubsystem is responsible for all drive logic. It is intended to be
 * used in both autonomous or teleoperated mode.
 */
public class DriveSubsystem extends Subsystem {
    public static final DoubleSolenoid.Value LOW_GEAR = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value HIGH_GEAR = DoubleSolenoid.Value.kReverse;

    public static final double GYRO_COMPENSATION = -0.07;
    public static final double GYRO_TURNING_SPEED = .6;

    public static final double WHEEL_CIRCUMFERENCE = /* 18.85 */ 6 * Math.PI;
    public static final double ENCODER_RATIO = 3;

    public static final int MOTOR_CURRENT_LIMIT_AMPS = 20;
    public static final double OPEN_LOOP_RAMP_SECONDS = 0.1;
    public static final double DEAD_BAND_LIMIT = 0.1;

    public static final int NO_TIMEOUT = 0;

    public static final double DEFAULT_SPEED_MULTIPLIER = 0.75;
    public static final double BOOST_SPEED_MULTIPLIER = 1.0;
    public static final double AUTO_MODE_SPEED = 0.7;

    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    double speedMultiplier = DEFAULT_SPEED_MULTIPLIER;

    WPI_TalonSRX frontLeft = new WPI_TalonSRX(RobotMap.leftPrimaryDriveMotor);
    WPI_TalonSRX frontRight = new WPI_TalonSRX(RobotMap.rightPrimaryDriveMotor);

    /* extra talons for six motor drives */
    WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(RobotMap.leftFollowerDriveMotor);
    WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(RobotMap.rightFollowerDriveMotor);
    WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(RobotMap.leftFollowerDriveMotor2);
    WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(RobotMap.rightFollowerDriveMotor2);

    DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);

    Compressor compressor = new Compressor(RobotMap.gearShiftCompressor);

    DoubleSolenoid solenoid = new DoubleSolenoid(RobotMap.gearShiftSolenoidForwardChannel,
	    RobotMap.gearShiftSolenoidReverseChannel);

    public Encoder leftEncoder = new Encoder(RobotMap.leftDriveEncoderChannelA, RobotMap.leftDriveEncoderChannelB,
	    true);
    public Encoder rightEncoder = new Encoder(RobotMap.rightDriveEncoderChannelA, RobotMap.rightDriveEncoderChannelB);

    public DigitalInput linesensor = new DigitalInput(RobotMap.lineSensorChannel);

    Command defaultCommand;

    Timer timer = new Timer();

    /**
     * Construct a new DriveSubsystem.
     */
    public DriveSubsystem() {
	leftSlave1.follow(frontLeft);
	leftSlave2.follow(frontLeft);
	rightSlave1.follow(frontRight);
	rightSlave2.follow(frontRight);

	frontRight.configContinuousCurrentLimit(MOTOR_CURRENT_LIMIT_AMPS, NO_TIMEOUT);
	frontLeft.configContinuousCurrentLimit(MOTOR_CURRENT_LIMIT_AMPS, NO_TIMEOUT);
	frontRight.enableCurrentLimit(true);
	frontLeft.enableCurrentLimit(true);

	frontRight.configOpenloopRamp(OPEN_LOOP_RAMP_SECONDS, NO_TIMEOUT);
	frontLeft.configOpenloopRamp(OPEN_LOOP_RAMP_SECONDS, NO_TIMEOUT);

	compressor.start();

	solenoid.set(LOW_GEAR);
	timer.start();
    }

    /**
     * Drive using arcade-style values: speed and rotation.
     * 
     * @param xSpeed
     *            The speed, forward (positive values) or reverse (negative values)
     * @param zRotation
     *            The rotation
     */
    public void driveArcade(double xSpeed, double zRotation) {
	drive.arcadeDrive(adjustSpeed(xSpeed), deadBand(zRotation, DEAD_BAND_LIMIT));
    }

    /**
     * Drive using tank-style values: left speed, right speed.
     * 
     * @param ySpeed
     *            The left motor speed
     * @param y2Speed
     *            The right motor speed
     */
    public void driveTank(double ySpeed, double y2Speed) {
	drive.tankDrive(adjustSpeed(ySpeed), adjustSpeed(y2Speed));
    }

    /**
     * Boost the forward speed.
     */
    public void startBoost() {
	speedMultiplier = BOOST_SPEED_MULTIPLIER;
    }

    /**
     * Stop boosting the forward speed.
     */
    public void stopBoost() {
	speedMultiplier = DEFAULT_SPEED_MULTIPLIER;
    }

    /**
     * Set the drive mode.
     * 
     * @param arcadeMode
     *            If true, then use arcade mode, otherwise use tank mode.
     */
    public void setDriveMode(boolean arcadeMode) {
	if (arcadeMode) {
	    setDefaultCommand(new ArcadeDrive());
	} else {
	    setDefaultCommand(new TankDrive());
	}
    }

    /**
     * Sets a default command that is run at start and whenever no other command is
     * running.
     */
    public void initDefaultCommand() {
	if (defaultCommand == null) {
	    this.defaultCommand = new ArcadeDrive();
	}
	setDefaultCommand(defaultCommand);
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
	SmartDashboard.putNumber(Robot.LEFT_DRIVE_ENCODER, leftEncoder.get());
	SmartDashboard.putNumber(Robot.RIGHT_DRIVE_ENCODER, rightEncoder.get());
    }

    /**
     * Move forward the specific number of ticks.
     * 
     * @param ticks
     *            Encoder ticks to move forward
     */
    public void moveForwardTicks(int ticks) {
	while (Math.abs(rightEncoder.get()) < ticks) {
	    arcadeDriveStraight(AUTO_MODE_SPEED);
	    SmartDashboard.putNumber(Robot.LEFT_DRIVE_ENCODER, leftEncoder.get());
	    SmartDashboard.putNumber(Robot.RIGHT_DRIVE_ENCODER, rightEncoder.get());
	}
	stop();
	SmartDashboard.putNumber(Robot.LEFT_DRIVE_ENCODER, leftEncoder.get());
	SmartDashboard.putNumber(Robot.RIGHT_DRIVE_ENCODER, rightEncoder.get());
    }

    /**
     * Move forward the specific number of inches.
     * 
     * @param inches
     *            Inches to move forward
     */
    public void moveForward(double inches) {
	OI.gyro.reset();
	resetEncoders();

	double ticksRequired = 6.36 * inches;
	while (Math.abs(leftEncoder.get()) < ticksRequired) {
	    arcadeDriveStraight(AUTO_MODE_SPEED);
	    SmartDashboard.putNumber(Robot.LEFT_DRIVE_ENCODER, leftEncoder.get());
	    SmartDashboard.putNumber(Robot.RIGHT_DRIVE_ENCODER, rightEncoder.get());
	}
	stop();
	SmartDashboard.putNumber(Robot.LEFT_DRIVE_ENCODER, leftEncoder.get());
	SmartDashboard.putNumber(Robot.RIGHT_DRIVE_ENCODER, rightEncoder.get());
    }

    /**
     * Drive forward until the line sensor detects a line.
     * 
     * WARNING! If no line is sensed calling this method will drive forward
     * indefinitely.
     */
    public void driveForwardToLine() {
	while (linesensor.get()) {
	    arcadeDriveStraight(AUTO_MODE_SPEED);
	    SmartDashboard.putBoolean(Robot.LINE_SENSOR, linesensor.get());
	}
	SmartDashboard.putBoolean(Robot.LINE_SENSOR, linesensor.get());
	stop();
    }

    /**
     * Turn the given angle and direction with a PID feedback loop.
     * 
     * @param angle
     *            The angle to turn
     * @param direction
     *            -1 (LEFT), 1 (RIGHT)
     */
    void turnWithPid(double angle, int direction) {
	double integral = 0, previousError = 0, previousTime = timer.get(), derivative = 0;
	double tolerance = 0;
	OI.gyro.reset();
	while (Math.abs(direction * OI.gyro.getAngle() - angle) > tolerance || Math.abs(derivative) > .01) {
	    double time = timer.get();
	    double error = ((int) OI.gyro.getAngle() - (direction * angle));
	    derivative = (error - previousError) / (time - previousTime);
	    integral = integral + error * (time - previousTime);
	    frontLeft.set(-.03 * error + -.02 * integral + -.015 * derivative);
	    frontRight.set(-.03 * error + -.02 * integral + -.015 * derivative);
	    SmartDashboard.putNumber("Error", error);
	    SmartDashboard.putNumber("proportional", -.03 * error);
	    SmartDashboard.putNumber("integral", -.02 * integral);
	    SmartDashboard.putNumber("derivative", -.015 * derivative);
	    SmartDashboard.putNumber("Gyro", (int) OI.gyro.getAngle());
	    previousTime = time;
	    previousError = error;
	}
    }

    void turnWithoutPid(double angle, int direction) {
	while ((int) Math.abs(OI.gyro.getAngle()) < angle) {
	    drive.tankDrive(direction * GYRO_TURNING_SPEED, direction * -GYRO_TURNING_SPEED);
	}
    }

    /**
     * Turn left the given angle.
     * 
     * @param angle
     *            Turning angle
     */
    public void turnLeft(double angle) {
	turnWithPid(angle, LEFT);

	// turnWithoutPid(angle, LEFT);
	// stop();
    }

    /**
     * Turn right the given angle.
     * 
     * @param angle
     *            Turning angle
     */
    public void turnRight(double angle) {
	turnWithPid(angle, RIGHT);

	// turnWithoutPid(angle, RIGHT);
	// stop();
    }

    /**
     * Stop the robot from moving.
     */
    public void stop() {
	drive.tankDrive(0, 0);
    }

    /**
     * Drives straight with help from gyro.
     * 
     * @param speed
     *            The speed to drive straight.
     */
    private void arcadeDriveStraight(double speed) {
	drive.arcadeDrive(speed, GYRO_COMPENSATION * OI.gyro.getAngle());
    }

    /**
     * Return 0 if the given value is less than the specified limit.
     * 
     * This is used to make sure that the robot only moves forward when an input
     * value passes a certain threshold.
     * 
     * @param in
     *            The input value
     * @param limit
     *            The limit
     * @return 0 or the speed if the input is greater than the limit
     */
    private double deadBand(double in, double limit) {
	if (Math.abs(in) < limit) {
	    return 0;
	} else {
	    return in;
	}
    }

    /**
     * Applies adjustments to the speed (such as inverting the direction for
     * inverted motors, or applying a dead band.
     * 
     * @param speed
     *            The input speed
     * @return The adjusted speed
     */
    private double adjustSpeed(double speed) {
	return deadBand((-1 * speedMultiplier * speed), DEAD_BAND_LIMIT);
    }
}
