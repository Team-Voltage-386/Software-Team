package org.usfirst.frc.team683.robot;

import edu.wpi.first.wpilibj.Relay;

public class CameraMoveThread extends Thread {
	int middleAngle = 74;
	int upAngle = 32;
	int downAngle = 116;
	int cameraMoveSpeed = 3;
	/**
	 * Moves the camera based off the POV on top of the left joystick
	 */
	public void run(){
		Robot.cameraServo.setAngle(middleAngle);
		while(true){
		    if(Robot.driverstation.isOperatorControl())
		    {
			if(Robot.leftJoystick.getPOV() == 0)
	        	 {
			    Robot.cameraServo.setAngle(upAngle);
	        	 }
	        	 else if(Robot.leftJoystick.getPOV() == 180)
	        	 {
	        	     Robot.cameraServo.setAngle(downAngle);
	        	 }
	        	 else if(Robot.leftJoystick.getPOV() == 90 || Robot.leftJoystick.getPOV() == 270)
	        	 {
	        	     Robot.cameraServo.setAngle(middleAngle);
	        	 }
        		
			 Robot.light.set(Relay.Value.kForward);
		    }
		}
		
	}

}
