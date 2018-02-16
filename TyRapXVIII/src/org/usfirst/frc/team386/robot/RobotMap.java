package org.usfirst.frc.team386.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
/*
* group 1 = talon[talon, encoder]
* group 2 = pwd[spark]
* group 3 elevaotr{gpio]
* group 4 = canbus[pigeon]
* group 5 = solenoid[solenoid]
* group 6 = dio[ultrasonic]
*/
public class RobotMap {
    // Talon Motors
    public static int leftPrimaryDriveMotor = 1; //Talon
    public static int rightPrimaryDriveMotor = 4; //talon

    public static int leftFollowerDriveMotor = 2; //talon
    public static int rightFollowerDriveMotor = 5; //talon
    
    public static int leftDriveEncoderChannelA = 10; //encoder
    public static int leftDriveEncoderChannelB = 11; //encoder
    public static int rightDriveEncoderChannelA = 12; //encodr
    public static int rightDriveEncoderChannelB = 13; //encoder

    public static int elevatorEncoderA = 1; //encoder
    public static int elevatorEncoderB = 2; //encoder
    
    
    //pwd
    public static int leftCubeIntakeMotor = 2; //spark
    public static int rightCubeIntakeMotor = 3; //spark

    public static int elevatorSpark = 0; //spark

    
    // canbus
    public static int compressor = 0; //compressor //
    public static int lineSensorChannel = 7; //digital input //canbus
    public static int pigeon = 17; //pigeonimu
    
    
    //dio
    public static int rearPingChannel = 5; //ultrasonic
    public static int rearEchoChannel = 6; //ultrasonic
    public static int cubeUltraLeft = 0; //ultrasonic
    public static int cubeUltraRight = 1; //ultrasonic
    
    
    // solenoids
    public static int gearShiftSolenoidForwardChannel = 0; //solenoid
    public static int gearShiftSolenoidReverseChannel = 1; //solenoid

    public static int chainBreaker = 2; //solenoid

    public static int armsForwardChannel = 3; //solenoid
    public static int armsReverseChannel = 4; //solenoid

    public static int latchForwardChannel = 6; //solenoid
    public static int latchReverseChannel = 7; //solenoid
    
    

}
