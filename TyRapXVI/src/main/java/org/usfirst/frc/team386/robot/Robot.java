package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.OkiePokerThread.State;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.vision.USBCamera;

	/**
	 * TyRap XVI Code (AKA Duktape I)
	 * The main class of the robot
	 * Consists mostly of variable definitions and method calls to other classes.
	 * @author TeamVoltage
	 */
public class Robot extends IterativeRobot {
    	//Final is needed to prevent thread access issues. 
    	//Static is required to all other threads to access the variable without making an instance of the Robot class or passing as a parameter
	
    	//INPUT
	final static DriverStation driverstation = DriverStation.getInstance();
	//Joysticks
	final static Joystick leftJoystick = new Joystick(0);
	final static Joystick rightJoystick = new Joystick(1);
	final static Joystick manipController = new Joystick(2);
	
	//AutoChooser (Defense)
	final static String martianRock = "Martian Rock";
	final static String moatRockWallUnevenTerrain = "Moat/Rock Wall/Uneven Terrain";
	final static String ramparts = "Ramparts";
	final static String seesaw = "Seesaw";
	final static String portcullis = "Portcullis";
	final static String driveUpToDefense = "Drive up to defense";
	final static SendableChooser<String> defenseChooser = new SendableChooser<String>();    
		
	//Camera
	    
	//DIO
	//(pingChannel, echoChannel)
	final static Ultrasonic ultra = new Ultrasonic(0,1);
	//Order only inverts sign of the outputs
	final static Encoder leftEncodee = new Encoder(8,9);
	final static Encoder rightEncodee = new Encoder(6,7);
	final static DigitalInput pickupLimit = new DigitalInput(4);
	final static DigitalInput resetLimit = new DigitalInput(3);
	final static Relay light = new Relay(0);
	
	//Analog
	//("Gyro Temp A1")
	final static AnalogGyro gyro = new AnalogGyro(0);
    
	//OUTPUT
	//Drivetrain
	//(Front Left, Rear Left, Front Right, Rear Right)
	final static RobotDrive drivetrain = new RobotDrive(8,9,1,0);
	final static double differential = 1;
		
	//Servo
	final static Servo cameraServo = new Servo(3);
	final static Talon pickup = new Talon(5);
	final static Spark extendoMotor = new Spark(6);
	final static Spark winchMotor = new Spark(7);
	
	//Pneumatics
	final static Compressor compressor = new Compressor();
	final static DoubleSolenoid shootResetSolenoid = new DoubleSolenoid(0,2,3);
	final static DoubleSolenoid shootLockSolenoid = new DoubleSolenoid(1,6,7);
	final static DoubleSolenoid driveSolenoid = new DoubleSolenoid(1,0,1);
	final static DoubleSolenoid okiePokerAirSolenoid = new DoubleSolenoid(1,2,3);
	final static DoubleSolenoid okiePokerControlSolenoid = new DoubleSolenoid(1,4,5);
	
	//Threads
	final static DriveThread drive = new DriveThread();
	final static ShootThread shooter = new ShootThread();
	final static PickupThread ballPickup = new PickupThread();
	final static OkiePokerThread okiePoker = new OkiePokerThread();
	final static EasyButtonThread easyButton = new EasyButtonThread();
	final static CameraMoveThread cameraMove = new CameraMoveThread();
	final static SensorOutputThread sensorOutput = new SensorOutputThread();
	
	/**
	 * Runs when the robot turns on
	 */
	public void robotInit() { 
    		//Defense Chooser
	    	defenseChooser.addObject(martianRock, martianRock);
	    	defenseChooser.addObject(ramparts, ramparts);
	    	defenseChooser.addObject(moatRockWallUnevenTerrain, moatRockWallUnevenTerrain);
	    	defenseChooser.addDefault(seesaw, seesaw);
	    	defenseChooser.addObject(portcullis, portcullis);
	    	defenseChooser.addObject(driveUpToDefense, driveUpToDefense);
	        SmartDashboard.putData("Auto choices", defenseChooser);
	        
	        //Camera
	        //cServer.setQuality(100);
	        //cServer.startAutomaticCapture("cam0");
	        //usbCam.startCapture();
	        
	        //Compressor
	        compressor.start();
	        //Ultrasonic
	        ultra.setAutomaticMode(true);
    }
	/**
         * Runs when teleop starts
         */
        public void teleopInit(){ 
            	Robot.shootLockSolenoid.set(DoubleSolenoid.Value.kForward);
            	Robot.shootResetSolenoid.set(DoubleSolenoid.Value.kReverse);
            	Robot.okiePoker.setState(State.UP);
        	drivetrain.tankDrive(0,0);
        	compressor.start();
        	gyro.reset();
            	try
            	{
            	    drive.start(); //This thread controls the drivetrain and only the drivetrain
            	    ballPickup.start(); //Controls the ball pickup and makes the controller vibrate @ 25 seconds left in the match
            	    shooter.start();//Makes the robot shoot when the trigger is pressed
            	    okiePoker.start();//Controls the skeleton key
            	    cameraMove.start();//Makes the camera move on command
            	    sensorOutput.start();//outputs sensor values to the dashboard
            	    easyButton.start();//controls the easy buttons that they wanted so bad and never used
            	}
            	catch (IllegalThreadStateException e)
            	{
            	    
            	}
        	
        }
        /**
         * Called when autonomous begins
         */
        public void autonomousInit() 
        {
        	Auto.variableAuto();
        }
    	
    
    public void teleopPeriodic(){}
    public void autonomousPeriodic(){}
    public void testInit(){}
    public void testPeriodic(){}
    public void disabledInit(){}
    public void disabledPeriodic(){}
    
}
