package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.DoNotShootCommand;
import org.usfirst.frc.team386.robot.commands.ShootCommand;
import org.usfirst.frc.team386.robot.commands.StartShooterCommand;
import org.usfirst.frc.team386.robot.commands.StartTriggerCommand;
import org.usfirst.frc.team386.robot.commands.StopShooterCommand;
import org.usfirst.frc.team386.robot.commands.StopTriggerCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public Joystick leftStick = new Joystick(0); // set to ID 1 in DriverStation
    public Joystick rightStick = new Joystick(1); // set to ID 2 in DriverStation
    Relay decorationLight = new Relay(2);

    public Button rightTriggerButton = new JoystickButton(rightStick, 1);
    public Button rightButtonNumber2 = new JoystickButton(rightStick, 2);
    public Button leftTriggerButton = new JoystickButton(leftStick, 1);

    public OI() {
	rightTriggerButton.whenPressed(new StartShooterCommand());
	rightTriggerButton.whenReleased(new StopShooterCommand());

	rightButtonNumber2.whenPressed(new ShootCommand());
	rightButtonNumber2.whenReleased(new DoNotShootCommand());

	leftTriggerButton.whenPressed(new StartTriggerCommand());
	leftTriggerButton.whenReleased(new StopTriggerCommand());
    }
}
