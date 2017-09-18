package org.usfirst.frc.team386.robot;

import org.usfirst.frc.team386.robot.auto.AutoFunctions;
import org.usfirst.frc.team386.robot.auto.Autonomous;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.ButtonHoldSubsystem;
import utility.ButtonLatchSubsystem;
import utility.Subsystem;
import utility.TeleopSubsystem;

/**
 * TODO Change camera resolution back, change center gear to not use vision
 * 2017
 * @author TeamVoltage
 *
 *         ---Old Battery-- Pressurized Bumper Colour Velcro Rope Guard Winch
 *         Ratched Engaged Check Rope Both sides same gear (high) Gear mech
 *         closed Gear drop closed Bands in line Check wheel rub ---Change
 *         Battery--- Radio Plugged in Battery Strap Skins ---Back up when you
 *         get the gear---
 */
public class Robot extends IterativeRobot
{
	//Here is a test comment to check github functionality. 
    public boolean isDriving = true;
    Object drive = new TeleopSubsystem()
    {
	boolean direction = true;
	boolean currentState;
	boolean previousState;

	public void loop()
	{
	    currentState = IO.leftJoystick.getRawButton(2) || IO.rightJoystick.getRawButton(2);
	    if (currentState && !previousState)
	    {
		direction = !direction;

		if (!direction)
		{
		    IO.cameraLight.set(Value.kForward);
		} else
		{
		    IO.cameraLight.set(Value.kOff);
		}

	    }
	    previousState = currentState;
	    if (direction && isDriving)
	    {
		IO.drivetrain.tankDrive(IO.leftJoystick.getY(), IO.rightJoystick.getY());
	    } else if (isDriving)
	    {
		IO.drivetrain.tankDrive(IO.rightJoystick.getY() * -1, IO.leftJoystick.getY() * -1);

	    }
	}
    };

    Object agitator = new TeleopSubsystem()
    {
	public void loop()
	{
	    if (IO.manip.getRawButton(Constants.agitatorButton) || IO.manip.getRawButton(Constants.shootButton))
	    {
		IO.agitator.set(Relay.Value.kForward);
	    } else
	    {
		IO.agitator.set(Relay.Value.kOff);
	    }
	}
    };
    Object gear = new TeleopSubsystem()
    {
	public void loop()
	{
	    while (IO.manip.getRawButton(Constants.gearButton))
	    {
		IO.gearDelivery.set(DoubleSolenoid.Value.kReverse);
		Timer.delay(.5);
		IO.cameraLight.set(Value.kForward);
		Timer.delay(3);
		IO.cameraLight.set(Value.kReverse);
	    }
	    IO.gearDelivery.set(DoubleSolenoid.Value.kForward);
	}
    };

    Object shooter = new TeleopSubsystem()
    {
	public void loop()
	{

	    while (IO.manip.getRawButton(Constants.shootButton))
	    {
		IO.shooter.setSpeed(SmartDashboard.getNumber("Shooter motor speed", Constants.shooterMotorSpeed));
		Timer.delay(Constants.spinnupTime);
		IO.trigger.setSpeed(SmartDashboard.getNumber("Trigger motor speed", Constants.triggerMotorSpeed));
	    }
	    IO.shooter.setSpeed(0);
	    IO.trigger.setSpeed(0);
	}
    };
    Object dashboard = new Subsystem()
    {
	public void loop()
	{
	    // Debug Readouts
	    SmartDashboard.putString("Selected Autonomous", IO.autoChooser.getSelected());
	    SmartDashboard.putNumber("Camera error", AutoFunctions.getGearError());
	    // Sensor Readouts
	    // SmartDashboard.putNumber("Shooter Ultrasonic value",
	    // IO.shooterUltrasonic.getInches());
	    // SmartDashboard.putNumber("Gear US",
	    // IO.gearUltrasonic.getInches());
	    // SmartDashboard.putNumber("Left Encoder Rate",
	    // IO.leftEncodee.getRate());
	    // SmartDashboard.putNumber("Right Encoder Raw Value",
	    // IO.rightEncodee.getRaw());
	    SmartDashboard.putNumber("Gyro Value", IO.gyro.getAngle());
	    // SmartDashboard.putNumber("Raw Axis 5", IO.manip.getRawAxis(5));

	    // Testing inputs
	}
    };

    Object pickup = new TeleopSubsystem()
    {
	public void loop()
	{
	    IO.ballPickup.set(IO.manip.getRawAxis(3) * -1000);
	}
    };

    Object climber = new TeleopSubsystem()
    {
	public void loop()
	{
	    if (IO.manip.getRawButton(Constants.climbButton) || IO.manip.getRawAxis(2) > 0)
	    {
		IO.climber.set(-1);
	    } else
	    {
		IO.climber.set(0);
	    }
	}
    };
    /*
     * Object autoGearDrop = new TeleopSubsystem(){ public void loop() {
     * if(IO.rightJoystick.getRawButton(Constants.autoGearStart)){ isDriving =
     * false; AutoFunctions.gearVision(); isDriving = true; } } };
     */

    Object decoLightControl = new TeleopSubsystem()
    {
	public void loop()
	{
	    if (IO.gearUltrasonic.getInches() < 7)
	    {
		IO.decorationLight.set(Value.kOff);
	    } else
	    {
		IO.decorationLight.set(Value.kForward);
	    }
	}
    };

    Object driveShift = new ButtonLatchSubsystem(IO.leftJoystick, Constants.shiftButton, IO.shifter,
	    DoubleSolenoid.Value.kReverse, DoubleSolenoid.Value.kForward);
    Object gearCollector = new ButtonHoldSubsystem(IO.manip, 6, IO.gearCollection, DoubleSolenoid.Value.kReverse,
	    DoubleSolenoid.Value.kForward);

    @Override
    public void autonomousInit()
    {
	IO.camera.setResolution(160, 120);
	IO.cameraLight.set(Value.kForward);
	IO.gearCollection.set(DoubleSolenoid.Value.kForward);
	Autonomous.run();
    }

    @Override
    public void teleopInit()
    {
	IO.camera.setResolution(Constants.mainCameraWidth, Constants.mainCameraWidth / 4 * 3);
	IO.cameraLight.set(Value.kOff);
    }

    @Override
    public void autonomousPeriodic()
    {

    }
}
