package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.TankDriveWithJoysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private RobotDrive drive = new RobotDrive(RobotMap.frontLeftMotor, RobotMap.rearLeftMotor, RobotMap.frontRightMotor,
	    RobotMap.rearRightMotor);

    @Override
    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
	setDefaultCommand(new TankDriveWithJoysticks());
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
	drive.tankDrive(left, right);

    }

    /**
     * Drive the robot forward at full speed.
     */
    public void driveForward() {
	drive.tankDrive(1.0, 1.0);
    }
}
