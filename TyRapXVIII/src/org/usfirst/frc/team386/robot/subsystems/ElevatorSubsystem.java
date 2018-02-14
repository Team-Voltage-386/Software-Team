package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;

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

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
	// setDefaultCommand(new ManualElevator());
    }

    public void elevatorFromDPad(int pov, double speed) {
	if (pov != -1 && pov < 270 && pov > 90) {
	    leftElevator.set(-1 * speed);
	} else if (pov != -1) {
	    leftElevator.set(speed);
	} else {
	    leftElevator.set(0);
	}
    }

    public void raiseElevatorTo(double percent) {
	// System.out.println("Work In Progress");
	// plan is to make it a percentage on how much we can raise it in general.
	// 0 - lowest it can go
	// 100 - highest it can go
    }

    public void lowerElevatorTo(double percent) {
	// System.out.println("Work In Progress");
	// plan is to make it a percentage on how much we can raise it in general.
	// 0 - lowest it can go
	// 100 - highest it can go
    }

}
