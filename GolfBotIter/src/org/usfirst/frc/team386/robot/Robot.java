/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team386.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();


    Spark Swing1;
    Joystick Club;
    Encoder golfEncoder;
    int modifier,maxenc,minenc;
	
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		//SmartDashboard.putData("Auto choices", m_chooser);
		
		golfEncoder = new Encoder(5, 6, false, Encoder.EncodingType.k4X);
		golfEncoder.reset();
		//Swing1 = new Victor (2);
		Swing1 = new Spark (3);
		Club = new Joystick(0);
		//PowerDistributionPanel PDP = new PowerDistributionPanel(0);
		boolean isInverted  = true;
		Swing1.setInverted(isInverted);
		modifier = 1;
		maxenc = 100;
		minenc = -100;
	}

	@Override
	public void autonomousInit() 
	{
	}

	@Override
	public void autonomousPeriodic() 
	{
	}

	@Override
	public void teleopPeriodic() 
	{
		double clubPower = Club.getRawAxis(1);
		SmartDashboard.putNumber("Golf Encoder", golfEncoder.getRaw());
		SmartDashboard.putNumber("Club Power Actual", clubPower);
		SmartDashboard.putNumber("Power", modifier);
		SmartDashboard.putNumber("MaxEnc", maxenc );
		SmartDashboard.putNumber("MinEnc", minenc);
		SmartDashboard.getNumber("Power", 1);
		SmartDashboard.getNumber("MaxEnc", 100);
		SmartDashboard.getNumber("MinEnc", -100);
		
		
		if(Club.getRawButton(1))
		{
			golfEncoder.reset();
		}
		
		if(Club.getRawButton(2))
		{
			while(golfEncoder.getRaw() > 10 || golfEncoder.getRaw() < -10)
			{
				System.out.println("In correction loop");
				
				if(golfEncoder.getRaw() > 5)
				{
					System.out.println("Greater than");
					Swing1.set(-0.2);
					Timer.delay(0.1);
				}
				if(golfEncoder.getRaw() < -5)
				{
					System.out.println("Less than");
					Swing1.set(0.2);
					Timer.delay(0.1);
				}
				Timer.delay(0.1);
				System.out.println("Bottom of Correction loop");
			}
		}
				
		if(golfEncoder.getDistance() > maxenc)
		{
			clubPower = Math.min(clubPower, 0);
		}
		else if(golfEncoder.getDistance() < minenc)
		{
			clubPower = Math.max(clubPower, 0);	
		}

		Swing1.set((clubPower/modifier));
	}

	@Override
	public void testPeriodic() {
	}
}
