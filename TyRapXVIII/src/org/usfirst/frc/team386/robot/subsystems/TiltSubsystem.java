package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.TiltDetection;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The TiltSubsystem monitors the tilt values from the Pigeon IMU and attempts
 * to auto-correct by driving in the appropriate direction.
 */
public class TiltSubsystem extends Subsystem {

    public double pitchLeeway = 10;

    PigeonIMU pigeon = new PigeonIMU(RobotMap.pigeon);

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

    public double pitch() {
	double[] pig = new double[3];
	pigeon.getYawPitchRoll(pig);
	return pig[1];
    }
}
