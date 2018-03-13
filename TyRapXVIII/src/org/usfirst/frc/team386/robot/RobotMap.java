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
    public static int rightPrimaryDriveMotor = 3; // motor
    public static int leftFollowerDriveMotor = 2; // motor
    public static int rightFollowerDriveMotor = 4; // motor

    public static int leftDriveEncoderChannelA = 10; // encoder
    public static int leftDriveEncoderChannelB = 11; // encoder
    public static int rightDriveEncoderChannelA = 12; // encoder
    public static int rightDriveEncoderChannelB = 13; // encoder

    // PWM
    public static int elevatorSparks = 0; // spark
    public static int leftCubeIntakeMotor = 6; // spark (2). currently using sparks
    public static int rightCubeIntakeMotor = 5; // spark (3). currently using sparks

    // CAN bus
    public static int pigeon = 0; // pigeonimu

    // DIO (Digital IO)
    public static int lowerElevatorLimitSwitch = 0; // digital input
    public static int upperElevatorLimitSwitch = 4;
    public static int elevatorEncoderA = 1; // encoder
    public static int elevatorEncoderB = 2; // encoder
    public static int rearPingChannel = 5; // ultrasonic
    public static int rearEchoChannel = 6; // ultrasonic
    public static int switchLimitSwitch = 8; // digital input
    public static int latchLimitSwitch = 7;

    // Analog In
    public static int cubeUltrasonicCenter = 0; // ultrasonic
    public static int cubeUltrasonicEdge = 1; // ultrasonic

    // PCM (Pneumatic Control Module)
    public static int compressor = 0;

    public static int gearShiftSolenoidForwardChannel = 0; // solenoid
    public static int gearShiftSolenoidReverseChannel = 1; // solenoid
    public static int latchForwardChannel = 2; // solenoid
    public static int latchReverseChannel = 3; // solenoid

    public static int chainBreakerIn = 6; // solenoid // waiting on electrical for these three
    public static int chainBreakerOut = 7;
    public static int armsForwardChannel = 4; // solenoid
    public static int armsReverseChannel = 5; // solenoid

    // Controls
    public static int driverPort = 0;
    public static int manipulatorPort = 1;

    // Manipulator Buttons

    public static int manipLeftTriggerAxis = 2;
    public static int manipRightTriggerAxis = 3;
    public static int manipRightJoystickVertical = 5;
    public static int manipLeftJoystickVertical = 1;
    // public static int elevatorToExchange = 2;
    public static int toggleElevatorLockButton = 2;
    public static int autoCubeIntakeButton = 3;
    public static int climbButton = 4;
    public static int prepForClimbButton1 = 8;
    public static int prepForClimbButton2 = 9;
    public static int shiftArmsButton = 1;
    public static int halfSpeedEject = 6;

    // Drive buttons
    public static int driveLeftJoystickVertical = 1;
    public static int driveRightJoystickHorizontal = 4;
    public static int shiftButton = 5;
    public static int breakTrigger = 3;
    public static int driveToCubeButton = 6;
}
