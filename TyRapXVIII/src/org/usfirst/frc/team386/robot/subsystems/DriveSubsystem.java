package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.CubeVisionThread;
import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.teleop.ArcadeDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
    // Important note, proper notation would be high and low gear
    public static final DoubleSolenoid.Value SLOW_GEAR = DoubleSolenoid.Value.kReverse;
    public static final DoubleSolenoid.Value FAST_GEAR = DoubleSolenoid.Value.kForward;

    public static final double GYRO_COMPENSATION = -0.07;
    public static final double GYRO_TURNING_SPEED = .6;

    public static final double WHEEL_CIRCUMFERENCE = /* 18.85 */ 6 * Math.PI;
    public static final double ENCODER_RATIO = 3;

    public static final int MOTOR_CURRENT_LIMIT_AMPS = 15;
    public static final double OPEN_LOOP_RAMP_SECONDS = 0.1;
    public static final double DEAD_BAND_LIMIT = 0.0001;

    public static final double DEFAULT_SPEED_MULTIPLIER = 0.75;
    public static final double BOOST_SPEED_MULTIPLIER = 1.0;
    public static final double FAST_AUTO_MODE_SPEED = 0.9;// .75
    public static final double SLOW_AUTO_MODE_SPEED = 0.5;

    public boolean isGoingUpRamp = false;

    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private static final int NO_TIMEOUT = 0;
    final int kPeakCurrentAmps = 35; /* threshold to trigger current limit */
    final int kPeakTimeMs = 0; /* how long after Peak current to trigger current limit */
    final int kContinCurrentAmps = 25; /* hold current after limit is triggered */

    public static double speedMultiplier = BOOST_SPEED_MULTIPLIER;

    WPI_TalonSRX frontLeft = new WPI_TalonSRX(RobotMap.leftPrimaryDriveMotor);
    WPI_TalonSRX frontRight = new WPI_TalonSRX(RobotMap.rightPrimaryDriveMotor);

    /* extra talons for six motor drives */
    WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(RobotMap.leftFollowerDriveMotor);
    WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(RobotMap.rightFollowerDriveMotor);
    /*
     * Commented out because we removed // WPI_TalonSRX leftSlave2 = new
     * WPI_TalonSRX(RobotMap.leftFollowerDriveMotor2); // WPI_TalonSRX rightSlave2 =
     * new // WPI_TalonSRX(RobotMap.rightFollowerDriveMotor2);
     */

    DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);

    Compressor compressor = new Compressor(RobotMap.compressor);

    DoubleSolenoid gearShifter = new DoubleSolenoid(RobotMap.gearShiftSolenoidForwardChannel,
	    RobotMap.gearShiftSolenoidReverseChannel);

    public boolean IS_FAST_GEAR = (gearShifter.get() == FAST_GEAR);
    public Ultrasonic rearUltrasonic = new Ultrasonic(RobotMap.rearPingChannel, RobotMap.rearEchoChannel);
    Command defaultCommand;

    Timer timer = new Timer();

    public ADXRS450_Gyro gyro = new ADXRS450_Gyro();

    /**
     * Construct a new DriveSubsystem. No kidding
     */
    public DriveSubsystem() {
	leftSlave1.follow(frontLeft);
	rightSlave1.follow(frontRight);
	frontLeft.configPeakCurrentLimit(kPeakCurrentAmps, 10);
	frontLeft.configPeakCurrentDuration(kPeakTimeMs, 10); /* this is a necessary call to avoid errata. */
	frontLeft.configContinuousCurrentLimit(kContinCurrentAmps, 10);
	frontLeft.enableCurrentLimit(true); /* honor initial setting */

	frontRight.configPeakCurrentLimit(kPeakCurrentAmps, 10);
	frontRight.configPeakCurrentDuration(kPeakTimeMs, 10); /* this is a necessary call to avoid errata. */
	frontRight.configContinuousCurrentLimit(kContinCurrentAmps, 10);
	frontRight.enableCurrentLimit(true); /* honor initial setting */

	frontRight.configOpenloopRamp(OPEN_LOOP_RAMP_SECONDS, NO_TIMEOUT);
	frontLeft.configOpenloopRamp(OPEN_LOOP_RAMP_SECONDS, NO_TIMEOUT);

	compressor.start();

	rearUltrasonic.setAutomaticMode(true);

	gearShifter.set(SLOW_GEAR);
	timer.start();
    }

    /**
     * Update the smart dashboard with diagnostics values.
     */

    public void updateDiagnostics() {
	// SmartDashboard.putBoolean(Robot.LINE_SENSOR, linesensor.get());
	SmartDashboard.putNumber(Robot.REAR_ULTRASONIC, rearUltrasonic.getRangeMM());
	SmartDashboard.putBoolean("Shifter", DoubleSolenoid.Value.kForward == gearShifter.get());
	// SmartDashboard.putNumber(Robot.FRONT_ULTRASONIC,
	// frontUltrasonic.getRangeMM());
	SmartDashboard.putNumber(Robot.ENCODER_TALON_1, frontLeft.getSelectedSensorPosition(0));
	SmartDashboard.putNumber(Robot.ENCODER_TALON_3, frontRight.getSelectedSensorPosition(0));
	// SmartDashboard.putNumber(Robot.RIGHT_ENCODER_RIO, rightEncoder.get());
	SmartDashboard.putNumber("Gyro", gyro.getAngle());
	SmartDashboard.putString("Gear shifter state", gearShifter.get().toString());
	if (this.getCurrentCommand() != null)
	    SmartDashboard.putString("Active drive command", this.getCurrentCommand().getName());
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
     * 
     *            The left motor speed
     * @param y2Speed
     *            The right motor speed
     */
    public void driveTank(double ySpeed, double y2Speed) {
	drive.tankDrive(adjustSpeed(squareKeepSign(ySpeed)), adjustSpeed(squareKeepSign(y2Speed)));
    }

    /**
     * Boost the forward speed. Not used
     */
    public void startBoost() {
	speedMultiplier = BOOST_SPEED_MULTIPLIER;
    }

    /**
     * Stop boosting the forward speed. Not used
     */
    public void stopBoost() {
	speedMultiplier = DEFAULT_SPEED_MULTIPLIER;
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
	if (gearShifter.get() == FAST_GEAR) {
	    shift(SLOW_GEAR);
	} else {
	    shift(FAST_GEAR);
	}
    }

    /**
     * Sift the gearShifter specifically to either low or high gear.
     * 
     * @param gear
     *            SLOW_GEAR or FAST_GEAR
     */
    public void shift(Value gear) {
	gearShifter.set(gear);
    }

    /**
     * Zero all drive encoders.
     */
    public void resetEncoders() {
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
	while (Math.abs(frontLeft.getSelectedSensorPosition(0)) < ticks && RobotState.isEnabled()) {
	    arcadeDriveStraight(FAST_AUTO_MODE_SPEED);
	    updateDiagnostics();
	}
	stop();
    }

    /**
     * An instance of this internal class is used whenever the driver wants to
     * computer-vision assistance to drive towards a cube. It contains internal
     * state for PID control.
     */
    double KP = -.0075, KD = -.1, KI = -.001;
    double KPCubeVision = -.0075, KDCubeVision = -.1, KICubeVision = -.001;

    public double previousTime = 0, time, integral = 0;
    double previousError = 0;

    public void resetTime() {
	timer.reset();
    }

    public void resetGyro() {
	gyro.reset();
    }

    double derivative;

    /**
     * Drives forward while turning based on vision values
     * 
     * @param speed
     *            Forward speed
     */
    public void driveWithVision(double speed) {
	double error = (Robot.cubeVision.getError());

	// Nessicary to avoid resetting derivative when the frame desn't update fast
	// enough
	if (error != previousError)
	    derivative = (error - previousError) / (CubeVisionThread.FPS);
	integral += error * (CubeVisionThread.FPS);
	double value = KPCubeVision * error + KDCubeVision * derivative + KICubeVision * integral;
	drive.arcadeDrive(-1 * speed, value);
	updateDiagnostics();
	previousError = error;
    }

    /**
     * Resets PID values
     */
    public void prepareDriveToCube() {
	previousTime = 0;
	integral = 0;
	previousError = 0;
	derivative = 0;
	KPCubeVision = .0035;
    }

    /**
     * Drive towards the cube using error adjustment values from the cube vision
     * thread.
     * 
     * WARNING: this method will not stop unless you disable the robot! Not used,
     * this structure has been replaced by the more optimal command based structure
     * (as opposed to the instant commands)
     */
    public void driveToCubeAuto() {
	while (RobotState.isEnabled()) {
	    drive.arcadeDrive(.75, Robot.cubeVision.getError() * -.005);
	    updateDiagnostics();
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
    public void moveDistanceFromWall(double distanceFromWall, boolean goingForward) {
	if (goingForward) {
	    arcadeDriveStraight(SLOW_AUTO_MODE_SPEED);
	    updateDiagnostics();
	} else {
	    arcadeDriveStraight(-1 * SLOW_AUTO_MODE_SPEED);
	    updateDiagnostics();
	}
    }

    /**
     * Move forward the specific number of inches. Not used, this structure has been
     * replaced by the more optimal command based structure (as opposed to the
     * instant commands)
     * 
     * @param inches
     *            Inches to move forward
     * @param speed
     *            A value between 0 and 1
     */
    public void moveForward(double inches, double speed) {
	gyro.reset();
	resetEncoders();

	double ticksRequired = 6.36 * inches * 4;
	while (Math.abs(frontLeft.getSelectedSensorPosition(0)) < ticksRequired && RobotState.isEnabled()) {
	    // double ticksRequired = 6.36*inches
	    // while(Math.abs(rightEncoder.get()) <ticksRequired && RobotState.isEnabled()){
	    arcadeDriveStraight(speed);
	    updateDiagnostics();
	}
	// stop();
    }

    double tolerance, speedThreshold, direction, angle;

    /**
     * Turns the robot based on gyro values and PID calculations
     */
    public void turnWithPid() {
	double error = (gyro.getAngle() - (direction * angle));
	double derivative = gyro.getRate();
	double value = KP * error + KD * derivative;
	if (value > .75)
	    value = .75;
	else if (value < -.75)
	    value = -.75;
	turn(value);
    }

    /**
     * Checks if the PID turn is done based on its current speed and position
     * 
     * @return If the PID turn is done
     */
    public boolean pidTurnDone() {
	return (Math.abs(direction * gyro.getAngle() - angle) < tolerance && Math.abs(gyro.getRate()) < speedThreshold);
    }

    /**
     * Resets the PID turn variables
     * 
     * @param angle
     *            The desired angle to turn to
     * @param direction
     *            The direction to turn left: -1 right: 1
     */
    public void resetPidTurn(double angle, int direction) {
	KP = -.1;// SmartDashboard.getNumber("P", -.1);// -.1;
	KD = -.0225;// -.05;
	tolerance = 4;
	speedThreshold = 20;
	gyro.reset();
	this.direction = direction;
	this.angle = angle;
    }

    /**
     * Turn the given angle and direction with no PID feedback loop.
     * 
     * @param direction
     *            -1 (LEFT), 1 (RIGHT)
     */
    public void turnWithoutPid(int direction) {
	drive.tankDrive(direction * GYRO_TURNING_SPEED, direction * -GYRO_TURNING_SPEED);
    }

    public void turn(double speed) {
	drive.arcadeDrive(0, speed, false);
    }

    /**
     * Stop the robot from moving.
     */
    public void stop() {
	frontLeft.set(0);
	frontRight.set(0);
    }

    /**
     * Turn the given angle and direction with a PID feedback loop. Outdated, left
     * here mainly for reference
     * 
     * @param angle
     *            The angle to turn
     * @param direction
     *            -1 (LEFT), 1 (RIGHT)
     */
    void oldTurnWithPid(double angle, int direction) {
	// shift(FAST_GEAR);
	double tolerance = 2, speedThreshold = 15;
	double KP = -.2, KD = -.05;
	gyro.reset();
	while ((Math.abs(direction * gyro.getAngle() - angle) > tolerance || Math.abs(gyro.getRate()) > speedThreshold)
		&& RobotState.isEnabled()) {
	    double error = (gyro.getAngle() - (direction * angle));
	    double derivative = gyro.getRate();
	    double value = KP * error + KD * derivative;
	    frontLeft.set(value);
	    frontRight.set(value);
	    // } else {
	    // if (value > 0) {
	    // frontLeft.set(.35);
	    // frontRight.set(.35);
	    // } else {
	    // frontLeft.set(-.35);
	    // frontRight.set(-.35);
	    // }
	    // }
	    // SmartDashboard.putNumber("proportional", KP * error);
	    // SmartDashboard.putNumber("derivative", KD * derivative);
	    // SmartDashboard.putNumber("Gyro", gyro.getAngle());
	}
	// SmartDashboard.putString("Using pid", "true");
	stop();
    }

    /**
     * Drives straight with help from gyro.
     * 
     * @param speed
     *            The speed to drive straight.
     */
    public void arcadeDriveStraight(double speed) {
	drive.arcadeDrive(speed, GYRO_COMPENSATION * gyro.getAngle());
    }

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
	return deadBand((-1 * BOOST_SPEED_MULTIPLIER * speed), DEAD_BAND_LIMIT);
    }

    /**
     * Reduce the sensitivity of the joystick input to make tank driving easier. Not
     * used, but a useful concept, runs into problems if the robot requires high
     * power to start moving
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

    /**
     * @return the left encoder value
     */
    public double getLeftEncoder() {
	return frontLeft.getSelectedSensorPosition(0);
    }

    /**
     * @return the right encoder value
     */
    public double getRightEncoder() {
	return frontRight.getSelectedSensorPosition(0);
    }

    /**
     * @return the gear shifter's state high: kForward low: kReverse
     */
    public DoubleSolenoid.Value getGearState() {
	return gearShifter.get();
    }

}
