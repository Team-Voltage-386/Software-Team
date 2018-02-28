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

    Spark left = new Spark(RobotMap.leftCubeIntakeMotor);
    Spark right = new Spark(RobotMap.rightCubeIntakeMotor);

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
    }

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	SmartDashboard.putNumber("defaultSpeed", 0);
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
	    left.set(-1 * mainSpeed);
	    right.set(mainSpeed);
	} else {
	    left.set(-1 * leftSpeed);
	    right.set(rightSpeed);
	}
    }

}
