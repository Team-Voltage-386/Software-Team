package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.commands.ArcadeDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    WPI_TalonSRX frontLeft = new WPI_TalonSRX(1); /* device IDs here (1 of 2) */
    WPI_TalonSRX frontRight = new WPI_TalonSRX(4);

    /* extra talons for six motor drives */
    WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(2);
    WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(5);
    WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(3);
    WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(6);
    DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);

    public DriveSubsystem() {
	leftSlave1.follow(frontLeft);
	leftSlave2.follow(frontLeft);
	rightSlave1.follow(frontRight);
	rightSlave2.follow(frontRight);
	frontRight.configContinuousCurrentLimit(20, 0);
	frontLeft.configContinuousCurrentLimit(20, 0);
	frontRight.enableCurrentLimit(true);
	frontLeft.enableCurrentLimit(true);

	frontRight.configOpenloopRamp(.1, 0);
	frontLeft.configOpenloopRamp(.11, 0);
    }

    public void drive(double xSpeed, double zRotation) {
	drive.arcadeDrive(xSpeed, zRotation);
	// drive.arcadeDrive(deadBand(-1*manipulator.getRawAxis(1), .1),
	// deadBand(manipulator.getRawAxis(4), .1));
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	setDefaultCommand(new ArcadeDrive());
    }
}
