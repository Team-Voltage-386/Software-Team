
package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.TankDriveWithJoysticks;
import org.usfirst.frc.team386.robot.subsystems.AgitatorSubsystem;
import org.usfirst.frc.team386.robot.subsystems.CameraControl;
import org.usfirst.frc.team386.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team386.robot.subsystems.FeedSubsystem;
import org.usfirst.frc.team386.robot.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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

    public static final DriveTrainSubsystem driveTrain = new DriveTrainSubsystem();
    public static final ShooterSubsystem shootSubsystem = new ShooterSubsystem();
    public static final FeedSubsystem feedSubsystem = new FeedSubsystem();
    public static final AgitatorSubsystem agitatorSubsystem = new AgitatorSubsystem();
    public static final CameraControl cameracontrol = new CameraControl();

    public static OI oi;

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
	oi = new OI();
	chooser.addDefault("Default Auto", new TankDriveWithJoysticks());
	// chooser.addObject("My Auto", new MyAutoCommand());
	SmartDashboard.putData("Auto mode", chooser);
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

	/*
	 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
	 * switch(autoSelected) { case "My Auto": autonomousCommand = new
	 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
	 * TankDriveWithJoysticks(); break; }
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
	// Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
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
	LiveWindow.run();
    }
}
