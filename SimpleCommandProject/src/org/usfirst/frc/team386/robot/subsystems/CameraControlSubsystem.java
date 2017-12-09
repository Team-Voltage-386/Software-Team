package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.FollowObjectTrackerCommand;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CameraControlSubsystem extends Subsystem {

    Servo servo = new Servo(RobotMap.cameraServo);

    public void panleft() {
	servo.set(0);
    }

    public void panright() {
	servo.set(1);
    }

    public void center() {
	servo.set(0.5);
    }

    public void initDefaultCommand() {
	setDefaultCommand(new FollowObjectTrackerCommand());

    }
}
