package org.usfirst.frc.team683.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * Handles all the autonomous methods. It's like OneSpot at Target (A giant vadyrtgicl).
 * ...I'm sorry
 * @author TeamVoltage
 *
 */
public class Auto
{

    public static final double FORWARD = -1; //go forward
    public static final double BACKWARD = 1; //go backward
    
    public static int normalEncoderDistance = 3650;
    public static double portUSDistance = 5;
    public static int portEncoderDistance = 370;
    public static double drawUSDistance = 5;
    public static double drawEncoderDistance = 922.0;
    public static double seesawUSDistance = 5.5;
    public static int seesawEncoderDistance = 30;

    /* 
     * Encoder, 1 foot is 250 "distance"
     */
    static double gyroValue; //value of gyro
    static double difference; //not important
    static int homeGyro;//again, not important
    
    /**
     * Reads from the auto chooser what auto modes should be done
     */
    public static void variableAuto()
    {
	String defenseToCross = (String) Robot.defenseChooser.getSelected();
	    // Cross what defense the robot is in front of
	    switch (defenseToCross)
	    {
	    case Robot.martianRock:
	    default:
		martianRock(); //a defense
		break;

	    case Robot.moatRockWallUnevenTerrain:
		moatRockWallUnevenTerrain(); //a defense
		break;

	    case Robot.seesaw:
		//AutoShoot whyCantIMakeThisStatic = new AutoShoot();
		//whyCantIMakeThisStatic.pray();
		break;

	    case Robot.portcullis:
		doPortcullis();//oh my god another defense
		break;
	
	    case Robot.ramparts:
		ramparts();
		break;
		
	    case Robot.driveUpToDefense:
		doDriveUpToDefense();
		break;

	    }
	}

    // Public Methods
    public static void martianRock() //it sits like a rock on mars
    {
	// Nothing!!!
    }

    public static void moatRockWallUnevenTerrain() // move forward, yes
    {
	Robot.driveSolenoid.set(DoubleSolenoid.Value.kReverse);
	Timer.delay(.02);
	Robot.rightEncodee.reset();
	while(Robot.rightEncodee.getDistance()<=normalEncoderDistance && Robot.driverstation.isAutonomous())
	    {
		Robot.drivetrain.tankDrive(1.0 * Auto.FORWARD , 1.0 * Robot.differential * Auto.FORWARD);
	    }
    }
    
    public enum rampartsState
    {
	straight, displacedClockwise, displacedCounterClockwise;
    }
    
    public static void ramparts()
    {
	Robot.driveSolenoid.set(DoubleSolenoid.Value.kReverse);//Set in low gear
	Robot.gyro.reset();//Reset the gyro
	double baseSpeed = 1 * FORWARD;//Set the basespeed
	int gyroThreshold = 15;//Set the gyro threshold in degrees
	rampartsState currentHeading = rampartsState.straight;//We start facing straight
	Robot.rightEncodee.reset();//
	while(Robot.rightEncodee.getDistance()<=normalEncoderDistance && Robot.driverstation.isAutonomous())
	{
	    SmartDashboard.putString("Gyro", Robot.gyro.getAngle()+"");
	    switch(currentHeading)
	    {
	    case straight:
	    default:
		Robot.drivetrain.tankDrive(baseSpeed, baseSpeed);
		
		if(Robot.gyro.getAngle() > gyroThreshold)
		    currentHeading = rampartsState.displacedClockwise;
		else if(Robot.gyro.getAngle() < -1 * gyroThreshold)
		    currentHeading = rampartsState.displacedCounterClockwise;
		break;
	    case displacedClockwise:
		Robot.drivetrain.tankDrive(0, FORWARD);
		if (Robot.gyro.getAngle() < 0)
		    currentHeading = rampartsState.straight;
		break;
	    case displacedCounterClockwise:
		Robot.drivetrain.tankDrive(FORWARD, 0);
		if (Robot.gyro.getAngle() > 0)
		    currentHeading = rampartsState.straight;
		break;
	    }
	}
    }

