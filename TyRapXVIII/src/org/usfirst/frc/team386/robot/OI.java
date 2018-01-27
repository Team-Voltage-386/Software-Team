package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.GearShift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public Joystick manipulator = new Joystick(2);
    public Button gearShiftButton = new JoystickButton(manipulator, 5);

    public OI() {
	gearShiftButton.whenPressed(new GearShift());
    }
}
