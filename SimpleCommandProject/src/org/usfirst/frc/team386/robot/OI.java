package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.StartTriggerCommand;
import org.usfirst.frc.team386.robot.commands.StopTriggerCommand;
import org.usfirst.frc.team386.robot.commands.UpdateObjectTrackerCenterCommand;
import org.usfirst.frc.team386.robot.commands.UpdateObjectTrackerLeftCommand;
import org.usfirst.frc.team386.robot.commands.UpdateObjectTrackerRightCommand;

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

    public Relay decorationLight = new Relay(RobotMap.decorationLight);

    public Button rightJoystickButton1 = new JoystickButton(rightStick, 1);
    public Button rightJoystickButton2 = new JoystickButton(rightStick, 2);
    public Button rightJoystickButton3 = new JoystickButton(rightStick, 3);
    public Button rightJoystickButton4 = new JoystickButton(rightStick, 4);
    public Button rightJoystickButton5 = new JoystickButton(rightStick, 5);

    public Button leftJoystickButton1 = new JoystickButton(leftStick, 1);
    public Button leftJoystickButton2 = new JoystickButton(leftStick, 2);
    public Button leftJoystickButton3 = new JoystickButton(leftStick, 3);
    public Button leftJoystickButton4 = new JoystickButton(leftStick, 4);
    public Button leftJoystickButton5 = new JoystickButton(leftStick, 5);

    public OI() {
	/*
	 * The right joystick trigger button will start the shooter wheel when pressed
	 * and then turn the same shooter wheel off when released.
	 * rightJoystickButton5.whenPressed(new CameraPanRight());
	 * rightJoystickButton3.whenPressed(new CameraCenterCommand());
	 * rightJoystickButton4.whenPressed(new CameraPanLeft());
	 */

	/*
	 * The button labeled 2 on the joystick will run all subsystems needed to shoot
	 * balls when pressed and will disable those subsystems when released.
	 * rightJoystickButton2.whenPressed(new ShootCommand());
	 * rightJoystickButton2.whenReleased(new DoNotShootCommand());
	 */

	/*
	 * The left joystick trigger button will start the feeder wheel when pressed and
	 * turn the same wheel off when released.
	 */
	leftJoystickButton1.whenPressed(new StartTriggerCommand());
	leftJoystickButton1.whenReleased(new StopTriggerCommand());

	leftJoystickButton4.whenPressed(new UpdateObjectTrackerLeftCommand());
	leftJoystickButton3.whenPressed(new UpdateObjectTrackerCenterCommand());
	leftJoystickButton5.whenPressed(new UpdateObjectTrackerRightCommand());
    }
}
