package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.TiltDetection;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TiltSubsystem extends Subsystem {

    public static PigeonIMU pigeon = new PigeonIMU(0);
    Timer timer = new Timer();

    public void initDefaultCommand() {
	setDefaultCommand(new TiltDetection());
    }

    public void tiltForward() {
	SmartDashboard.putString("Robot tilt", "forwards");
	while (pitch() < -1 * pitchLeeway / 2 && RobotState.isEnabled()) {
	    Robot.driveSubsystem.drive.tankDrive(.5, .5);
	}
    }

    public void tiltBackwards() {
	SmartDashboard.putString("Robot tilt", "backwards");
	while (pitch() > pitchLeeway / 2 && RobotState.isEnabled()) {
	    Robot.driveSubsystem.drive.tankDrive(-.5, -.5);
	}
    }

    public double pitchLeeway = 10;

    public double pitch() {
	double[] pig = new double[3];
	pigeon.getYawPitchRoll(pig);
	return pig[1];
    }
}
