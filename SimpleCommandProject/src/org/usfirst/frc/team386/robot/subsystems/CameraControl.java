package org.usfirst.frc.team386.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CameraControl extends Subsystem {

    Servo servo = new Servo(0); 
    
    public void panleft()
    {
	servo.set(0);
    }
    public void panright()
    {
	servo.set(1);
    }
    
    public void initDefaultCommand()
    {
      
  
	
    }
}

