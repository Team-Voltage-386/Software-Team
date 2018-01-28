
package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.DriveForwardSomeDistance;
import org.usfirst.frc.team386.robot.subsystems.CubeSubsystem;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team386.robot.subsystems.ElevatorSubsystem;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static final CubeSubsystem cubeSubsystem = new CubeSubsystem();
    public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
    public static final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();

    public static OI oi;

    Command autonomousCommand = new DriveForwardSomeDistance();
    SendableChooser<Command> chooser = new SendableChooser<>();
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();

    public static final String DRIVE_MODE_KEY = "Arcade drive?";

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
	oi = new OI();
	chooser.addDefault("Default Auto", new DriveForwardSomeDistance());
	// chooser.addObject("My Auto", new MyAutoCommand());
	SmartDashboard.putData("Auto mode", chooser);
	SmartDashboard.putBoolean(DRIVE_MODE_KEY, true);
    }

    /**
     * This function is called once each time the robot enters Disabled mode. You
     * can use it to reset any subsystem information you want to clear when the
     * robot is disabled.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
	Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable chooser
     * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
     * remove all of the chooser code and uncomment the getString code to get the
     * auto name from the text box below the Gyro
     *
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons to
     * the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
	autonomousCommand = chooser.getSelected();
	driveSubsystem.resetEncoders();

	/*
	 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
	 * switch(autoSelected) { case "My Auto": autonomousCommand = new
	 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
	 * ExampleCommand(); break; }
	 */

	// schedule the autonomous command (example)
	if (autonomousCommand != null)
	    autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
	Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
	Robot.driveSubsystem.setDriveMode(SmartDashboard.getBoolean(DRIVE_MODE_KEY, true));

	// This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to
	// continue until interrupted by another command, remove
	// this line or comment it out.
	if (autonomousCommand != null)
	    autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
	Scheduler.getInstance().run();

    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {

    }
}
