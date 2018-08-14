package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.AnalogUltrasonic;
import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.teleop.CubeWithTrigger;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The CubeSubsystem is responsible cube intake and cube release.
 */
public class CubeSubsystem extends Subsystem {

    public static final String POV_NUMBER_LABEL = "POV value";
    public static final String CUBE_CONTROL_LABEL = "Cube Control";
    final static double halfSpeedEject = -.3;
    public final double DEFAULT_SPEED = .4;

    WPI_TalonSRX left = new WPI_TalonSRX(RobotMap.leftCubeIntakeMotor);
    WPI_TalonSRX right = new WPI_TalonSRX(RobotMap.rightCubeIntakeMotor);

    AnalogUltrasonic ultraCenter = new AnalogUltrasonic(RobotMap.cubeUltrasonicCenter, 1.18, 10.3);
    AnalogUltrasonic ultraEdge = new AnalogUltrasonic(RobotMap.cubeUltrasonicEdge, 1.18, 10.3);

    public CubeSubsystem() {

	final int kPeakCurrentAmps = 7; // threshold to trigger current limit final
	int kPeakTimeMs = 0; // how long after Peak current to trigger current limit
	final int kContinCurrentAmps = 4; // hold current after limit is triggered

	left.configPeakCurrentLimit(kPeakCurrentAmps, 10);
	left.configPeakCurrentDuration(kPeakTimeMs, 10); // this is a necessary call to avoid errata.
	left.configContinuousCurrentLimit(kContinCurrentAmps, 10);
	left.enableCurrentLimit(true); // honor initial setting

	right.configPeakCurrentLimit(kPeakCurrentAmps, 10);
	right.configPeakCurrentDuration(kPeakTimeMs, 10); // this is a necessary callto avoid errata.
	right.configContinuousCurrentLimit(kContinCurrentAmps, 10);
	right.enableCurrentLimit(true); // honor initial setting
	left.configClosedloopRamp(.1, 100);
	right.configClosedloopRamp(.1, 100);
    }

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
	// SmartDashboard.putNumber("Right motor value", right.get());
	// SmartDashboard.putNumber("Left motor value", left.get());
    }

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// SmartDashboard.putNumber("defaultSpeed", 0);
	setDefaultCommand(new CubeWithTrigger());
    }

    /**
     * Sets intake to the default speed
     */
    public void stop() {
	left.set(DEFAULT_SPEED);
	right.set(-1 * DEFAULT_SPEED);
    }

    /**
     * Directly sets the speed of the intake motors
     * 
     * @param leftSpeed
     *            The speed for the left motor
     * @param rightSpeed
     *            The speed for the right motor
     */
    public void run(double leftSpeed, double rightSpeed) {
	left.set(leftSpeed);
	right.set(rightSpeed);
    }

    /**
     * Sets both motors to the same speed
     * 
     * @param speed
     *            The speed to set both motors to
     */
    public void symetricalCube(double speed) {
	left.set(speed);
	right.set(-1 * speed);
    }

    /**
     * Symetrical cube that overrides to run if the left or right value is high
     * enough, used for manual control
     * 
     * @param mainSpeed
     *            The speed to set both motors to if left and right are to small
     * @param leftSpeed
     *            The speed to set the left motor to
     * @param rightSpeed
     *            The speed to set the right motor to
     */
    public void runCombined(double mainSpeed, double leftSpeed, double rightSpeed) {
	if (Math.abs(leftSpeed) < .1 && Math.abs(rightSpeed) < .1 && Math.abs(mainSpeed) < .1
		&& !Robot.oi.manipulator.getRawButton(RobotMap.halfSpeedEject)) {
	    left.set(DEFAULT_SPEED);
	    right.set(-1 * DEFAULT_SPEED);
	} else if (Robot.oi.manipulator.getRawButton(RobotMap.halfSpeedEject)) {
	    left.set(halfSpeedEject);
	    right.set(-1 * halfSpeedEject);
	} else if (Math.abs(leftSpeed) < .1 && Math.abs(rightSpeed) < .1) {
	    left.set(mainSpeed);
	    right.set(-1 * mainSpeed);
	} else {
	    left.set(leftSpeed);
	    right.set(-1 * rightSpeed);
	}
    }

    /**
     * Runs the intake with speed differentials based on ultrasonic sensors. Not
     * used
     * 
     * @param speed
     *            the base speed for both motors
     */
    public void runWithUltrasonics(double speed) {
	double difference = ultraCenter.getInches() - ultraEdge.getInches();
	if (difference > 2) {
	    left.set(speed);
	    right.set(speed);
	} else if (-2 < difference) {
	    left.set(-1 * speed);
	    right.set(-1 * speed);
	} else if (difference < -2 || difference < 2) {
	    left.set(speed);
	    right.set(-1 * speed);
	}
    }

    /**
     * Uses ultrasonics to detect cube
     * 
     * @return If there's a cube in the intake
     */
    public boolean hasCube() {
	return ultraCenter.getInches() < 4 && ultraEdge.getInches() < 4;
    }
}
