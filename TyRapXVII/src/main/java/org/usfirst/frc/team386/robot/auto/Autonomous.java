package org.usfirst.frc.team386.robot.auto;

import org.usfirst.frc.team386.robot.Constants;
import org.usfirst.frc.team386.robot.IO;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;

/**
 * Oh boy Welcome to hell
 * 
 * @author Team 386
 */
public class Autonomous
{
    public static void run()
    {
	DriverStation.reportWarning("reseting gyro", false);
	IO.gyro.reset();
	String selectedAutoMode = (String) IO.autoChooser.getSelected();
	switch (selectedAutoMode)
	{
	case Constants.martianRock:
	default:
	    DriverStation.reportWarning("Going into martian rock", false);
	    martianRock();
	    break;
	case Constants.driveForward:
	    driveForward();
	    break;
	case Constants.frontPeg:
	    frontPeg();
	    break;
	case Constants.boilerSidePeg:
	    boilerSidePeg();
	    break;
	case Constants.feederStationSidePeg:
	    DriverStation.reportWarning("Going into side peg", false);
	    feederStationSidePeg();
	    break;
	case Constants.boilerShoot:
	    boilerShoot();
	    break;
	}
    }

    /**
     * Does nothing
     */
    private static void martianRock()
    {
    }

    /**
     * Moves forward a set amount of encoder ticks
     */
    private static void driveForward()
    {
	AutoFunctions.driveForward(5000, AutoFunctions.driveSensorType.encoderInRawTicks);
    }

    private static void frontPeg()
    {
	AutoFunctions.dropGear();
	//AutoFunctions.gearVision();
    }

    private static void boilerSidePeg()
    {
	AutoFunctions.driveForward(4700, AutoFunctions.driveSensorType.encoderInRawTicks);
	AutoFunctions.turnOneMotor(43, AutoFunctions.accountForSide(AutoFunctions.direction.clockwise));
	Timer.delay(1);
	AutoFunctions.gearVision();

    }

    private static void boilerShoot()
    {
	System.out.println("shooting");
	AutoFunctions.shoot(8);
	String selectedAutoMode = (String) IO.sideChooser.getSelected();
	System.out.println(selectedAutoMode);
	switch(selectedAutoMode)
	{
	case Constants.red:
	    System.out.println("red");
	    while (IO.gyro.getAngle() < 35)
	    {
		IO.drivetrain.tankDrive(Constants.baseTurnSpeed * Constants.forwards, Constants.baseTurnSpeed * Constants.backwards);
		if (RobotState.isOperatorControl())
		{
		    return;
		}
	    }
	    Timer.delay(1);
	    AutoFunctions.driveForward(6000, AutoFunctions.driveSensorType.encoderInRawTicks);
	    break;
	case Constants.blue:
	    IO.gyro.reset();
	    while (IO.gyro.getAngle() > -20)
	    {
		IO.drivetrain.tankDrive(0.4 * Constants.forwards, 0.7 * Constants.forwards);
		if (RobotState.isOperatorControl())
		{
		    return;
		}
	    }
	    IO.gyro.reset();
	    while (IO.gyro.getAngle() < 60)
	    {
		IO.drivetrain.tankDrive(Constants.baseTurnSpeed * Constants.forwards, Constants.baseTurnSpeed * Constants.backwards);
		if (RobotState.isOperatorControl())
		{
		    return;
		}
	    }
	    Timer.delay(1);
	    AutoFunctions.driveBackwards(6000, AutoFunctions.driveSensorType.encoderInRawTicks);
	    break;
	}   
    }

    private static void feederStationSidePeg()
    {
	AutoFunctions.driveForward(4500, AutoFunctions.driveSensorType.encoderInRawTicks);
	AutoFunctions.turn(25.8, AutoFunctions.accountForSide(AutoFunctions.direction.counterClockwise));
	Timer.delay(1);
	AutoFunctions.gearVision();
    }
}
