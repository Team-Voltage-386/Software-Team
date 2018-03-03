
package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.subsystems.CubeSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static final CubeSubsystem cubeSubsystem = new CubeSubsystem();

    /**
     * The operator interface. This is where the joystick(s) and manipulator are
     * configured.
     */
    public static OI oi;

    /**
     * Provides game data from the driver station. This is where the randomized
     * switch and scale data comes from.
     */

    // Configuration
    public static final String ELEVATOR_SPEED_LABEL = "Elevator Speed";

    // Encoder and sensor labels
    public static final String LEFT_ENCODER_RIO = "Left encoder RIO";
    public static final String RIGHT_ENCODER_RIO = "Right encoder RIO";
    public static final String ENCODER_TALON_1 = "Encoder Talon 1";
    public static final String ENCODER_TALON_3 = "Encoder Talon 3";
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

    // Autonomous configuration
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
	// cubeVision.start();
	// SmartDashboard.putNumber("defaultSpeed", .001);
	// SmartDashboard.putNumber("proportion", 1);
	initializeDashboard();
    }

    /**
     * Initialize the dashboard. Sets up all of the controls and initializes any
     * diagnostic display fields.
     */
    private void initializeDashboard() {
	// Autonomous control

    }

    /**
     * This function is called once each time the robot enters Disabled mode. You
     * can use it to reset any subsystem information you want to clear when the
     * robot is disabled.
     */
    @Override
    public void disabledInit() {
	// new SetElevator(0).start();
	if (autonomousCommand != null)
	    autonomousCommand.cancel();
	Scheduler.getInstance().removeAll();
    }

    /**
     * Called repeatedly while the robot is disabled.
     */
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
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
	updateDiagnostics();
	Scheduler.getInstance().run();
    }

    /**
     * Called once when the robot enters teleop mode.
     */
    @Override
    public void teleopInit() {
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

	// if (!driveSubsystem.linesensor.get()) {
	// timesSeenWhiteLine = timesSeenWhiteLine + 1;
	// }
    }

    /**
     * Renders a collection of diagnostic data to the smart dashboard.
     */
    private void updateDiagnostics() {
	cubeSubsystem.updateDiagnostics();
    }
}
