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

    /**
     * Rotate the camera to the left `value` degress.
     * 
     * @param value
     *            The number of degrees to rotate.
     */
    public void rotateLeft(double value) {
	double angle = servo.getAngle() - value;
	servo.setAngle(Math.max(angle, 0));
    }

    /**
     * Rotate the camera to the right `value` degrees.
     * 
     * @param value
     *            The number of degrees to rotate.
     */
    public void rotateRight(double value) {
	double angle = servo.getAngle() + value;
	servo.setAngle(Math.min(angle, 180));
    }

    /**
     * Center the servo.
     */
    public void center() {
	servo.set(0.5);
    }

    /**
     * Initialize the default command, which is to follow the object tracker.
     */
    public void initDefaultCommand() {
	setDefaultCommand(new FollowObjectTrackerCommand());
    }
}
