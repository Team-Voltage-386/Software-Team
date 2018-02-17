package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.AnalogUltrasonic;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.teleop.CubeManual;

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
	setDefaultCommand(new CubeManual());
    }

    public void stop() {
	left.set(0);
	right.set(0);
    }

    public void run(double leftSpeed, double rightSpeed) {
	left.set(leftSpeed);
	right.set(rightSpeed);
    }

    public void runWithUltrasonics() {
	double difference = ultraCenter.getInches() - ultraEdge.getInches();
	if (difference > 2) {
	    left.set(SmartDashboard.getNumber("left fast", .5));
	    right.set(SmartDashboard.getNumber("right slow", -.5));
	} else if (-2 < difference) {
	    left.set(SmartDashboard.getNumber("left slow", -.5));
	    right.set(SmartDashboard.getNumber("right fast", .5));
	} else if (difference < -2 || difference < 2) {
	    left.set(SmartDashboard.getNumber("left fast", .5));
	    right.set(SmartDashboard.getNumber("right fast", .5));
	}
    }
}
