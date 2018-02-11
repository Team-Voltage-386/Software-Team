package org.usfirst.frc.team386.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The ElevatorSubsyste is responsible for operations related to the elevator,
 * such as raising and lowering the elevator.
 */
public class ElevatorSubsystem extends Subsystem {

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
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
    }

    public void raiseElevatorTo(double percent) {
	System.out.println("Work In Progress");
	// plan is to make it a percentage on how much we can raise it in general.
	// 0 - lowest it can go
	// 100 - highest it can go
    }

    public void lowerElevatorTo(double percent) {
	System.out.println("Work In Progress");
	// plan is to make it a percentage on how much we can raise it in general.
	// 0 - lowest it can go
	// 100 - highest it can go
    }
}
