package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.GearShift;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public Joystick manipulator = new Joystick(2);
    public Joystick leftJoy = new Joystick(0);
    public Joystick rightJoy = new Joystick(1);
    public Button gearShiftButton = new JoystickButton(manipulator, 5);

    public static ADXRS450_Gyro gyro;
    static {
	try {
	    gyro = new ADXRS450_Gyro();
	} catch (NoClassDefFoundError e) {
	    throw e;
	}
    }

    public OI() {
	gearShiftButton.whenPressed(new GearShift());
	SmartDashboard.putBoolean("Drive mode, if false = tank, if true = arcade", true);
    }
}
