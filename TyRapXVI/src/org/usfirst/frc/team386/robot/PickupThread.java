package org.usfirst.frc.team386.robot;

import edu.wpi.first.wpilibj.Joystick;

public class PickupThread extends Thread {
    	double manipControllerValue;
	
    	/**
    	 * Moves the motor based off the Y value from the manipulator joystick
    	 */
    	public enum State{
    	noBall, limitSwitchToggled, ballIn, ballOnTheWay
        }
    	
	public void run(){
		while(true)
		{
		    if(Robot.driverstation.isOperatorControl() && !Robot.shooter.isShooting)//This if statement checks to see if the robot is in teleop and is not currently performing a shoot
		    {
			manipControllerValue = Robot.manipController.getRawAxis(1);//Pulls analog stick value from xbox controller
			System.out.println(Robot.pickupLimit.get()); //Print out 
			System.out.println(manipControllerValue); 
			manipControllerValue = Robot.drive.deadAndMult(manipControllerValue,0.2,-1.0); //Adds a dead band to the value
			if(!Robot.pickupLimit.get() && manipControllerValue<0)
			{
			    manipControllerValue = 0;
			}
			Robot.pickup.set(manipControllerValue);
			
			
			
	                if(Robot.driverstation.getMatchTime()<=25 && Robot.driverstation.getMatchTime()>=24)
	                {
	                    	Robot.manipController.setRumble(Joystick.RumbleType.kLeftRumble, 1);
	                    	Robot.manipController.setRumble(Joystick.RumbleType.kRightRumble, 1);
	                }
	                else
	                {
	                    Robot.manipController.setRumble(Joystick.RumbleType.kLeftRumble, 0);
	                    Robot.manipController.setRumble(Joystick.RumbleType.kRightRumble, 0);
	                  
	                }
			
		    }
		    
		}
	}
}
