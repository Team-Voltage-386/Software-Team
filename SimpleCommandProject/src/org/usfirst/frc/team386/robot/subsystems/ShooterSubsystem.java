package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {

    private Spark shooter = new Spark(RobotMap.shooterMotor);

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
    }

    public void turnOnShooterWheel(double speed) {
	shooter.setSpeed(speed);
    }

    public void turnOffShooterWheel() {
	shooter.setSpeed(0);
    }
}
