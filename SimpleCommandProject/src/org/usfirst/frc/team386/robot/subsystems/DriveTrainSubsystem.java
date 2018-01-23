package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.TankDriveWithJoysticksCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Subsystem for operating the drive train and controlling vehicle movement.
 */
public class DriveTrainSubsystem extends Subsystem {
    private DifferentialDrive drive;

    public DriveTrainSubsystem() {
	SpeedControllerGroup leftGroup = new SpeedControllerGroup(new Spark(RobotMap.frontLeftMotor),
		new Spark(RobotMap.rearLeftMotor));
	SpeedControllerGroup rightGroup = new SpeedControllerGroup(new Spark(RobotMap.frontRightMotor),
		new Spark(RobotMap.rearRightMotor));
	this.drive = new DifferentialDrive(leftGroup, rightGroup);
    }

    @Override
    public void initDefaultCommand() {
	setDefaultCommand(new TankDriveWithJoysticksCommand());
    }

    /**
     * Control the robot drive with the left and right joysticks in tank mode.
     * 
     * @param left
     *            The left joystick
     * @param right
     *            The right joystick
     */
    public void drive(Joystick left, Joystick right) {
	drive.setSafetyEnabled(true);
	drive.tankDrive(left.getThrottle(), right.getThrottle());
    }

    /**
     * Drive the robot forward at full speed.
     */
    public void driveForward() {
	drive.setSafetyEnabled(true);
	drive.tankDrive(1.0, 1.0);
    }
}
