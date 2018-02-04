package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.BoostStart;
import org.usfirst.frc.team386.robot.commands.BoostStop;
import org.usfirst.frc.team386.robot.commands.GearShift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public Joystick xboxControl = new Joystick(2);
    public Joystick leftJoy = new Joystick(0);
    public Joystick rightJoy = new Joystick(1);
    public Joystick manipulator = new Joystick(3);

    public Button gearShiftButton = new JoystickButton(xboxControl, 5);
    public Button boostButton = new JoystickButton(xboxControl, 8);
    public Button tankDriveShiftButton = new JoystickButton(leftJoy, 1);
    public Button tankBoostButton = new JoystickButton(rightJoy, 1);

    public OI() {
	gearShiftButton.whenPressed(new GearShift());
	tankDriveShiftButton.whenPressed(new GearShift());

	boostButton.whenPressed(new BoostStart());
	boostButton.whenReleased(new BoostStop());

	tankBoostButton.whenPressed(new BoostStart());
	tankBoostButton.whenReleased(new BoostStop());
    }
}