    /**
	 * Go through the portcullis automatically
	 */
	public static void doPortcullis()
	{   
	    Robot.okiePoker.setState(OkiePokerThread.State.DOWN);
	    Robot.driveSolenoid.set(DoubleSolenoid.Value.kReverse);
	    while(Robot.ultra.getRangeInches()>portUSDistance)//Forward until close
	    {
		Robot.drivetrain.tankDrive(0.7 * Auto.FORWARD , 0.7 * Robot.differential * Auto.FORWARD);
		if(testForCodeStop())
		{
		    return;
		}
	    }
	    Robot.drivetrain.tankDrive(0,0);
	    Robot.okiePoker.setState(OkiePokerThread.State.UP);
	    Timer.delay(1);
	    
	    Robot.leftEncodee.reset();
	    Timer.delay(1);
	    while(Robot.leftEncodee.getDistance()<=portEncoderDistance)//Backwards until gate goes up
	    {
		Robot.drivetrain.tankDrive(0.5 * Robot.differential * Auto.BACKWARD , 0.5 * Auto.BACKWARD);
		if(testForCodeStop())
		{
		    return;
		}
	    }
	    Robot.drivetrain.tankDrive(0,0);
	    
	    double timeStarted = Timer.getFPGATimestamp();
	    while (timeStarted > Timer.getFPGATimestamp()-4)//Forward until through
	    {
		Robot.drivetrain.tankDrive(0.8 * Auto.FORWARD , 0.8 * Robot.differential * Auto.FORWARD);
		if(testForCodeStop() || Robot.ultra.getRangeInches()<3)
		{
		    return;
		}
	    }
	    
	}
	
	public static void doSeesaw()
	{
	    Robot.okiePoker.setState(OkiePokerThread.State.UP);
	    Robot.driveSolenoid.set(DoubleSolenoid.Value.kReverse);
	    while(Robot.ultra.getRangeInches()>seesawUSDistance)//Forward until close
	    {
		Robot.drivetrain.tankDrive(0.7 * Auto.FORWARD , 0.7 * Robot.differential * Auto.FORWARD);
		if(testForCodeStop())
		{
		    return;
		}
	    }
	    Robot.drivetrain.tankDrive(0,0);
	    Robot.okiePoker.setState(OkiePokerThread.State.DOWN);
	    Timer.delay(1);
	    
	    Robot.leftEncodee.reset();
	    Timer.delay(1);
	    while(Robot.leftEncodee.getDistance()<=seesawEncoderDistance)//Backwards until thing goes down
	    {
		Robot.drivetrain.tankDrive(0.5 * Robot.differential * Auto.BACKWARD , 0.5 * Auto.BACKWARD);
		if(testForCodeStop())
		{
		    return;
		}
	    }
	    Robot.drivetrain.tankDrive(0,0);
	    
	    double timeStarted = Timer.getFPGATimestamp();
	    while (timeStarted > Timer.getFPGATimestamp()-4)//Forward until through
	    {
		Robot.drivetrain.tankDrive(0.8 * Auto.FORWARD , 0.8 * Robot.differential * Auto.FORWARD);
		if(testForCodeStop() || Robot.ultra.getRangeInches()<12)
		{
		    return;
		}
	    }
	}
	
	/**
	 * Go through the drawbridge automatically
	 */
	public static void doDrawbridge()
	{
	    Robot.driveSolenoid.set(DoubleSolenoid.Value.kReverse);
	    Robot.okiePoker.setState(OkiePokerThread.State.UP);
	    while(Robot.ultra.getRangeInches()>drawUSDistance)//Forward until close
	    {
		Robot.drivetrain.tankDrive(0.7 * Auto.FORWARD , 0.7 * Robot.differential * Auto.FORWARD);
		if(testForCodeStop())
		{
		    return;
		}
	    }
	    
	    Robot.drivetrain.tankDrive(0,0);
	    Robot.okiePoker.setState(OkiePokerThread.State.DOWN);
	    
	    Timer.delay(0.5);
	    Robot.leftEncodee.reset();
	    while(Robot.leftEncodee.getDistance()<=drawEncoderDistance)//Backwards until down
	    {
		Robot.drivetrain.tankDrive(0.6 * Robot.differential * Auto.BACKWARD , 0.6 * Auto.BACKWARD);
		if(testForCodeStop())
		{
		    return;
		}
	    }
	    Robot.drivetrain.tankDrive(0,0);
	    
	    double timeStarted = Timer.getFPGATimestamp();
	    while (timeStarted > Timer.getFPGATimestamp()-3)
	    {
		System.out.println("BAD");
		Robot.drivetrain.tankDrive(0.8 * Auto.FORWARD , 0.85 * Robot.differential * Auto.FORWARD);
		if(testForCodeStop())
		{
		    return;
		}
	    }
	    Robot.okiePoker.setState(OkiePokerThread.State.UP);
	}

    public static void doDriveUpToDefense()
    {
	Robot.okiePoker.setState(OkiePokerThread.State.UP);
	   while(Robot.ultra.getRangeInches()>=6)//Backwards until gate goes up
	    {
		Robot.drivetrain.tankDrive(0.6 * Robot.differential * Auto.FORWARD , 0.6 * Auto.FORWARD);
		
	    }
	   Robot.drivetrain.tankDrive(0, 0);
	   
    }

    private static boolean testForCodeStop()
    {
	if(Robot.driverstation.isAutonomous())
	{
	    return false; //never stop autonomous
	}
	return Robot.manipController.getRawButton(7); //stop in teleop if button is pressed
    }
}
