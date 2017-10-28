package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Represents the large shooter wheel that launches balls.
 */
public class ShooterSubsystem extends Subsystem {

    private Spark shooter = new Spark(RobotMap.shooterMotor);

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Turn on the shooter wheel at the given speed.
     * 
     * @param speed
     *            Range from -1.0 to 1.0
     */
    public void turnOnShooterWheel(double speed) {
	shooter.setSpeed(speed);
    }

    /**
     * Set the shooter wheel speed to 0.
     */
    public void turnOffShooterWheel() {
	shooter.setSpeed(0);
    }
}
