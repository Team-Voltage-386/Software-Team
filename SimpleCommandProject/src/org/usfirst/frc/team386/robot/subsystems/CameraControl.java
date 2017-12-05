package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.commands.FollowObjectTracker;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CameraControl extends Subsystem {

    Servo servo = new Servo(4);

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
	setDefaultCommand(new FollowObjectTracker());

    }
}
