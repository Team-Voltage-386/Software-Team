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
    AnalogUltrasonic ultra1 = new AnalogUltrasonic(0, 1.18, 10.3);
    AnalogUltrasonic ultra2 = new AnalogUltrasonic(1, 1.18, 10.3);

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
	left.set(leftSpeed);
	right.set(rightSpeed);
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
