
package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.DriveDistanceFromWall;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.DriveForwardToLine;
import org.usfirst.frc.team386.robot.commands.DriveToCube;
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
    // UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();

    // Game data configurations
    public static final String LLL = "LLL";
    public static final String LRL = "LRL";
    public static final String RRR = "RRR";
    public static final String RLR = "RLR";

    // Settings
    public static final String DRIVE_MODE_LABEL = "Arcade drive?";
    public static final String TURN_WITH_PID_LABEL = "Turn with PID?";
    public static final String CUBE_CONTROL_LABEL = "Joystick cube control?";

    // Encoder and sensor labels
    public static final String LEFT_DRIVE_ENCODER = "Left encoder";
    public static final String RIGHT_DRIVE_ENCODER = "Right encoder";
    public static final String LINE_SENSOR = "Line sensor";
    public static final String GAME_DATA = "Game data";
    public static final String ULTRASONIC = "Rear ultra";
    public static final String FRONT_ULTRASONIC = "Front ultra";

    // Labels for commands to execute by pressing a button on the dashboard
    public static final String DRIVE_TO_LINE_LABEL = "Drive to line";
    public static final String TURN_LEFT_LABEL = "turn left";
    public static final String TURN_RIGHT_LABEL = "turn right";
    public static final String DRIVE_FORWARD_FIVE_FEET_LABEL = "drive forward 5 feet";

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
    public static final String MOVE_FROM_WALL = "Move to wall";
    public static final CubeVisionThread cubeVision = new CubeVisionThread();

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
	oi = new OI();
	gameData = new GameData();
	cubeVision.start();
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

	SmartDashboard.putData(AUTO_MODE_LABEL, chooser);

	SmartDashboard.putBoolean(DRIVE_MODE_LABEL, true);
	SmartDashboard.putBoolean(TURN_WITH_PID_LABEL, false);
	SmartDashboard.putBoolean(CUBE_CONTROL_LABEL, true);
	SmartDashboard.putNumber(LEFT_DRIVE_ENCODER, 0);
	SmartDashboard.putNumber(RIGHT_DRIVE_ENCODER, 0);
	SmartDashboard.putString(GAME_DATA, "");
	SmartDashboard.putData(DRIVE_FORWARD_FIVE_FEET_LABEL, new DriveForward(60, 0.5));
	SmartDashboard.putData(DRIVE_TO_LINE_LABEL, new DriveForwardToLine());
	SmartDashboard.putData(TURN_LEFT_LABEL, new TurnLeft(90));
	SmartDashboard.putData(TURN_RIGHT_LABEL, new TurnRight(90));
	SmartDashboard.putData(STOP_LABEL, new Stop());
	SmartDashboard.putData(MOVE_FROM_WALL, new DriveDistanceFromWall(558));
	SmartDashboard.putData("Drive to cube", new DriveToCube());
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
	SmartDashboard.putBoolean(LINE_SENSOR, driveSubsystem.linesensor.get());
	SmartDashboard.putNumber(ULTRASONIC, driveSubsystem.ultrasonic.getRangeMM());
	SmartDashboard.putNumber(FRONT_ULTRASONIC, driveSubsystem.frontUltrasonic.getRangeMM());
	Scheduler.getInstance().run();
    }

    int timesSeenWhiteLine = 0;

    @Override
    public void teleopInit() {
	Robot.driveSubsystem.setDriveMode(SmartDashboard.getBoolean(DRIVE_MODE_LABEL, true));
	Robot.cubeSubsystem.setCubeControlMode(SmartDashboard.getBoolean(CUBE_CONTROL_LABEL, true));
	// This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to
	// continue until interrupted by another command, remove
	// this line or comment it out.
	if (autonomousCommand != null) {
	    autonomousCommand.cancel();
	}

	// TODO: create an event system for listening to line events
	timesSeenWhiteLine = 0;
	SmartDashboard.putNumber("Times Robot has seen White Line", timesSeenWhiteLine);
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
	SmartDashboard.putBoolean(LINE_SENSOR, driveSubsystem.linesensor.get());
	SmartDashboard.putNumber(ULTRASONIC, driveSubsystem.ultrasonic.getRangeMM());
	SmartDashboard.putNumber(FRONT_ULTRASONIC, driveSubsystem.frontUltrasonic.getRangeMM());
	Scheduler.getInstance().run();

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
