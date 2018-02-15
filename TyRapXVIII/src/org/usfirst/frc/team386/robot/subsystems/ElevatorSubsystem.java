package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The ElevatorSubsyste is responsible for operations related to the elevator,
 * such as raising and lowering the elevator.
 */
public class ElevatorSubsystem extends Subsystem {
    Spark elevatorSpark = new Spark(RobotMap.elevatorSpark);
    Encoder elevatorEncoder = new Encoder(RobotMap.elevatorEncoderA, RobotMap.elevatorEncoderB); // find out actual
												 // values
    Solenoid chainBreaker = new Solenoid(RobotMap.chainBreaker);

    DoubleSolenoid latchSolenoid = new DoubleSolenoid(RobotMap.latchForwardChannel, RobotMap.latchReverseChannel);

    public static final DoubleSolenoid.Value UNLOCKED = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value LOCKED = DoubleSolenoid.Value.kReverse;

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

    public void lockElevator() {
	if (latchSolenoid.get() == LOCKED) {
	    lock(UNLOCKED);
	} else {
	    lock(LOCKED);
	}
    }

    public void lock(Value gear) {
	latchSolenoid.set(gear);
    }

    public void breakChain() {
	chainBreaker.set(true);
    }

}
