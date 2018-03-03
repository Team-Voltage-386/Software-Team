package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The CubeSubsystem is responsible cube intake and cube release.
 */
public class CubeSubsystem extends Subsystem {

    public static final String POV_NUMBER_LABEL = "POV value";
    public static final String CUBE_CONTROL_LABEL = "Cube Control";
    public static final int CURRENT_LIMIT = 3;
    public static final int TIMEOUT = 200;

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
	left.set(leftSpeed);
	right.set(rightSpeed);
    }

    public void symetricalCube(double speed) {
	left.set(-1 * speed);
	right.set(speed);
    }

    public void runCombined(double mainSpeed, double leftSpeed, double rightSpeed) {
	if (Math.abs(leftSpeed) < .1 && Math.abs(rightSpeed) < .1 && Math.abs(mainSpeed) < .1) {
	    left.set(-1 * SmartDashboard.getNumber("defaultSpeed", 0));
	    right.set(SmartDashboard.getNumber("proportion", 1) * SmartDashboard.getNumber("defaultSpeed", 0));
	} else if (Math.abs(leftSpeed) < .1 && Math.abs(rightSpeed) < .1) {
	    left.set(-1 * mainSpeed * mainSpeed * mainSpeed);
	    right.set(mainSpeed * mainSpeed * mainSpeed);
	} else {
	    left.set(-1 * leftSpeed * leftSpeed * leftSpeed);
	    right.set(rightSpeed * rightSpeed * rightSpeed);
	}
    }

}
