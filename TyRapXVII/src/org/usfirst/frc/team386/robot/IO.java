package org.usfirst.frc.team386.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.AnalogUltrasonic;

/**
 * Houses declarations for physical components of the robot and driver station
 * Also houses setup functions for components within the static block
 * 
 * @author Team 386
 *
 */
public class IO
{
    // Driver Station
    // Joysticks

    public final static Joystick leftJoystick = new Joystick(0);
    public final static Joystick rightJoystick = new Joystick(1);
    public final static Joystick manip = new Joystick(2);
    // Dashboard
    public final static SendableChooser<String> autoChooser = new SendableChooser<>();
    static
    {
	SmartDashboard.putNumber("Pixel Compensation", Constants.pixelCompensation);
	SmartDashboard.putNumber("Shooter motor speed", Constants.shooterMotorSpeed);
	SmartDashboard.putNumber("Trigger motor speed", Constants.triggerMotorSpeed);
	autoChooser.addDefault(Constants.martianRock, Constants.martianRock);
	autoChooser.addObject(Constants.driveForward, Constants.driveForward);
	autoChooser.addObject(Constants.frontPeg, Constants.frontPeg);
	autoChooser.addObject(Constants.boilerSidePeg, Constants.boilerSidePeg);
	autoChooser.addObject(Constants.boilerShoot, Constants.boilerShoot);
	autoChooser.addObject(Constants.feederStationSidePeg, Constants.feederStationSidePeg);
	SmartDashboard.putData("Auto Modes", autoChooser);
    }
    public final static SendableChooser<String> sideChooser = new SendableChooser<>();
    static
    {
	sideChooser.addObject(Constants.blue, Constants.blue);
	sideChooser.addObject(Constants.red, Constants.red);
	SmartDashboard.putData("Field Side", sideChooser);
    }

    // RoboRIO
    // USB
    public static UsbCamera backCamera = CameraServer.getInstance().startAutomaticCapture(0);
    //public static CvSink frontCameraSink = CameraServer.getInstance().getVideo(frontCamera);
    public static UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(1);
    public static CvSink cameraSink = CameraServer.getInstance().getVideo(camera);
    static
    {
	backCamera.setResolution(80, 80/4*3);
	backCamera.setFPS(15);
	camera.setResolution(Constants.mainCameraWidth, Constants.mainCameraWidth / 4 * 3);
	camera.setFPS(30);
	
    }

    // SPI
    public static ADXRS450_Gyro gyro;
    static
    {
	try
	{
	    gyro = new ADXRS450_Gyro();
	} catch (NoClassDefFoundError e)
	{
	    throw e;
	}
    }
    // PWMs
    public final static RobotDrive drivetrain = new RobotDrive(0, 1, 2, 3);
    public final static Spark ballPickup = new Spark(5);
    public final static Spark trigger = new Spark(6);
    public final static Spark climber = new Spark(7);
    public final static Spark shooter = new Spark(8);
    // Analog In
    public final static AnalogUltrasonic gearUltrasonic = new AnalogUltrasonic(0, 1.18, 11.3);
    public final static AnalogUltrasonic shooterUltrasonic = new AnalogUltrasonic(1, 0, 196.85);

    // Relays
    public final static Relay cameraLight = new Relay(1);
    public final static Relay decorationLight = new Relay(2);
    public final static Relay agitator = new Relay(3);
    static
    {
	cameraLight.set(Relay.Value.kForward);
	decorationLight.set(Relay.Value.kForward);
    }
    // DIO
    public final static Encoder rightEncodee = new Encoder(8, 9);
    public final static Encoder leftEncodee = new Encoder(0, 1);
    /**
     * pressed is false
     */
    public final static DigitalInput rightGearRelease = new DigitalInput(6);
    /**
     * pressed is false
     */
    public final static DigitalInput leftGearRelease = new DigitalInput(7);

    // Pnuematics Control Module
    public final static Compressor compressor = new Compressor();
    public final static DoubleSolenoid shifter = new DoubleSolenoid(0, 1);
    public final static DoubleSolenoid gearDelivery = new DoubleSolenoid(6, 7);
    public final static DoubleSolenoid gearCollection = new DoubleSolenoid(4, 5);
    static
    {
	compressor.start();
	gearDelivery.set(Value.kForward);
    }
}
