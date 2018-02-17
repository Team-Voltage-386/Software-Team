package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.BoostStart;
import org.usfirst.frc.team386.robot.commands.BoostStop;
import org.usfirst.frc.team386.robot.commands.GearShift;
import org.usfirst.frc.team386.robot.commands.InstPrepForClimb;
import org.usfirst.frc.team386.robot.commands.SetElevator;
import org.usfirst.frc.team386.robot.commands.teleop.CubeWithUltrasonics;
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
    public Joystick xboxControl = new Joystick(RobotMap.driverPort);
    // public Joystick leftJoy = new Joystick(0);
    // public Joystick rightJoy = new Joystick(1);
    public Joystick manipulator = new Joystick(RobotMap.manipulatorPort);

    // driver buttons
    public Button arcadeDriveShiftButton = new JoystickButton(xboxControl, RobotMap.shiftButton);
    public Button arcadeDriveBoostButton = new JoystickButton(xboxControl, RobotMap.boostButton);
    // public Button tankDriveShiftButton = new JoystickButton(leftJoy, 1);
    // public Button tankDriveBoostButton = new JoystickButton(rightJoy, 1);

    // manipulator buttons
    public Button driveToCubeButton = new JoystickButton(xboxControl, RobotMap.driveToCubeButton);
    public Button arcadeDriveVaultButton = new JoystickButton(manipulator, RobotMap.elevatorToExchange);
    public Button arcadeDriveLockElevator = new JoystickButton(manipulator, RobotMap.chainBreakButton);
    public Button autoCubeIntakeButton = new JoystickButton(manipulator, RobotMap.autoCubeIntakeButton);
    public Button prepForClimbButton = new JoystickButton(manipulator, RobotMap.prepForClimbButton);

    public OI() {
	arcadeDriveShiftButton.whenPressed(new GearShift());
	// tankDriveShiftButton.whenPressed(new GearShift());

	arcadeDriveBoostButton.whenPressed(new BoostStart());
	arcadeDriveBoostButton.whenReleased(new BoostStop());

	// tankDriveBoostButton.whenPressed(new BoostStart());
	// tankDriveBoostButton.whenReleased(new BoostStop());

	driveToCubeButton.whenPressed(new DriveToCube());

	arcadeDriveVaultButton.whenPressed(new SetElevator(200));
	arcadeDriveLockElevator.whenPressed(new LockElevator());

	autoCubeIntakeButton.whenPressed(new CubeWithUltrasonics());

	prepForClimbButton.whenPressed(new InstPrepForClimb());
    }
}
