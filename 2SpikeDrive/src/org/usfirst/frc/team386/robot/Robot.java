package org.usfirst.frc.team386.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import Utility.AnalogUltrasonic;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
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
	WPI_TalonSRX left = new WPI_TalonSRX(13);
	WPI_TalonSRX right = new WPI_TalonSRX(14);
	AnalogUltrasonic ultra1 = new AnalogUltrasonic(0,1.18,10.3);
	AnalogUltrasonic ultra2 = new AnalogUltrasonic(1,1.18,10.3);
	Joystick controller = new Joystick(0);
	final String leftName = "Left speed";
	final String rightName = "Right Speed";
	boolean on;

	@Override
	public void robotInit() {
		SmartDashboard.putNumber(leftName, 0);
		SmartDashboard.putNumber(rightName, 0);
		SmartDashboard.putNumber("ultra1", ultra1.getInches());
	}

	@Override
	public void teleopInit() {
		on = false;
	}
	double difference = Math.abs(ultra2.getInches()-ultra1.getInches());
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("ultra2", ultra2.getInches());
		SmartDashboard.putNumber("ultra1", ultra1.getInches());
		if (controller.getRawButtonPressed(2)) {
			SmartDashboard.putNumber(leftName, -1 * SmartDashboard.getNumber(leftName, 0));
			SmartDashboard.putNumber(rightName, -1 * SmartDashboard.getNumber(rightName, 0));
		}
		if (controller.getRawButtonPressed(1)) {
			on = !on;
		}
		double leftOut = SmartDashboard.getNumber(leftName, 0);
		double rightOut = SmartDashboard.getNumber(rightName, 0);
		SmartDashboard.putNumber("Left out", leftOut);
		SmartDashboard.putNumber("Right out", rightOut);
		int rightUltrasonic = (int) (ultra2.getInches()*10);
		int leftUltrasonic = (int) (ultra1.getInches()*10);
		if(controller.getRawButtonPressed(3))
		{
			double startTime = Timer.getFPGATimestamp();
			SmartDashboard.putBoolean("button pressed", true);
			while(Timer.getFPGATimestamp() - .2 < startTime) {
				left.set(leftOut*-1);
				right.set(rightOut*-1);
				SmartDashboard.putBoolean("forward", true);
			}
			left.set(0);
			right.set(0);
			double secondStartTime = Timer.getFPGATimestamp();
			while(Timer.getFPGATimestamp() - .2 < secondStartTime)
			{
				left.set(leftOut);
				right.set(rightOut);
				SmartDashboard.putBoolean("backwards", true);
			}
		}
	/*	if (on) {
			SmartDashboard.putBoolean("button pressed", on);
			if (SmartDashboard.getNumber(leftName, 0) > 0) {
				if (ultra2.getInches() > 2) {
					if(rightUltrasonic > leftUltrasonic) //ultra2 = right, ultra1 = left
					{
						left.set(leftOut*50);
						right.set(rightOut);
					}
					else if(leftUltrasonic > rightUltrasonic)
					{
						left.set(leftOut);
						right.set(rightOut*.50);
					}
					else if(leftUltrasonic == rightUltrasonic) {
					left.set(leftOut);
					right.set(rightOut);
					}
				} else {
					left.set(0);
					right.set(0);
				}
			} else {
				left.set(leftOut);
				right.set(rightOut);
			}
		} else {
			left.set(0);
			right.set(0);
		}
	}*/
	left.set(controller.getRawAxis(1));
	right.set(controller.getRawAxis(5));
}
 
}