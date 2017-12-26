package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.FollowObjectTrackerCommand;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that controls the camera adjustment motors.
 */
public class CameraControlSubsystem extends Subsystem {
    Servo servo = new Servo(RobotMap.cameraHorizontalServo);

    double lastAngle = 0.0;

    /**
     * Rotate the camera to the left `value` degress.
     * 
     * @param value
     *            The number of degrees to rotate.
     */
    public void rotateLeft(double value) {
	// System.out.println("Starting angle: " + lastAngle);
	double angle = Math.max(servo.getAngle() - value, 0.0);
	// System.out.println("Rotating left to " + angle + " degrees.");
	servo.setAngle(angle);
	// System.out.println("Ending angle: " + servo.getAngle());
	this.lastAngle = angle;
    }

    /**
     * Rotate the camera to the right `value` degrees.
     * 
     * @param value
     *            The number of degrees to rotate.
     */
    public void rotateRight(double value) {
	// System.out.println("Starting angle: " + lastAngle);
	double angle = Math.min(servo.getAngle() + value, 180);
	// System.out.println("Rotating right to " + angle + "degrees.");
	servo.setAngle(angle);
	// System.out.println("Ending angle: " + servo.getAngle());
	this.lastAngle = angle;
    }

    /**
     * Center the servo.
     */
    public void center() {
	servo.set(0.5);
	this.lastAngle = servo.getAngle();
    }

    /**
     * Set the servo to the specific angle.
     * 
     * @param angle
     *            The angle between 0.0 and 180.0
     */
    public void setAngle(double angle) {
	servo.setAngle(angle);
	this.lastAngle = servo.getAngle();
    }

    /**
     * Initialize the default command, which is to follow the object tracker.
     */
    public void initDefaultCommand() {
	setDefaultCommand(new FollowObjectTrackerCommand());
    }
}
