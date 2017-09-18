package org.usfirst.frc.team386.robot.auto;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team386.robot.Constants;
import org.usfirst.frc.team386.robot.IO;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * basic auto functions for auto modes to be comprised of I'm sorry I thought I
 * was making something easier lord oh lord I was wrong
 * 
 * @author Team386
 */
public class AutoFunctions
{
    /**
     * Which sensor and increment to use
     * 
     * @author TeamVoltage
     *
     */
    public static enum driveSensorType
    {
	encoderInRawTicks, ultrasonicInInches, gearLimitSwitch
    }

    /**
     * drives forwards the specified encoder ticks or until it is a certain
     * distance away based on which of the second parameter is passed
     * 
     * @param distance
     * @param type
     */
    public static void driveForward(double distance, driveSensorType type)
    {
	driveForward(distance, type, Constants.baseAutoSpeed);

	/*
	 * SmartDashboard.putString("Drive forward state", "Resetting");
	 * IO.leftEncodee.reset(); IO.gyro.reset(); while
	 * ((Math.abs(IO.leftEncodee.getRaw()) < Math.abs(distance) && type ==
	 * driveSensorType.encoderInRawTicks) ||
	 * (IO.shooterUltrasonic.getInches() > distance && type ==
	 * driveSensorType.ultrasonicInInches) || (type ==
	 * driveSensorType.gearLimitSwitch && (IO.rightGearRelease.get() ||
	 * IO.leftGearRelease.get() || Math.abs(IO.leftEncodee.getRaw()) <
	 * Math.abs(distance)))) { SmartDashboard.putNumber("Left encoder",
	 * IO.leftEncodee.getRaw());
	 * SmartDashboard.putString("Drive forward state", "Driving");
	 * IO.drivetrain.arcadeDrive(Constants.forwards *
	 * Constants.baseAutoSpeed, Constants.gyroCompensation *
	 * IO.gyro.getAngle()); if (RobotState.isOperatorControl()) { return; }
	 * } IO.drivetrain.tankDrive(0, 0);
	 * SmartDashboard.putString("Drive forward state", "Stopped");
	 */

    }

    /**
     * drives backwards the specified encoder ticks or until it is a certain
     * distance away based on which of the second parameter is passed
     * 
     * @param distance
     * @param type
     */
    public static void driveBackwards(double distance, driveSensorType type)
    {
	driveBackwards(distance, type, Constants.baseAutoSpeed);
	/*
	 * IO.leftEncodee.reset(); IO.gyro.reset(); while
	 * ((Math.abs(IO.leftEncodee.getRaw()) <= (Math.abs(distance)) && type
	 * == driveSensorType.encoderInRawTicks) ||
	 * (IO.gearUltrasonic.getInches() <= distance && type ==
	 * driveSensorType.ultrasonicInInches) || (type ==
	 * driveSensorType.gearLimitSwitch && (IO.rightGearRelease.get() ||
	 * IO.leftGearRelease.get() || Math.abs(IO.leftEncodee.getRaw()) <
	 * Math.abs(distance)))) { SmartDashboard.putNumber("Left encoder",
	 * IO.leftEncodee.getRaw());
	 * IO.drivetrain.arcadeDrive(Constants.backwards *
	 * Constants.baseAutoSpeed, Constants.gyroCompensation *
	 * IO.gyro.getAngle()); if (RobotState.isOperatorControl()) { return; }
	 * 
	 * }
	 */

    }

    public static void driveForward(double distance, driveSensorType type, double speed)
    {
	SmartDashboard.putString("Drive forward state", "Resetting");
	IO.leftEncodee.reset();
	IO.gyro.reset();
	while ((Math.abs(IO.leftEncodee.getRaw()) <= Math.abs(distance) && type == driveSensorType.encoderInRawTicks)
		|| (IO.gearUltrasonic.getInches() >= distance && type == driveSensorType.ultrasonicInInches)
		|| (type == driveSensorType.gearLimitSwitch && ((IO.rightGearRelease.get() || IO.leftGearRelease.get()
			|| Math.abs(IO.leftEncodee.getRaw()) < Math.abs(distance)))))
	{
	    IO.drivetrain.arcadeDrive(Constants.forwards * speed, Constants.gyroCompensation * IO.gyro.getAngle());
	    if (RobotState.isOperatorControl())
	    {
		return;
	    }
	}
    }

