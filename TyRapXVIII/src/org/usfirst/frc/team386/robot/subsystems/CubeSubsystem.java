package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.AnalogUltrasonic;
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

    Spark left = new Spark(RobotMap.leftCubeIntakeMotor);
    Spark right = new Spark(RobotMap.rightCubeIntakeMotor);
    AnalogUltrasonic ultraCenter = new AnalogUltrasonic(RobotMap.cubeUltrasonicCenter, 1.18, 10.3);
    AnalogUltrasonic ultraEdge = new AnalogUltrasonic(RobotMap.cubeUltrasonicEdge, 1.18, 10.3);

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
	SmartDashboard.putNumber("Analog Ultra Center", ultraCenter.getInches());
	SmartDashboard.putNumber("Analog Ultra Edge", ultraEdge.getInches());
    }

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new CubeManual());
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
	left.set(-1 * speed);
	right.set(speed);
    }

    public void runCombined(double mainSpeed, double leftSpeed, double rightSpeed) {
	if (Math.abs(leftSpeed) < .1 && Math.abs(rightSpeed) < .1) {
	    left.set(-1 * mainSpeed);
	    right.set(mainSpeed);
	} else {
	    left.set(-1 * leftSpeed);
	    right.set(rightSpeed);
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
}
