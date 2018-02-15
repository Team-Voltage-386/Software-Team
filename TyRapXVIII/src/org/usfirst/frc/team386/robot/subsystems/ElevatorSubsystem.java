package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The ElevatorSubsyste is responsible for operations related to the elevator,
 * such as raising and lowering the elevator.
 */
public class ElevatorSubsystem extends Subsystem {
    /*
     * <<<<<<< HEAD Spark leftElevator = new Spark(RobotMap.elevatorSpark);
     * DoubleSolenoid raiseIntake = new DoubleSolenoid(4, 5); =======
     */ Spark elevatorSpark = new Spark(RobotMap.elevatorSpark);
    Encoder elevatorEncoder = new Encoder(1, 2); // find out actual values
    /* >>>>>>> 32a0bf106497aca4269ab73f725daa772f246589 */

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
	    elevatorSpark.set(-1 * speed);
	} else if (pov != -1) {
	    elevatorSpark.set(speed);
	} else {
	    elevatorSpark.set(0);
	}
    }

    public void raiseElevatorTo(double percent) {
	// System.out.println("Work In Progress");
	// plan is to make it a percentage on how much we can raise it in general.
	// 0 - lowest it can go
	// 100 - highest it can go
	// encoder.reset();
    }

    public void lowerElevatorTo(double percent) {
	// System.out.println("Work In Progress");
	// plan is to make it a percentage on how much we can raise it in general.
	// 0 - lowest it can go
	// 100 - highest it can go
	// encoder.reset();
    }

    public void setHeight(int ticks) {
	if (elevatorEncoder.get() < ticks) {
	    while (elevatorEncoder.get() < ticks) {
		elevatorSpark.set(1);
	    }
	} else {
	    while (elevatorEncoder.get() > ticks) {
		elevatorSpark.set(-1);
	    }
	}
    }

}