    public static void driveBackwards(double distance, driveSensorType type, double speed)
    {
	IO.leftEncodee.reset();
	IO.gyro.reset();
	while ((Math.abs(IO.leftEncodee.getRaw()) <= (Math.abs(distance)) && type == driveSensorType.encoderInRawTicks)
		|| (IO.shooterUltrasonic.getVoltage() <= distance && type == driveSensorType.ultrasonicInInches)
		|| (type == driveSensorType.gearLimitSwitch && (IO.rightGearRelease.get() || IO.leftGearRelease.get()
			|| Math.abs(IO.leftEncodee.getRaw()) < Math.abs(distance))))
	{
	    SmartDashboard.putNumber("Left encoder", IO.leftEncodee.getRaw());
	    IO.drivetrain.arcadeDrive(Constants.backwards * speed, Constants.gyroCompensation * IO.gyro.getAngle());
	    if (RobotState.isOperatorControl())
	    {
		return;
	    }

	}

    }

    public static void forwardSec(double sec)
    {
	double startTime = Timer.getFPGATimestamp();
	while (Timer.getFPGATimestamp() - sec < startTime)
	{
	    IO.drivetrain.arcadeDrive(1, Constants.gyroCompensation * IO.gyro.getAngle());
	}
    }

    public static void backwardsSec(double sec)
    {
	double startTime = Timer.getFPGATimestamp();
	while (Timer.getFPGATimestamp() - sec < startTime)
	{
	    IO.drivetrain.arcadeDrive(-1, Constants.gyroCompensation * IO.gyro.getAngle());
	}

    }

    /**
     * which direction you are turning
     * 
     * @author TeamVoltage
     *
     */
    public static enum direction
    {
	clockwise, counterClockwise
    }

    /**
     * Turns the specified degrees in the specified direction.
     * 
     * @param degreesToTurn
     * @param directionToTurn
     */
    public static void turn(double degreesToTurn, direction directionToTurn)
    {
	IO.gyro.reset();
	double timeStarted = Timer.getFPGATimestamp();
	double currentTime;
	while (IO.gyro.getAngle() <= Math.abs(degreesToTurn) && directionToTurn == direction.clockwise)
	{
	    if (IO.autoChooser.getSelected().equals(Constants.boilerShoot))
	    {
		currentTime = Timer.getFPGATimestamp();
		if (currentTime - timeStarted > 3)
		{
		    return;
		}
	    }
	    IO.drivetrain.tankDrive(Constants.forwards * Constants.baseTurnSpeed,
		    Constants.backwards * Constants.baseTurnSpeed);
	    if (RobotState.isOperatorControl())
	    {
		return;
	    }
	}
	while (IO.gyro.getAngle() >= -1 * Math.abs(degreesToTurn) && directionToTurn == direction.counterClockwise)
	{
	    IO.drivetrain.tankDrive(Constants.backwards * Constants.baseTurnSpeed,
		    Constants.forwards * Constants.baseAutoSpeed);
	    if (RobotState.isOperatorControl())
	    {
		return;
	    }
	}
    }

    public static void turnOneMotor(double degreesToTurn, direction directionToTurn)
    {
	IO.gyro.reset();
	System.out.println(IO.gyro.getAngle());
	while (IO.gyro.getAngle() <= Math.abs(degreesToTurn) && directionToTurn == direction.clockwise)
	{
	    IO.drivetrain.tankDrive(Constants.forwards * Constants.baseTurnSpeed, 0);
	    if (RobotState.isOperatorControl())
	    {
		return;
	    }
	}
	while (IO.gyro.getAngle() >= -1 * Math.abs(degreesToTurn) && directionToTurn == direction.counterClockwise)
	{
	    IO.drivetrain.tankDrive(0, Constants.forwards * Constants.baseTurnSpeed);
	    if (RobotState.isOperatorControl())
	    {
		return;
	    }
	}
    }

    public static void shoot(double secondsToShoot)
    {
	double timeStart = Timer.getFPGATimestamp();
	while (timeStart > Timer.getFPGATimestamp() - secondsToShoot)
	{
	    IO.agitator.set(Relay.Value.kForward);
	    IO.shooter.setSpeed(SmartDashboard.getNumber("Shooter motor speed", Constants.shooterMotorSpeed));
	    Timer.delay(Constants.spinnupTime);
	    IO.trigger.setSpeed(SmartDashboard.getNumber("Triggger motor speed", Constants.triggerMotorSpeed));
	    SmartDashboard.putNumber("Shooter Rate", IO.shooter.getSpeed());
	    if (RobotState.isOperatorControl())
	    {
		return;
	    }
	}
	IO.agitator.set(Relay.Value.kOff);
	IO.shooter.setSpeed(0);
	IO.trigger.setSpeed(0);
    }

    public static void dropGear()
    {
	IO.gyro.reset();
	// IO.gyro.calibrate();
	while (IO.gearUltrasonic.getInches() > 5)
	{
	    IO.drivetrain.arcadeDrive(Constants.forwards * Constants.baseAutoSpeed,
		    Constants.gyroCompensation * IO.gyro.getAngle());
	    if (RobotState.isOperatorControl())
	    {
		return;
	    }
	}
	IO.drivetrain.tankDrive(0, 0);
	IO.gearDelivery.set(DoubleSolenoid.Value.kReverse);
	Timer.delay(1);
	driveBackwards(500, driveSensorType.encoderInRawTicks);

    }

