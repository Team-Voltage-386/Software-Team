package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.teleop.ElevatorManual;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The ElevatorSubsyste is responsible for operations related to the elevator,
 * such as raising and lowering the elevator.
 */
public class ElevatorSubsystem extends Subsystem {
    Spark leftElevator = new Spark(RobotMap.elevatorSpark);

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
    }

    public void initDefaultCommand() {
	setDefaultCommand(new ElevatorManual());
    }

    public void raiseOrLower(double value, double speed) {
	if (value == -1) {
	    leftElevator.set(0);
	} else {
	    leftElevator.set(speed * Math.acos(Math.toRadians(value)));
	}

    }

    public void raiseElevatorTo(double percent) {
	// TODO: implement for autonomous
	// plan is to make it a percentage on how much we can raise it in general.
	// 0 - lowest it can go
	// 100 - highest it can go
    }

    public void lowerElevatorTo(double percent) {
	// TODO: implement for autonomous
	// plan is to make it a percentage on how much we can raise it in general.
	// 0 - lowest it can go
	// 100 - highest it can go
    }
}
