package org.usfirst.frc.team386.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static int frontLeftMotor = 0;
    public static int rearLeftMotor = 1;
    public static int frontRightMotor = 2;
    public static int rearRightMotor = 3;

    public static int triggerMotor = 6;
    public static int shooterMotor = 8;

    public static int agitatorRelay = 3;

    public static int backCamera = 0;
    public static int frontCamera = 1;

    public static int decorationLight = 2;

    public static int cameraServo = 4;

    /*
     * If you are using multiple modules, make sure to define both the port number
     * and the module. For example you with a rangefinder: public static int
     * rangefinderPort = 1; public static int rangefinderModule = 1;
     */
}
