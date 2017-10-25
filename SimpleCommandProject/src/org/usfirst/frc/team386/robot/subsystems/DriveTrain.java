package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.TankDriveWithJoysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	
	private RobotDrive drive = new RobotDrive(RobotMap.frontLeftMotor, RobotMap.rearLeftMotor, RobotMap.frontRightMotor, RobotMap.rearRightMotor);

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new TankDriveWithJoysticks());
	}
	public void drive (Joystick left, Joystick right) {
		drive.setSafetyEnabled(true);
		System.out.println("Invoking drive function within driveTrain subsystem");
		
		drive.tankDrive(left, right);
		
	}
}

