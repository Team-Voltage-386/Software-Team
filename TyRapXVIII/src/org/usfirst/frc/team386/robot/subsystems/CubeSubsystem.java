package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.AnalogUltrasonic;
import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.teleop.CubeManual;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The CubeSubsystem is responsible cube intake and cube release.
 */
public class CubeSubsystem extends Subsystem {
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

    public void runWithPOV(int pov) {
	double cubeSpeed = 0.5;
	SmartDashboard.putNumber("POV number", pov);
	if (Robot.oi.xboxControl.getPOV() == 0) {
	    cubeOut(cubeSpeed);
	    SmartDashboard.putString("Cube Control", "Cube Out");
	} else if (Robot.oi.xboxControl.getPOV() == 90) {
	    twistRight(cubeSpeed);
	    SmartDashboard.putString("Cube Control", "Twist Right");
	} else if (Robot.oi.xboxControl.getPOV() == 180) {
	    cubeIn(cubeSpeed);
	    SmartDashboard.putString("Cube Control", "Cube In");
	} else if (Robot.oi.xboxControl.getPOV() == 270) {
	    twistLeft(cubeSpeed);
	    SmartDashboard.putString("Cube Control", "Twist Left");
	}
    }
}
