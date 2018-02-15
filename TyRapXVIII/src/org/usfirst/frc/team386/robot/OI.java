package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.BoostStart;
import org.usfirst.frc.team386.robot.commands.BoostStop;
import org.usfirst.frc.team386.robot.commands.GearShift;
import org.usfirst.frc.team386.robot.commands.SetElevator;
import org.usfirst.frc.team386.robot.commands.teleop.DriveToCube;
import org.usfirst.frc.team386.robot.commands.teleop.LockElevator;

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
    public static Joystick manipulator = new Joystick(3);

    public Button arcadeDriveShiftButton = new JoystickButton(xboxControl, 5);
    public Button arcadeDriveBoostButton = new JoystickButton(xboxControl, 8);
    public Button tankDriveShiftButton = new JoystickButton(leftJoy, 1);
    public Button tankDriveBoostButton = new JoystickButton(rightJoy, 1);
    public Button cubeButton = new JoystickButton(xboxControl, 3);
    public Button arcadeDriveVaultButton = new JoystickButton(xboxControl, 2);
    public Button arcadeDriveLockElevator = new JoystickButton(xboxControl, 1);

    public OI() {
	arcadeDriveShiftButton.whenPressed(new GearShift());
	tankDriveShiftButton.whenPressed(new GearShift());

	arcadeDriveBoostButton.whenPressed(new BoostStart());
	arcadeDriveBoostButton.whenReleased(new BoostStop());

	tankDriveBoostButton.whenPressed(new BoostStart());
	tankDriveBoostButton.whenReleased(new BoostStop());

	cubeButton.whenPressed(new DriveToCube());

	arcadeDriveVaultButton.whenPressed(new SetElevator(200));
	arcadeDriveLockElevator.whenPressed(new LockElevator());
    }
}
