
package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.DriveForwardToLine;
import org.usfirst.frc.team386.robot.commands.ReverseTillSensed;
import org.usfirst.frc.team386.robot.commands.Stop;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;
import org.usfirst.frc.team386.robot.commands.auto.CenterSwitchAuto;
import org.usfirst.frc.team386.robot.commands.auto.LeftScaleAuto;
import org.usfirst.frc.team386.robot.commands.auto.LeftSwitchAuto;
import org.usfirst.frc.team386.robot.commands.auto.RightScaleAuto;
import org.usfirst.frc.team386.robot.commands.auto.RightSwitchAuto;
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
    public static GameData gameData;

    Command autonomousCommand = new Stop();
    SendableChooser<Command> chooser = new SendableChooser<>();
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();

    // Game data configurations
    public static final String LLL = "LLL";
    public static final String LRL = "LRL";
    public static final String RRR = "RRR";
    public static final String RLR = "RLR";

    // Settings
    public static final String DRIVE_MODE_LABEL = "Arcade drive?";
    public static final String TURN_WITH_PID_LABEL = "Turn with PID?";

    // Encoder and sensor labels
    public static final String LEFT_DRIVE_ENCODER = "Left encoder";
    public static final String RIGHT_DRIVE_ENCODER = "Right encoder";
    public static final String LINE_SENSOR = "Line sensor";
    public static final String GAME_DATA = "Game data";

    // Labels for commands to execute by pressing a button on the dashboard
    public static final String DRIVE_TO_LINE_LABEL = "Drive to line";
    public static final String TURN_LEFT_LABEL = "turn left";
    public static final String TURN_RIGHT_LABEL = "turn right";

    public static final String AUTO_MODE_LABEL = "Auto mode";
    public static final String DEFAULT_AUTO_LABEL = "Default Auto";
    // Strategic autonomous commands
    public static final String CENTER_START_SWITCH = "Center switch";
    public static final String LEFT_SWITCH_AUTO = "Starting on left, going for switch";
    public static final String RIGHT_SWITCH_AUTO = "Starting on right, going for switch";
    public static final String LEFT_SCALE_AUTO = "Starting on left, going for scale";
    public static final String RIGHT_SCALE_AUTO = "Starting on right. going for scale";

    // Tactical autonomous commands
    public static final String LEFT_START_SWITCH_RIGHT = "Left start, Right switch";
    public static final String LEFT_START_SWITCH_LEFT = "Left start, Left switch";
    public static final String RIGHT_START_SWITCH_RIGHT = "Right start, Right switch";
    public static final String LEFT_START_SCALE_LEFT = "Left start, Left scale";
    public static final String RIGHT_START_SCALE_LEFT = "Right start, Left scale";
    public static final String STOP_LABEL = "Stop the Robit";
    public static final String LEFT_START_SCALE_RIGHT = "Left start, Right scale";
    public static final String RIGHT_START_SCALE_RIGHT = "Right start, Right scale";
    public static final String RIGHT_START_SWITCH_LEFT = "Right start, Left switch";
    public static final String REVERSE_TO_SENSOR = "Reversing to Distance";

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
	oi = new OI();
	gameData = new GameData();

	initializeDashboard();
    }

    /**
     * Initialize the dashboard.
     */
    private void initializeDashboard() {
	chooser.addDefault(DEFAULT_AUTO_LABEL, new Stop()); // martian rock
	chooser.addObject(DRIVE_TO_LINE_LABEL, new DriveForwardToLine());
	chooser.addObject(CENTER_START_SWITCH, new CenterSwitchAuto());
	chooser.addObject(LEFT_SWITCH_AUTO, new LeftSwitchAuto());
	chooser.addObject(RIGHT_SWITCH_AUTO, new RightSwitchAuto());
	chooser.addObject(LEFT_SCALE_AUTO, new LeftScaleAuto());
	chooser.addObject(RIGHT_SCALE_AUTO, new RightScaleAuto());
	chooser.addObject(STOP_LABEL, new Stop());
	chooser.addObject(REVERSE_TO_SENSOR, new ReverseTillSensed());

	/*
	 * chooser.addObject(LEFT_START_SWITCH_RIGHT, new LeftSwitchAutoRight());
	 * chooser.addObject(LEFT_START_SWITCH_LEFT, new LeftSwitchAutoLeft());
	 * chooser.addObject(RIGHT_START_SWITCH_RIGHT, new RightSwitchAutoRight());
	 * chooser.addObject(LEFT_START_SCALE_LEFT, new LeftScaleAutoLeft());
	 * chooser.addObject(RIGHT_START_SCALE_RIGHT, new RightScaleAutoRight());
	 * chooser.addObject(RIGHT_START_SCALE_LEFT, new RightScaleAutoLeft());
	 * chooser.addObject(LEFT_START_SCALE_RIGHT, new LeftScaleAutoRight());
	 * chooser.addObject(RIGHT_START_SWITCH_LEFT, new RightSwitchAutoLeft());
	 */
	SmartDashboard.putData(AUTO_MODE_LABEL, chooser);

	SmartDashboard.putBoolean(DRIVE_MODE_LABEL, true);
	SmartDashboard.putBoolean(TURN_WITH_PID_LABEL, false);
	SmartDashboard.putNumber(LEFT_DRIVE_ENCODER, 0);
	SmartDashboard.putNumber(RIGHT_DRIVE_ENCODER, 0);
	SmartDashboard.putString(GAME_DATA, "");
	SmartDashboard.putData(DRIVE_TO_LINE_LABEL, new DriveForwardToLine());
	SmartDashboard.putData(TURN_LEFT_LABEL, new TurnLeft(90));
	SmartDashboard.putData(TURN_RIGHT_LABEL, new TurnRight(90));
	SmartDashboard.putData(STOP_LABEL, new Stop());
	SmartDashboard.putData(REVERSE_TO_SENSOR, new ReverseTillSensed());
    }

    /**
     * This function is called once each time the robot enters Disabled mode. You
     * can use it to reset any subsystem information you want to clear when the
     * robot is disabled.
     */
    @Override
    public void disabledInit() {
	Scheduler.getInstance().removeAll();
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
	gameData.readGameData();
	autonomousCommand = chooser.getSelected();
	driveSubsystem.resetEncoders();

	// schedule the autonomous command
	if (autonomousCommand != null) {
	    autonomousCommand.start();
	}
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
	Scheduler.getInstance().run();
    }

    int timesSeenWhiteLine = 0;

    @Override
    public void teleopInit() {
	Robot.driveSubsystem.setDriveMode(SmartDashboard.getBoolean(DRIVE_MODE_LABEL, true));
	// This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to
	// continue until interrupted by another command, remove
	// this line or comment it out.
	if (autonomousCommand != null) {
	    autonomousCommand.cancel();
	}
	timesSeenWhiteLine = 0;
	SmartDashboard.putNumber("Times Robot has seen White Line", timesSeenWhiteLine);
    }

    /**
     * This function is called periodically during operator control
     */
    public static final double cubeSpeed = 0.5;

    @Override
    public void teleopPeriodic() {
	SmartDashboard.putBoolean(LINE_SENSOR, driveSubsystem.linesensor.get());
	Scheduler.getInstance().run();
	SmartDashboard.putNumber("POV number", Robot.oi.xboxControl.getPOV());
	if (Robot.oi.xboxControl.getPOV() == 0.0) {
	    cubeSubsystem.cubeOut(cubeSpeed);
	    SmartDashboard.putString("Cube Control", "Cube Out");
	} else if (Robot.oi.xboxControl.getPOV() == 90.0) {
	    cubeSubsystem.twistRight(cubeSpeed);
	    SmartDashboard.putString("Cube Control", "Twist Right");
	} else if (Robot.oi.xboxControl.getPOV() == 180.0) {
	    cubeSubsystem.cubeIn(cubeSpeed);
	    SmartDashboard.putString("Cube Control", "Cube In");
	} else if (Robot.oi.xboxControl.getPOV() == 270.0) {
	    cubeSubsystem.twistLeft(cubeSpeed);
	    SmartDashboard.putString("Cube Control", "Twist Left");
	}
	if (!Robot.driveSubsystem.linesensor.get()) {
	    timesSeenWhiteLine = timesSeenWhiteLine + 1;
	    SmartDashboard.putNumber("Times Robot has seen White Line", timesSeenWhiteLine);
	}
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {

    }
}
