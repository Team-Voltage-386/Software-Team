package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.teleop.ArcadeDrive;
import org.usfirst.frc.team386.robot.commands.teleop.TankDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
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

    public static final int MOTOR_CURRENT_LIMIT_AMPS = 15;
    public static final double OPEN_LOOP_RAMP_SECONDS = 0.1;
    public static final double DEAD_BAND_LIMIT = 0.1;

    public static final double DEFAULT_SPEED_MULTIPLIER = 0.75;
    public static final double BOOST_SPEED_MULTIPLIER = 1.0;
    public static final double FAST_AUTO_MODE_SPEED = 0.9;
    public static final double SLOW_AUTO_MODE_SPEED = 0.5;

    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private static final int NO_TIMEOUT = 0;

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

    Encoder leftEncoder = new Encoder(RobotMap.leftDriveEncoderChannelA, RobotMap.leftDriveEncoderChannelB, true);
    Encoder rightEncoder = new Encoder(RobotMap.rightDriveEncoderChannelA, RobotMap.rightDriveEncoderChannelB);

    public DigitalInput linesensor = new DigitalInput(RobotMap.lineSensorChannel);
    Ultrasonic rearUltrasonic = new Ultrasonic(RobotMap.pingChannel, RobotMap.echoChannel);
    Ultrasonic frontUltrasonic = new Ultrasonic(RobotMap.frontPingChannel, RobotMap.frontEchoChannel);

    Command defaultCommand;

    Timer timer = new Timer();

    ADXRS450_Gyro gyro = new ADXRS450_Gyro();

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

	rearUltrasonic.setAutomaticMode(true);
	frontUltrasonic.setAutomaticMode(true);

	solenoid.set(LOW_GEAR);
	timer.start();
    }

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
	SmartDashboard.putBoolean(Robot.LINE_SENSOR, linesensor.get());
	SmartDashboard.putNumber(Robot.REAR_ULTRASONIC, rearUltrasonic.getRangeMM());
	SmartDashboard.putNumber(Robot.FRONT_ULTRASONIC, frontUltrasonic.getRangeMM());
	SmartDashboard.putNumber(Robot.ENCODER_TALON_1, frontLeft.getSelectedSensorPosition(0));
	SmartDashboard.putNumber(Robot.ENCODER_TALON_4, frontRight.getSelectedSensorPosition(0));
	SmartDashboard.putNumber(Robot.LEFT_ENCODER_RIO, leftEncoder.get());
	SmartDashboard.putNumber(Robot.RIGHT_ENCODER_RIO, rightEncoder.get());
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
	drive.tankDrive(adjustSpeed(squareKeepSign(ySpeed)), adjustSpeed(squareKeepSign(y2Speed)));
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
	    shift(LOW_GEAR);
	} else {
	    shift(HIGH_GEAR);
	}
    }

    /**
     * Sift the solenoid specifically to either low or high gear.
     * 
     * @param gear
     *            LOW_GEAR or HIGH_GEAR
     */
    public void shift(Value gear) {
	solenoid.set(gear);
    }

    /**
     * Zero all drive encoders.
     */
    public void resetEncoders() {
	leftEncoder.reset();
	rightEncoder.reset();
	frontLeft.setSelectedSensorPosition(0, 0, 10);
	frontRight.setSelectedSensorPosition(0, 0, 10);
    }

    /**
     * Move forward the specific number of ticks.
     * 
     * @param ticks
     *            Encoder ticks to move forward
     */
    public void moveForwardTicks(int ticks) {
	while (Math.abs(rightEncoder.get()) < ticks && RobotState.isEnabled()) {
	    arcadeDriveStraight(FAST_AUTO_MODE_SPEED);
	    updateDiagnostics();
	}
	stop();
    }

    public void driveToCubeTeleop() {
	while (Robot.oi.xboxControl.getRawButton(3)) {
	    drive.arcadeDrive(Robot.oi.xboxControl.getRawAxis(1), Robot.cubeVision.getError() * -.005);
        updateDiagnostics();
	}
    }

    public void driveToCubeAuto() {
	while (RobotState.isEnabled()) {
	    drive.arcadeDrive(Robot.oi.xboxControl.getRawAxis(1), Robot.cubeVision.getError() * -.005);
	}
    }

    /**
     * Reverse the robot until it is the specified distance, in millimeters, from
     * the wall.
     * 
     * Distance from wall is determined using an ultrasonic sensor.
     * 
     * @param distanceFromWall
     *            The distance from the wall is in millimeters.
     */
    public void moveDistanceFromWall(double distanceFromWall) {
	gyro.reset();
	resetEncoders();

	if (rearUltrasonic.getRangeMM() > distanceFromWall) {
	    while ((rearUltrasonic.getRangeMM()) > distanceFromWall && RobotState.isEnabled()) {
		arcadeDriveStraight(-SLOW_AUTO_MODE_SPEED);
		updateDiagnostics();
	    }
	} else {
	    while ((rearUltrasonic.getRangeMM()) < distanceFromWall && RobotState.isEnabled()) {
		arcadeDriveStraight(SLOW_AUTO_MODE_SPEED);
		updateDiagnostics();
	    }
	}
	stop();
    }

    /**
     * Move forward the specific number of inches.
     * 
     * @param inches
     *            Inches to move forward
     * @param speed
     *            A value between 0 and 1
     */
    public void moveForward(double inches, double speed) {
	gyro.reset();
	resetEncoders();

	// double ticksRequired = 6.36 * inches * 4;
	// while (Math.abs(frontLeft.getSelectedSensorPosition(0)) < ticksRequired &&
	// RobotState.isEnabled()) {
	double ticksRequired = 6.36 * inches;
	while (Math.abs(rightEncoder.get()) < ticksRequired && RobotState.isEnabled()) {
	    arcadeDriveStraight(speed);
	    updateDiagnostics();
	}
	stop();
    }

    /**
     * Drive forward until the line sensor detects a line.
     * 
     * WARNING! If no line is sensed calling this method will drive forward
     * indefinitely.
     */
    public void driveForwardToLine() {
	while (linesensor.get() && RobotState.isEnabled()) {
	    arcadeDriveStraight(FAST_AUTO_MODE_SPEED);
	    updateDiagnostics();
	}
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
	// shift(HIGH_GEAR);
	double integral = 0, previousTime = timer.get(), derivative = 0;
	double tolerance = 1;
	gyro.reset();
	while ((Math.abs(direction * gyro.getAngle() - angle) > tolerance || Math.abs(gyro.getRate()) > 30)
		&& RobotState.isEnabled()) {
	    double time = timer.get();
	    double error = (gyro.getAngle() - (direction * angle));
	    derivative = gyro.getRate();
	    integral = integral + error * (time - previousTime);
	    if (Math.abs(-.05 * error + .0 * integral + -.01 * derivative) > .3) {
		frontLeft.set(-.1 * error + .0 * integral + -.01 * derivative);
		frontRight.set(-.1 * error + .0 * integral + -.01 * derivative);
	    } else {
		if (-.05 * error + .0 * integral + -.01 * derivative > 0) {
		    frontLeft.set(.3);
		    frontRight.set(.3);
		} else {
		    frontLeft.set(-.3);
		    frontRight.set(-.3);
		}
	    }
	    SmartDashboard.putNumber("Error", error);
	    SmartDashboard.putNumber("Previous value:", gyro.getRate());
	    SmartDashboard.putNumber("proportional", -.05 * error);
	    SmartDashboard.putNumber("integral", -0 * integral);
	    SmartDashboard.putNumber("derivative", -.005 * derivative);
	    SmartDashboard.putNumber("Gyro", gyro.getAngle());
	    previousTime = time;
	}
	SmartDashboard.putString("Using pid", "true");
	frontLeft.set(0);
	frontRight.set(0);
	SmartDashboard.putNumber("derivative", -.015 * derivative);
	SmartDashboard.putNumber("Gyro", gyro.getAngle());
    }

    /**
     * Turn the given angle and direction with no PID feedback loop.
     * 
     * @param angle
     *            The angle to turn
     * @param direction
     *            -1 (LEFT), 1 (RIGHT)
     */
    void turnWithoutPid(double angle, int direction) {
	gyro.reset();
	while ((int) Math.abs(gyro.getAngle()) < angle && RobotState.isEnabled()) {
	    drive.tankDrive(direction * GYRO_TURNING_SPEED, direction * -GYRO_TURNING_SPEED);
	}
	stop();
    }

    /**
     * Turn left the given angle.
     * 
     * @param angle
     *            Turning angle
     */
    public void turnLeft(double angle) {
	turnWithPid(angle, LEFT);
    }

    /**
     * Turn right the given angle.
     * 
     * @param angle
     *            Turning angle
     */
    public void turnRight(double angle) {
	turnWithPid(angle, RIGHT);
    }

    /**
     * Stop the robot from moving.
     */
    public void stop() {
	frontLeft.set(0);
	frontRight.set(0);
    }

    /**
     * Drives straight with help from gyro.
     * 
     * @param speed
     *            The speed to drive straight.
     */
    private void arcadeDriveStraight(double speed) {
	drive.arcadeDrive(speed, GYRO_COMPENSATION * gyro.getAngle());
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
     * inverted motors, or applying a dead band).
     * 
     * @param speed
     *            The input speed
     * @return The adjusted speed
     */
    private double adjustSpeed(double speed) {
	return deadBand((-1 * speedMultiplier * speed), DEAD_BAND_LIMIT);
    }

    /**
     * Reduce the sensitivity of the joystick input to make tank driving easier.
     * 
     * @param in
     *            The speed.
     * @return The altered speed.
     */
    private double squareKeepSign(double in) {
	if (in < 0) {
	    return in * in * -1;
	} else {
	    return in * in;
	}
    }

}
