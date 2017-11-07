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

    public Button rightJoystickButton1 = new JoystickButton(rightStick, 1);
    public Button rightJoystickButton2 = new JoystickButton(rightStick, 2);
    public Button leftJoystickButton1 = new JoystickButton(leftStick, 1);
    public Button leftJoystickButton2 = new JoystickButton(leftStick, 2);

    public OI() {
	// The right joystick trigger button will start the shooter wheel when pressed
	// and then turn the same shooter wheel off when released.
	rightJoystickButton1.whenPressed(new StartShooterCommand());
	rightJoystickButton1.whenReleased(new StopShooterCommand());

	// The button labeled 2 on the joystick will run all subsystems needed to shoot
	// balls when pressed and will disable those subsystems when released.
	rightJoystickButton2.whenPressed(new ShootCommand());
	rightJoystickButton2.whenReleased(new DoNotShootCommand());

	// The left joystick trigger button will start the feeder wheel when pressed and
	// turn the same wheel off when released.
	leftJoystickButton1.whenPressed(new StartTriggerCommand());
	leftJoystickButton1.whenReleased(new StopTriggerCommand());
    }
}