    public static direction accountForSide(AutoFunctions.direction directionIn)
    {
	if (IO.sideChooser.getSelected().equals(Constants.red))
	{
	    if (directionIn.equals(direction.clockwise))
		return direction.counterClockwise;
	    else
		return direction.clockwise;
	} else
	    return directionIn;
    }

    public static direction accountForAll()
    {
	// I know there is an easier way to do this and I dont even know if this
	// works
	if (IO.autoChooser.getSelected().equals(Constants.boilerSidePeg)
		&& IO.sideChooser.getSelected().equals(Constants.red))
	{
	    return direction.counterClockwise;
	} else if (IO.autoChooser.getSelected().equals(Constants.feederStationSidePeg)
		&& IO.sideChooser.getSelected().equals(Constants.red))
	{
	    return direction.clockwise;
	} else if (IO.autoChooser.getSelected().equals(Constants.boilerSidePeg)
		&& IO.sideChooser.getSelected().equals(Constants.blue))
	{
	    return direction.clockwise;
	} else
	{
	    return direction.counterClockwise;
	}
    }

    public static void gearVision()
    {
	double turn;
	double error = getGearError();
	int x = 0;
	double speed;
	IO.camera.setResolution(160, 120);
	while (error == 0.1)
	{
	    error = getGearError();
	    if (accountForAll() == direction.clockwise)
	    {
		IO.drivetrain.tankDrive(Constants.forwards * Constants.baseTurnSpeed, 0);
		if (RobotState.isOperatorControl())
		{
		    return;
		}
	    }
	    if (accountForAll() == direction.counterClockwise)
	    {
		IO.drivetrain.tankDrive(0, Constants.forwards * Constants.baseTurnSpeed);
		if (RobotState.isOperatorControl())
		{
		    return;
		}
	    }
	}
	while (IO.gearUltrasonic.getInches() > 7)
	{
	    x++;
	    SmartDashboard.putNumber("vision loop count", x);
	    error = getGearError();
	    if (Math.abs(error) >= 9)
	    {
		speed = 0;
		turn = (error / Math.abs(error))
			* SmartDashboard.getNumber("Pixel Compensation", Constants.pixelCompensation);
	    } else
	    {
		speed = -.7;
		turn = 0;
	    }
	    IO.drivetrain.arcadeDrive(speed, turn);
	    if (RobotState.isOperatorControl())
		return;
	}

	IO.gearDelivery.set(DoubleSolenoid.Value.kReverse);
	Timer.delay(1);
	while (IO.gearUltrasonic.getInches() < 7)
	{
	    IO.drivetrain.tankDrive(0.7, 0.7);
	    if (RobotState.isOperatorControl())
		return;
	}

    }

    public static double getGearError()
    {
	utility.GripPipeline pipeline = new utility.GripPipeline();
	Mat image = new Mat();
	Rect target1;
	Rect target2;
	Rect leftTarget;
	Rect rightTarget;
	Rect combinedTarget;
	int objectIndex1;
	int objectIndex2;
	ArrayList<Rect> contourList = new ArrayList<Rect>();
	double error = 0;
	IO.camera.setResolution(160, 120);
	if (IO.cameraSink.grabFrame(image) == 0)
	{
	    DriverStation.reportWarning("No image", false);
	    return 0;
	}
	pipeline.process(image);
	SmartDashboard.putNumber("contours detected post filter", pipeline.filterContoursOutput().size());
	System.out.println(pipeline.filterContoursOutput().size());
	contourList.clear();
	if (pipeline.filterContoursOutput().size() >= 2)
	{
	    objectIndex1 = 0;
	    objectIndex2 = 1;
	    // smallestDistance = 1000;
	    for (MatOfPoint i : pipeline.filterContoursOutput())
	    {
		contourList.add(Imgproc.boundingRect(i));
	    }
	    target1 = contourList.get(objectIndex1);
	    target2 = contourList.get(objectIndex2);
	    if (target1.x < target2.x)
	    {
		leftTarget = target1;
		rightTarget = target2;
	    } else
	    {
		leftTarget = target2;
		rightTarget = target1;
	    }
	    combinedTarget = new Rect(leftTarget.x, leftTarget.y, rightTarget.x + rightTarget.width - leftTarget.x,
		    rightTarget.height);
	    error = combinedTarget.x + (combinedTarget.width / 2) - (80);
	} else
	{
	    error = 0.1;
	}
	return error;
    }
}