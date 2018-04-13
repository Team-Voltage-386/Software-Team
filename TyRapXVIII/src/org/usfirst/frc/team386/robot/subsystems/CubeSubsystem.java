package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.AnalogUltrasonic;
import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.teleop.CubeWithTrigger;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The CubeSubsystem is responsible cube intake and cube release.
 */
public class CubeSubsystem extends Subsystem {

    public static final String POV_NUMBER_LABEL = "POV value";
    public static final String CUBE_CONTROL_LABEL = "Cube Control";
    final static double halfSpeedEject = -.3;
    // TESTBOT CHANGES
    /*
     * WPI_TalonSRX left = new WPI_TalonSRX(RobotMap.leftCubeIntakeMotor);
     * WPI_TalonSRX right = new WPI_TalonSRX(RobotMap.rightCubeIntakeMotor);
     */
    Spark left = new Spark(2);
    Spark right = new Spark(3);
    // ^^^^ END OF TESTBOT CHANGES

    AnalogUltrasonic ultraCenter = new AnalogUltrasonic(RobotMap.cubeUltrasonicCenter, 1.18, 10.3);
    AnalogUltrasonic ultraEdge = new AnalogUltrasonic(RobotMap.cubeUltrasonicEdge, 1.18, 10.3);

    public CubeSubsystem() {
	// TESTBOT CHANGES
	/*
	 * final int kPeakCurrentAmps = 7; // threshold to trigger current limit final
	 * int kPeakTimeMs = 0; // how long after Peak current to trigger current limit
	 * final int kContinCurrentAmps = 4; // hold current after limit is triggered
	 * 
	 * left.configPeakCurrentLimit(kPeakCurrentAmps, 10);
	 * left.configPeakCurrentDuration(kPeakTimeMs, 10); // this is a necessary call
	 * to avoid errata. left.configContinuousCurrentLimit(kContinCurrentAmps, 10);
	 * left.enableCurrentLimit(true); // honor initial setting
	 * 
	 * right.configPeakCurrentLimit(kPeakCurrentAmps, 10);
	 * right.configPeakCurrentDuration(kPeakTimeMs, 10); // this is a necessary call
	 * to avoid errata. right.configContinuousCurrentLimit(kContinCurrentAmps, 10);
	 * right.enableCurrentLimit(true); // honor initial setting
	 */
	// ^^ END OF TESTBOT CHANGESs
	// left.configClosedloopRamp(.1, 100);
	// right.configClosedloopRamp(.1, 100);
    }

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
	SmartDashboard.putNumber("Right motor value", right.get());
	SmartDashboard.putNumber("Left motor value", left.get());
    }

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// SmartDashboard.putNumber("defaultSpeed", 0);
	setDefaultCommand(new CubeWithTrigger());
    }

    public void stop() {
	left.set(0);
	right.set(0);
    }

    public void run(double leftSpeed, double rightSpeed) {
	left.set(leftSpeed);
	right.set(rightSpeed);
    }

    public void symetricalCube(double speed) {
	left.set(speed);
	right.set(-1 * speed);
    }

    public void runCombined(double mainSpeed, double leftSpeed, double rightSpeed) {
	if (Math.abs(leftSpeed) < .1 && Math.abs(rightSpeed) < .1 && Math.abs(mainSpeed) < .1
		&& !Robot.oi.manipulator.getRawButton(RobotMap.halfSpeedEject)) {
	    left.set(.4);// * SmartDashboard.getNumber("proportion", 1)
			 // SmartDashboard.getNumber("defaultSpeed", 0)
	    right.set(-1 * .4);
	    // SmartDashboard.putString("Status", "default");
	} else if (Robot.oi.manipulator.getRawButton(RobotMap.halfSpeedEject)) {
	    left.set(halfSpeedEject);
	    right.set(-1 * halfSpeedEject);
	    // SmartDashboard.putString("Status", "eject");
	} else if (Math.abs(leftSpeed) < .1 && Math.abs(rightSpeed) < .1) {
	    left.set(mainSpeed);
	    right.set(-1 * mainSpeed);// -1*
	    // SmartDashboard.putString("Status", "triggers");
	    // SmartDashboard.putNumber("mainSpeed", mainSpeed);
	} else {
	    // SmartDashboard.putNumber("leftSpeed", leftSpeed);
	    // SmartDashboard.putNumber("rightSpeed", rightSpeed);
	    left.set(leftSpeed);
	    right.set(-1 * rightSpeed);
	    // SmartDashboard.putString("Status", "joy");
	}
    }

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

    public boolean hasCube() {
	return ultraCenter.getInches() < 2 && ultraEdge.getInches() < 2;
    }
}
