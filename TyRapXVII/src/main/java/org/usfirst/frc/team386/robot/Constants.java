package org.usfirst.frc.team386.robot;

/**
 * Houses numerical constants for use in other functions/classes
 * 
 * @author Team 386
 *
 */
public class Constants
{
    // Drivetrain Constants
    public final static int shiftButton = 1;// The trigger
    // Gear Constants
    public final static int gearButton = 3;//Blue
    public final static int autoGearEnd = 2;
    public final static int autoGearStart = 3;
    // Shooter Constants
    public final static int shootButton = 2;//Red
    public /*final*/ static double shooterMotorSpeed = -0.9; /*Previously -.85*/
    public /*final*/ static double triggerMotorSpeed = -0.9; /*Previously -.25*/
    public final static double spinnupTime = 2.5;
    public final static int agitatorButton = 1;//Green
    // Pickup Constants
    //public final static int pickupButton = 1;//Green
    // Climber Constants
    public final static int climbButton = 5;//Orange/LB
    //Camera Constants 
    //public final static int cameraButton = 1; //RB

    //camera
    public final static int mainCameraWidth = 300;//300
    // Auto modes
    public final static String martianRock = "Martian Rock";
    public final static String driveForward = "Drive Over Base Line";
    public final static String frontPeg = "Center Peg";
    public final static String boilerSidePeg = "Boiler Side Peg";
    public final static String feederStationSidePeg = "Feeder Station Side Peg";
    public final static String boilerShoot = "Just Shoot";//gear?
    // Sides of the field
    public final static String blue = "Blue";
    public final static String red = "Red";
    // Autonomous constants
    public final static double baseAutoSpeed = 0.7;
    public final static double baseTurnSpeed = 0.6;
    public final static double rawEncoderTicksToMoveForDriveForward = 5000;
    public final static double forwards = -1;
    public final static double backwards = 1;
    public final static double gyroCompensation = -0.25;
    public final static double pixelCompensation = 0.7;
}