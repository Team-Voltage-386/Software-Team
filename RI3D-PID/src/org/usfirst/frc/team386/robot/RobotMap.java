package org.usfirst.frc.team386.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static int backLeftDrive = 0;
	public static int frontLeftDrive = 1;
	public static int backRightDrive = 2;
	public static int frontRightDrive = 3;
	
	public static int PCM = 25;
	public static int shotPinForward = 25;
	public static int shotPinReverse = 25;
}
