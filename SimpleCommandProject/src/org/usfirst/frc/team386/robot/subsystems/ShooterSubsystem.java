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
     * Turn on the shooter wheel at the given speed. Using this method will always
     * cause the shooter wheel to turn in the correct direction.
     * 
     * @param speed
     *            Range from 0.0 to 1.0
     */
    public void turnOn(double speed) {
	turnOn(speed, false);
    }

    /**
     * Turn on the shooter wheel at the given speed.
     * 
     * @param speed
     *            Range from 0.0 to 1.0
     * @param reverse
     *            Set to true to reverse the shooter wheel.
     */
    public void turnOn(double speed, boolean reverse) {
	if (reverse) {
	    shooter.setSpeed(speed);
	} else {
	    shooter.setSpeed(-speed);
	}
    }

    /**
     * Set the shooter wheel speed to 0.
     */
    public void turnOff() {
	shooter.setSpeed(0);
    }
}
