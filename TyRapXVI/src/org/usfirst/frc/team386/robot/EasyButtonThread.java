package org.usfirst.frc.team386.robot;

public class EasyButtonThread extends Thread {
	public boolean isEasyButtonRunning = false;
	
	
	/**
	 * Runs a easy button when the specified button is pressed
	 */
	public void run(){
		while(true){
		    if(Robot.driverstation.isOperatorControl())
		    {
			/*if(Robot.manipController.getRawButton(1))//A
			{
			    isEasyButtonRunning = true;
			    Value previousShift = Robot.driveSolenoid.get();
			    Auto.doPortcullis();
			    Robot.driveSolenoid.set(previousShift);
			    isEasyButtonRunning = false;
			}
		
			if(Robot.manipController.getRawButton(3))//X
			{
			    isEasyButtonRunning = true;
			    Value previousShift = Robot.driveSolenoid.get();
			    Auto.doDrawbridge();
			    Robot.driveSolenoid.set(previousShift);
			    isEasyButtonRunning = false;
			}
			
			if(Robot.manipController.getRawButton(2))//B
			{
			    isEasyButtonRunning = true;
			    Value previousShift = Robot.driveSolenoid.get();
			    //Auto.doSeesaw();
			    Robot.driveSolenoid.set(previousShift);
			    isEasyButtonRunning = false;
			}
			
			if(Robot.manipController.getRawButton(8))//Back
			{
			    Robot.gyro.reset();
			    Robot.leftEncodee.reset();
			    Robot.rightEncodee.reset();
			}*/
		    }
		}
	}
}
