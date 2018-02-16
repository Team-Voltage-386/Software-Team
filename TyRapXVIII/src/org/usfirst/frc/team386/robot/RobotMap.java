package org.usfirst.frc.team386.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // Talons
    public static int leftPrimaryDriveMotor = 1; // motor
    public static int rightPrimaryDriveMotor = 4; // motor
    public static int leftFollowerDriveMotor = 2; // motor
    public static int rightFollowerDriveMotor = 5; // motor

    public static int leftDriveEncoderChannelA = 10; // encoder
    public static int leftDriveEncoderChannelB = 11; // encoder
    public static int rightDriveEncoderChannelA = 12; // encoder
    public static int rightDriveEncoderChannelB = 13; // encoder

    // PWM
    public static int elevatorSparks = 0; // spark
    public static int leftCubeIntakeMotor = 2; // spark
    public static int rightCubeIntakeMotor = 3; // spark

    // CAN bus
    public static int pigeon = 0; // pigeonimu

    // DIO (Digital IO)
    public static int lowerElevatorLimitSwitch = 0; // digital input
    public static int elevatorEncoderA = 1; // encoder
    public static int elevatorEncoderB = 2; // encoder
    public static int rearPingChannel = 5; // ultrasonic
    public static int rearEchoChannel = 6; // ultrasonic
    public static int lineSensorChannel = 7; // digital input

    // Analog In
    public static int cubeUltrasonicCenter = 0; // ultrasonic
    public static int cubeUltrasonicEdge = 1; // ultrasonic

    // PCM (Pneumatic Control Module)
    public static int compressor = 0;
    public static int gearShiftSolenoidForwardChannel = 0; // solenoid
    public static int gearShiftSolenoidReverseChannel = 1; // solenoid
    public static int chainBreaker = 2; // solenoid
    public static int armsForwardChannel = 3; // solenoid
    public static int armsReverseChannel = 4; // solenoid
    public static int latchForwardChannel = 6; // solenoid
    public static int latchReverseChannel = 7; // solenoid

    // Controls
    public static int driverPort = 0;
    public static int manipulatorPort = 1;

    // Manipulator Buttons
    public static int shiftButton = 5;
    public static int moveToVaultLevelButton = 2;
    public static int chainBreakButton = 1;

    // Drive buttons
    public static int boostButton = 8;
    public static int driveToCubeButton = 3;
}
