package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.AnalogUltrasonic;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.teleop.CubeManual;
import org.usfirst.frc.team386.robot.commands.teleop.CubeManualWithPad;

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
	SmartDashboard.putNumber("Analog Ultra 0", ultraCenter.getInches());
	SmartDashboard.putNumber("Analog Ultrasonic 1", ultraEdge.getInches());
    }

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	setDefaultCommand(new CubeManual());
    }

    public void cubeIn(double speed) {
	left.set(speed * -1);
	right.set(speed * -1);
    }

    public void cubeOut(double speed) {
	left.set(speed);
	right.set(speed);
    }

    public void twistLeft(double speed) {
	left.set(-speed);
	right.set(speed);
    }

    public void twistRight(double speed) {
	left.set(speed);
	right.set(-speed);
    }

    public void stop() {
	left.set(0);
	right.set(0);
    }

    public void run(double leftSpeed, double rightSpeed) {
	// left.set(leftSpeed);
	// right.set(rightSpeed);
	double difference = ultraCenter.getInches() - ultraEdge.getInches();
	if (difference > 2) {
	    left.set(SmartDashboard.getNumber("left fast", .5));
	    right.set(SmartDashboard.getNumber("right slow", -.5));
	} else if (-2 < difference) {
	    left.set(SmartDashboard.getNumber("left slow", -.5));
	    right.set(SmartDashboard.getNumber("right fast", .5));
	} else if (difference < -2 || difference < 2) {
	    left.set(leftSpeed);
	    right.set(rightSpeed);
	}
    }

    /**
     * Use the POV pad value to determine what action to take on the cube control.
     * 
     * @param pov
     *            The current POV (pad) value
     */
    public void runWithPOV(int pov) {
	double cubeSpeed = 0.5;
	SmartDashboard.putNumber(POV_NUMBER_LABEL, pov);
	if (pov == 0) {
	    cubeOut(cubeSpeed);
	    SmartDashboard.putString(CUBE_CONTROL_LABEL, "Cube Out");
	} else if (pov == 90) {
	    twistRight(cubeSpeed);
	    SmartDashboard.putString(CUBE_CONTROL_LABEL, "Twist Right");
	} else if (pov == 180) {
	    cubeIn(cubeSpeed);
	    SmartDashboard.putString(CUBE_CONTROL_LABEL, "Cube In");
	} else if (pov == 270) {
	    twistLeft(cubeSpeed);
	    SmartDashboard.putString(CUBE_CONTROL_LABEL, "Twist Left");
	}
    }

    /**
     * Set to true to use the xbox joysticks to control the cube intake, false to
     * use the pad.
     * 
     * @param useJoystick
     *            True to use the xbox joysticks, false for the pad
     */
    public void setCubeControlMode(boolean useJoystick) {
	if (useJoystick) {
	    setDefaultCommand(new CubeManual());
	} else {
	    setDefaultCommand(new CubeManualWithPad());
	}
    }
}
