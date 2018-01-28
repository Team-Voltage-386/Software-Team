/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team386.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;  // Import the I2C Library

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot 
{

	public static I2C arduino;  //create initial I2C object, assign name.

	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override //Keep all of these in place.
	public void robotInit() 
	{
		arduino = new I2C(I2C.Port.kOnboard, 8);  //when robot turns on, assign arduino to the onboard I2C bus, and assign it port #8.
		//NOTE: All LED functions should only be passed once per action. Placing them in the init functions ensures this. 
	}
	
	@Override  
	public void disabledInit()
	{
		Robot.UpdateLEDs("DISABLED");  //Passing DISABLED string to UpdateLEDs
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	
	@Override
	public void autonomousInit()
	{
		Robot.UpdateLEDs("AUTO"); //Passing AUTO string to UpdateLEDs
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() 
	{

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopInit() 
	{
		Robot.UpdateLEDs("TELEOP");  ////Passing TELEOP string to UpdateLEDs
	}
	@Override
	public void teleopPeriodic() 
	{

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() 
	{
	}
	
	
	//Here is the UpdateLEDs function.  This passes the string to the Ard/RIOduino.
	static void UpdateLEDs(String WriteString) // Constructor, pass it a string argument.
	{
		char[] CharArray = WriteString.toCharArray();  //Create an array of characters.  This breaks up the information into something that can be passed over the I2C bus.
		byte[] RobotStatus = new byte[CharArray.length]; //Characters cannot be passed over I2C, thus we must convert them to bytes. This line creates the byte array.  
		for (int i = 0; i < CharArray.length; i++)//Create a loop that fills the new  byte array. The new byte array is the same size as the character array. 
		{
			RobotStatus[i] = (byte) CharArray[i];  //Pass information slot by slot. This also converts the characters into bytes.
		}
		
		//arduino.transaction(RobotStatus, RobotStatus.length, null, 0);  //One type of sending info over the I2C bus.  This method asks for a response from the receiving unit. Caused null point exceptions. 
		arduino.writeBulk(RobotStatus, RobotStatus.length); //This method sends info one way, without demanding a response from reader unit. 
		
		
	}
	
}
