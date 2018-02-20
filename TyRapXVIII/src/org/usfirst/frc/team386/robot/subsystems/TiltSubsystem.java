package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The TiltSubsystem monitors the tilt values from the Pigeon IMU and attempts
 * to auto-correct by driving in the appropriate direction.
 */
public class TiltSubsystem extends Subsystem {

    /**
     * Label for pitch display.
     */
    public static final String PITCH = "Pitch";

    /**
     * Label for current robot tilt correction.
     */
    public static final String ROBOT_TILT = "Robot tilt";

    /**
     * Correcting forwards.
     */
    public static final String FORWARDS = "forwards";

    /**
     * Correcting backwards.
     */
    public static final String BACKWARDS = "backwards";

    /**
     * The allowed pitch threshold. Pitch should correct when it exceeds this value
     * in the positive or negative direction.
     */
    public double pitchLeeway = 10;

    PigeonIMU pigeon = new PigeonIMU(RobotMap.pigeon);

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
	SmartDashboard.putNumber(PITCH, pitch());
    }

    @Override
    public void initDefaultCommand() {
	// setDefaultCommand(new TiltDetection());
    }

    /**
     * Correct the robot tilt by driving the robot forward until the pitch is within
     * the allowed threshold.
     */
    public void tiltForward() {
	SmartDashboard.putString(ROBOT_TILT, FORWARDS);
	while (pitch() < -1 * pitchLeeway / 2 && RobotState.isEnabled()) {
	    Robot.driveSubsystem.drive.tankDrive(.5, .5);
	    updateDiagnostics();
	}
    }

    /**
     * Correct the robot tilt by driving the robot backwards until the pitch is
     * within the allowed threshold.
     */
    public void tiltBackwards() {
	SmartDashboard.putString(ROBOT_TILT, BACKWARDS);
	while (pitch() > pitchLeeway / 2 && RobotState.isEnabled()) {
	    Robot.driveSubsystem.drive.tankDrive(-.5, -.5);
	    updateDiagnostics();
	}
    }

    /**
     * Retrieve the current pitch value.
     * 
     * @return The pitch value
     */
    public double pitch() {
	double[] pig = new double[3];
	pigeon.getYawPitchRoll(pig);
	return pig[1];
    }
}
