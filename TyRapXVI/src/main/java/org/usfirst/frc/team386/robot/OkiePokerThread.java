package org.usfirst.frc.team386.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

public class OkiePokerThread extends Thread
{
    boolean currentState;
    boolean previousState;
    
    public enum State{
	DOWN, MIDDLE, UP
    }
    
    
    public void run()
    {
	while (true)
	{
	    if(Robot.driverstation.isOperatorControl())
	    {
        	 if(Robot.manipController.getPOV() == 0)
        	 {
        		setState(State.UP);
        	 }
        	 else if(Robot.manipController.getPOV() == 180)
        	 {
        	     	setState(State.DOWN);
        	 }
        	 else if(Robot.manipController.getPOV() == 90)
        	 {
        	     	setState(State.MIDDLE);
        	 }
	    }
    	}
    }
    
    //This function is isolated so that it can be called outside the thread
    public void setState(State desiredState)
    {
	switch(desiredState){
	case DOWN:
	default:
	    Robot.okiePokerAirSolenoid.set(DoubleSolenoid.Value.kForward);
	    Robot.okiePokerControlSolenoid.set(DoubleSolenoid.Value.kReverse);
	    break;
	case MIDDLE:
	    Robot.okiePokerAirSolenoid.set(DoubleSolenoid.Value.kForward);
	    if(Robot.okiePokerControlSolenoid.get().equals(DoubleSolenoid.Value.kForward))
		 {
			Robot.okiePokerControlSolenoid.set(DoubleSolenoid.Value.kReverse);
		 }
	    else if(Robot.okiePokerControlSolenoid.get().equals(DoubleSolenoid.Value.kReverse))
	    {
		Robot.okiePokerControlSolenoid.set(DoubleSolenoid.Value.kForward);
	    }
	    Timer.delay(1);
	    Robot.okiePokerAirSolenoid.set(DoubleSolenoid.Value.kReverse);
	    
	    break;
	case UP:
	    Robot.okiePokerAirSolenoid.set(DoubleSolenoid.Value.kForward);
	    Robot.okiePokerControlSolenoid.set(DoubleSolenoid.Value.kForward);
	    break;
	}
    }
}
