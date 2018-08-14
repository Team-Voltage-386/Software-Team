package org.usfirst.frc.team386.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

public class ShootThread extends Thread {
	public boolean isShooting =  false;
	/**
	 * Shoots when a button is pressed
	 */
	
	public void run(){
		while (true)
		{
		    if(Robot.driverstation.isOperatorControl())
		    {
			
			if (Robot.manipController.getRawAxis(3)>0.9)
			{
			    shootOnce();
			}
			
			if (Robot.manipController.getRawButton(5 /*LeftBumper*/))
			{
			    reset();
			}
		    }
		}
	}
	//This function is isolated so that it can be called outside the thread
	public void shootOnce()
	{
	    isShooting = true;
	    double timeStarted = Timer.getFPGATimestamp();
	    while (timeStarted > Timer.getFPGATimestamp()-3.5)
	    {
        	    Robot.pickup.set(-1);
	    }
	    Robot.pickup.set(0);
	    Robot.shootLockSolenoid.set(DoubleSolenoid.Value.kReverse);
	    Timer.delay(1);//Delay before reset
	    Robot.shootResetSolenoid.set(DoubleSolenoid.Value.kForward);
	    /*while(!Robot.resetLimit.get())
	    {
		Auto.martianRock();
	    }*/
	    Timer.delay(2.5);
	    Robot.shootLockSolenoid.set(DoubleSolenoid.Value.kForward);
	    Timer.delay(0.5);//Delay before unretracting reset
	    Robot.shootResetSolenoid.set(DoubleSolenoid.Value.kReverse);
	    Timer.delay(1);//This final timer prevents shooting before the shooter resets
	    isShooting = false;
	}
	
	public void reset()
	{
	    Robot.shootLockSolenoid.set(DoubleSolenoid.Value.kReverse);
	    Robot.shootResetSolenoid.set(DoubleSolenoid.Value.kForward);
	    Timer.delay(2.5);
	    Robot.shootLockSolenoid.set(DoubleSolenoid.Value.kForward);
	    Timer.delay(1.5);
	    Robot.shootResetSolenoid.set(DoubleSolenoid.Value.kReverse);
	    Timer.delay(1);//This final timer prevents shooting before the shooter resets
	}
}
