
package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.commands.DriveDistanceFromWall;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.DriveForwardToLine;
import org.usfirst.frc.team386.robot.commands.DriveToCube;
import org.usfirst.frc.team386.robot.commands.Stop;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;
import org.usfirst.frc.team386.robot.commands.auto.AutoLine;
import org.usfirst.frc.team386.robot.commands.auto.MartianRock;
import org.usfirst.frc.team386.robot.commands.auto.ScaleAuto;
import org.usfirst.frc.team386.robot.commands.auto.SwitchAuto;
import org.usfirst.frc.team386.robot.subsystems.CubeSubsystem;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team386.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team386.robot.subsystems.TiltSubsystem;

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
    public static final TiltSubsystem tiltSubsystem = new TiltSubsystem();

    public static final CubeVisionThread cubeVision = new CubeVisionThread();

    public static OI oi;
    public static GameData gameData;

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
    public static final String LEFT_ENCODER_RIO = "Left encoder RIO";
    public static final String RIGHT_ENCODER_RIO = "Right encoder RIO";
    public static final String ENCODER_TALON_1 = "Encoder Talon 1";
    public static final String ENCODER_TALON_4 = "Encoder Talon 4";
    public static final String LINE_SENSOR = "Line sensor";
    public static final String GAME_DATA = "Game data";
    public static final String REAR_ULTRASONIC = "Rear ultra";
    public static final String FRONT_ULTRASONIC = "Front ultra";
    public static final String VISION_ERROR = "Vision error";
    public static final String TIMES_SEEN_WHITE_LINE = "Times Seen White Line";

    // Labels for commands to execute by pressing a button on the dashboard
    public static final String DRIVE_TO_LINE_LABEL = "Drive to line";
    public static final String TURN_LEFT_LABEL = "Turn left";
    public static final String TURN_RIGHT_LABEL = "Turn right";
    public static final String DRIVE_FORWARD_FIVE_FEET_LABEL = "Drive forward 5 feet";
    public static final String MOVE_FROM_WALL = "Move to wall";
    public static final String DRIVE_TO_CUBE = "Drive to cube";
    public static final String STOP_LABEL = "Stop the Robit";

    // Strategic autonomous commands
    public static final String ROCK = "Rock";
    public static final String SWITCH = "Switch";
    public static final String SCALE = "Scale";
    public static final String AUTO_LINE = "Auto Line";

    public static final String LEFT = "Left";
    public static final String RIGHT = "Right";
    public static final String CENTER = "Center";
    public static final Boolean YES = true;
    public static final Boolean NO = false;

    public static SendableChooser<Command> chooserMode = new SendableChooser<>();
    public static SendableChooser<String> chooserPosition = new SendableChooser<>();
    public static SendableChooser<Boolean> chooserCrossSide = new SendableChooser<>();

    Command autonomousCommand;// = new Stop()
    // UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    int timesSeenWhiteLine = 0;

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
	// tiltDetection.start();
    }

    /**
     * Initialize the dashboard.
     */
    private void initializeDashboard() {
	chooserMode.addDefault(ROCK, new MartianRock());
	chooserMode.addObject(SWITCH, new SwitchAuto());
	chooserMode.addObject(SCALE, new ScaleAuto());
	chooserMode.addObject(AUTO_LINE, new AutoLine());
	// chooserMode.addObject("Rock", new MartianRock());
	// chooserMode.setName("Choose Mode");

	chooserPosition.addDefault("Center", CENTER);
	chooserPosition.addObject("Left", LEFT);
	chooserPosition.addObject("Right", RIGHT);
	// chooserPosition.addObject("Center", CENTER);

	chooserCrossSide.addDefault("Allow crossing", YES);
	// chooserCrossSide.addObject("Yes", yes);
	chooserCrossSide.addObject("Don't allow crossing", NO);
	// SmartDashboard.putBoolean("Cross to other Side", chooseCross);

	// SmartDashboard.putData(AUTO_MODE_LABEL, chooser);
	SmartDashboard.putData("Auto Mode", chooserMode);
	SmartDashboard.putData("Start Position", chooserPosition);
	SmartDashboard.putData("Allow Cross Side?", chooserCrossSide);

	// Configuration fields
	SmartDashboard.putBoolean(DRIVE_MODE_LABEL, true);
	SmartDashboard.putBoolean(TURN_WITH_PID_LABEL, false);
	SmartDashboard.putBoolean(CUBE_CONTROL_LABEL, true);

	// Diagnostic data
	updateDiagnostics();

	// Command buttons for one-time execution
	SmartDashboard.putData(DRIVE_FORWARD_FIVE_FEET_LABEL, new DriveForward(60, 0.5));
	SmartDashboard.putData(DRIVE_TO_LINE_LABEL, new DriveForwardToLine());
	SmartDashboard.putData(TURN_LEFT_LABEL, new TurnLeft(90));
	SmartDashboard.putData(TURN_RIGHT_LABEL, new TurnRight(90));
	SmartDashboard.putData(STOP_LABEL, new Stop());
	SmartDashboard.putData(MOVE_FROM_WALL, new DriveDistanceFromWall(558));
	SmartDashboard.putData(DRIVE_TO_CUBE, new DriveToCube());

	SmartDashboard.putNumber("Elevator Speed", .25);
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
	autonomousCommand = (Command) chooserMode.getSelected();
	driveSubsystem.resetEncoders();

	// schedule the autonomous command

	if (autonomousCommand != null) {
	    SmartDashboard.putString("i", "nit");
	    autonomousCommand.start();
	}
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
	updateDiagnostics();
	Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
	driveSubsystem.setDriveMode(SmartDashboard.getBoolean(DRIVE_MODE_LABEL, true));
	cubeSubsystem.setCubeControlMode(SmartDashboard.getBoolean(CUBE_CONTROL_LABEL, true));
	Robot.driveSubsystem.isGoingUpRamp = true;
	// This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to
	// continue until interrupted by another command, remove
	// this line or comment it out.
	if (autonomousCommand != null) {
	    autonomousCommand.cancel();
	}

	timesSeenWhiteLine = 0;

    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
	updateDiagnostics();
	Scheduler.getInstance().run();

	if (!driveSubsystem.linesensor.get()) {
	    timesSeenWhiteLine = timesSeenWhiteLine + 1;
	}
    }

    /**
     * Renders a collection of diagnostic data to the smart dashboard.
     */
    private void updateDiagnostics() {
	driveSubsystem.updateDiagnostics();
	elevatorSubsystem.updateDiagnostics();
	cubeSubsystem.updateDiagnostics();
	tiltSubsystem.updateDiagnostics();
	cubeVision.updateDiagnostics();

	SmartDashboard.putNumber(TIMES_SEEN_WHITE_LINE, timesSeenWhiteLine);
    }
}
