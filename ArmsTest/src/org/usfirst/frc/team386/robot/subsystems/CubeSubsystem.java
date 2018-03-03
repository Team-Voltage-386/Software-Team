package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The CubeSubsystem is responsible cube intake and cube release.
 */
public class CubeSubsystem extends Subsystem {

    public static final String POV_NUMBER_LABEL = "POV value";
    public static final String CUBE_CONTROL_LABEL = "Cube Control";
    public static final int CURRENT_LIMIT = 7;
    public static final int TIMEOUT = 200;
    double diagLeft;
    double diagRight;

    // Spark left = new Spark(RobotMap.leftCubeIntakeMotor);
    // Spark right = new Spark(RobotMap.rightCubeIntakeMotor);
    WPI_TalonSRX left = new WPI_TalonSRX(RobotMap.leftCubeIntakeMotor);
    WPI_TalonSRX right = new WPI_TalonSRX(RobotMap.rightCubeIntakeMotor);

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public CubeSubsystem() {
	left.configContinuousCurrentLimit(CURRENT_LIMIT, TIMEOUT);
	right.configContinuousCurrentLimit(CURRENT_LIMIT, TIMEOUT);
	left.enableCurrentLimit(true);
	right.enableCurrentLimit(true);
    }

    public void updateDiagnostics() {
	SmartDashboard.putNumber("Right Stick", Robot.oi.manipulator.getRawAxis(RobotMap.manipRightJoystickVertical));
	SmartDashboard.putNumber("Left Stick", Robot.oi.manipulator.getRawAxis(RobotMap.manipLeftJoystickVertical));
	SmartDashboard.putNumber("Left Speed", diagLeft);
	SmartDashboard.putNumber("Right Speed", diagRight);
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
    }

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	SmartDashboard.putNumber("defaultSpeed", .03);
	setDefaultCommand(new org.usfirst.frc.team386.robot.commands.CubeWithTrigger());
    }

    public void stop() {
	left.set(0);
	right.set(0);
    }

    public void run(double leftSpeed, double rightSpeed) {
	// left.set(leftSpeed);
	// right.set(rightSpeed);
    }

    public void symetricalCube(double speed) {
	// left.set(-1 * speed);
	// right.set(speed);
    }

    public void runCombined(double mainSpeed, double leftSpeed, double rightSpeed) {
	diagLeft = leftSpeed;
	diagRight = rightSpeed;
	left.set(leftSpeed);
	right.set(-1 * rightSpeed);
	/*
	 * if (Math.abs(leftSpeed) < .1 && Math.abs(rightSpeed) < .1 &&
	 * Math.abs(mainSpeed) < .1) { diagLeft = -1 *
	 * SmartDashboard.getNumber("defaultSpeed", 0); diagRight =
	 * SmartDashboard.getNumber("proportion", 1) *
	 * SmartDashboard.getNumber("defaultSpeed", 0); left.set(diagLeft);
	 * right.set(diagRight); } else if (Math.abs(leftSpeed) < .1 &&
	 * Math.abs(rightSpeed) < .1) { diagLeft = (-1 * mainSpeed * mainSpeed *
	 * mainSpeed); diagRight = (mainSpeed * mainSpeed * mainSpeed);
	 * left.set(diagLeft); right.set(diagRight); } else { diagLeft = (-1 * leftSpeed
	 * * leftSpeed * leftSpeed); diagRight = (rightSpeed * rightSpeed * rightSpeed);
	 * left.set(diagLeft); right.set(diagRight); }
	 */
    }

}
