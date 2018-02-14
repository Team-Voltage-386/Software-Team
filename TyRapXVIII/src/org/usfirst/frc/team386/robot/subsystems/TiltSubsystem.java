package org.usfirst.frc.team386.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

/**
 *
 */
public class TiltSubsystem extends Subsystem {

	WPI_TalonSRX frontLeft = new WPI_TalonSRX(RobotMap.leftPrimaryDriveMotor);
    WPI_TalonSRX frontRight = new WPI_TalonSRX(RobotMap.rightPrimaryDriveMotor);
    DifferentialDrive drive = new DifferentialDrive(frontLeft, frontRight);
	public static PigeonIMU pigeon = new PigeonIMU(0);
	Timer timer = new Timer();
    

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void tiltPrevention(boolean isRunningElevator, boolean isGoingUpRamp) {
    	SmartDashboard.putString("Robot tilt", "nuetral");
    	if (pitch() > 1 && !isGoingUpRamp) {
    	    SmartDashboard.putString("Robot tilt", "positive");
    	    double startTime = timer.get();
    	    while (timer.get() - startTime < 3 && !isGoingUpRamp) {
    	    	drive.tankDrive((DriveSubsystem.speedMultiplier * 1.25), (DriveSubsystem.speedMultiplier * 1.25));
    	    }
    	} else if (pitch() < -1 && !isGoingUpRamp) {
    	    SmartDashboard.putString("Robot tilt", "negative");
    	    double startTime = timer.get();
    	    while (timer.get() - startTime < 3 && !isGoingUpRamp) {
    	    	drive.tankDrive(-(DriveSubsystem.speedMultiplier * 1.25), -(DriveSubsystem.speedMultiplier * 1.25));
    	    }
    	}
    }
    public void tiltCorrection() {
    	SmartDashboard.putString("Robot tilt", "nuetral");
    	if (pitch() > Math.abs(pitchLeeway)) {
    	    SmartDashboard.putString("Robot tilt", "positive");
    	    double startTime = timer.get();
    	    while (timer.get() - startTime < 3) {
    	    	drive.tankDrive((DriveSubsystem.speedMultiplier * 1.25), (DriveSubsystem.speedMultiplier * 1.25));
    	    }
    	} else if (pitch() < -Math.abs(pitchLeeway)) {
    	    SmartDashboard.putString("Robot tilt", "negative");
    	    double startTime = timer.get();
    	    while (timer.get() - startTime < 3) {
    	    	drive.tankDrive(-(DriveSubsystem.speedMultiplier * 1.25), -(DriveSubsystem.speedMultiplier * 1.25));
    	    }
    	}
    }
    public static double pitchLeeway = 1;
    public static double pitch() {
    	double[] pig = new double[3];
    	pigeon.getYawPitchRoll(pig);
    	return pig[1];
    }
}

