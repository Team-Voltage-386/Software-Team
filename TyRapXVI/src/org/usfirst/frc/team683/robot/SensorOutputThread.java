package org.usfirst.frc.team683.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SensorOutputThread extends Thread
{
    double sensorOutputDashboardDelay = 1;
    //PowerDistributionPanel PDP = new PowerDistributionPanel();
    /**
     * Outputs sensor values
     */
    public void run()
    {
	while(true)
	{
    		if(Robot.gyro.getAngle()<=-360||Robot.gyro.getAngle()>=360)
        	{
    	    		Robot.gyro.reset();
        	}
    		/*SmartDashboard.putNumber("PDPFR", PDP.getCurrent(12));
    		SmartDashboard.putNumber("PDPFR", PDP.getCurrent(0));
    		SmartDashboard.putNumber("PDPRL", PDP.getCurrent(13));
    		SmartDashboard.putNumber("PDPRR", PDP.getCurrent(1));*/
    		
        	SmartDashboard.putNumber("Ultrasonic", Robot.ultra.getRangeInches());
    		SmartDashboard.putNumber("Left Encoder", Robot.leftEncodee.getDistance());
    		SmartDashboard.putNumber("Right Encoder", Robot.rightEncodee.getDistance());
    		SmartDashboard.putNumber("Gyro", Robot.gyro.getAngle());
    		SmartDashboard.putBoolean("Shoot Range", Robot.ultra.getRangeInches()>60 && Robot.ultra.getRangeInches()<66);
    		SmartDashboard.putNumber("RT Input", Robot.manipController.getRawAxis(3));
    		Timer.delay(sensorOutputDashboardDelay);
	}
    }
}
