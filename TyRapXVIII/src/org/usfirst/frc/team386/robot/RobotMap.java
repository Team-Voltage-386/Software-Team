package org.usfirst.frc.team386.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static int leftPrimaryDriveMotor = 1;
    public static int rightPrimaryDriveMotor = 4;

    public static int leftFollowerDriveMotor = 2;
    public static int rightFollowerDriveMotor = 5;
    public static int leftFollowerDriveMotor2 = 3;
    public static int rightFollowerDriveMotor2 = 6;

    public static int gearShiftCompressor = 0;

    public static int gearShiftSolenoidForwardChannel = 0;
    public static int gearShiftSolenoidReverseChannel = 1;

    public static int leftDriveEncoderChannelA = 0;
    public static int leftDriveEncoderChannelB = 1;
    public static int rightDriveEncoderChannelA = 2;
    public static int rightDriveEncoderChannelB = 3;

    public static int leftCubeIntakeMotor = 2;
    public static int rightCubeIntakeMotor = 3;

    public static int lineSensorChannel = 7;

    public static int rearPingChannel = 5;
    public static int rearEchoChannel = 6;
    public static int frontPingChannel = 3;
    public static int frontEchoChannel = 4;

    public static int elevatorSpark = 0;
}
