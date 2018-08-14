package org.usfirst.frc.team683.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveThread extends Thread {

	boolean shifted = true; //True is high speed
	boolean currentState;
	boolean previousState;
	
	/**
	 * Drives the robot. Tests for shift and then sets motor outputs.
	 */
	
	public void run(){
		double left;
		double right;
		previousState = false;
		while (true)
		{
		    //Shifting
		    if(Robot.driverstation.isOperatorControl() && !Robot.easyButton.isEasyButtonRunning)
		    {
			currentState = Robot.leftJoystick.getTrigger();
			if (currentState == true && previousState == false) //if the left joystick is pressed, change the state of shift
			{
			    shifted = !shifted;
			}
			if (shifted)
			{
				Robot.driveSolenoid.set(DoubleSolenoid.Value.kForward);
			}
			if (!shifted)
			{
				Robot.driveSolenoid.set(DoubleSolenoid.Value.kReverse);
			}
			previousState = currentState;
			SmartDashboard.putString("Shifted", shifted+"");
        		//Movement Itself
        		left = Robot.leftJoystick.getY();
        		right = Robot.rightJoystick.getY();
        		if(Robot.rightJoystick.getRawButton(3))
        		{
        		    left = deadAndMult(left, 0.2, 0.75);
        		    right = deadAndMult(right, 0.2, 0.75);
        		}
        		else
        		{
        		    left = deadAndMult(left, 0.2, 1);
        		    right = deadAndMult(right, 0.2, 1);
        		}
        		if(left > 0 && right > 0) //Negative is forward
        		{
        			Robot.drivetrain.tankDrive(left, right*Robot.differential);
        		}
        		else if(left < 0 && right < 0)
        		{
        			Robot.drivetrain.tankDrive(left*Robot.differential, right);
        		}
        		else
        		{
        			Robot.drivetrain.tankDrive(left, right);
        		}
		    }
		}
	}
	
	public double deadAndMult(double original, double deadband, double mult){
		double ret;
		if(Math.abs(original)<deadband)
			ret = 0;
		else
			ret = original*mult;
		return ret;
	}
}
