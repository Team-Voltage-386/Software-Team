package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.teleop.ManualElevator;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The ElevatorSubsystem is responsible for operations related to the elevator,
 * such as raising and lowering the elevator, breaking the chain prior to
 * climbing, and locking the elevator in place at the top of the climb.
 */
public class ElevatorSubsystem extends Subsystem {
    public static final String ELEVATOR_ENCODER_VALUE = "Elevator encoder value";

    public static final DoubleSolenoid.Value UNLOCKED = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value LOCKED = DoubleSolenoid.Value.kReverse;

    Spark elevatorSpark = new Spark(RobotMap.elevatorSparks);
    Encoder elevatorEncoder = new Encoder(RobotMap.elevatorEncoderA, RobotMap.elevatorEncoderB);

    Solenoid chainBreaker = new Solenoid(RobotMap.chainBreaker);

    DoubleSolenoid latchSolenoid = new DoubleSolenoid(RobotMap.latchForwardChannel, RobotMap.latchReverseChannel);
    DigitalInput dio0 = new DigitalInput(RobotMap.lowerElevatorLimitSwitch);
    DigitalInput dio4 = new DigitalInput(4);

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
	SmartDashboard.putNumber(ELEVATOR_ENCODER_VALUE, elevatorEncoder.get());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
	setDefaultCommand(new ManualElevator());
    }

    /**
     * Control elevator via DPad values.
     * 
     * @param pov
     *            The POV value (one of the 8 angle values available from the DPad)
     * @param speed
     *            The speed at which the elevator moves
     */
    public void elevatorFromDPad(int pov, double speed) {
	if (pov != -1 && pov < 270 && pov > 90) {
	    if (dio0.get())
		elevatorSpark.set(speed);
	    else
		elevatorSpark.set(0);
	} else if (pov != -1 && dio4.get()) {
	    elevatorSpark.set(-1 * speed);
	} else {
	    elevatorSpark.set(0);
	}
    }

    /**
     * Set the height of the elevator to a specific number of encoder ticks. This
     * method is reusable by both teleop and auto commands.
     * 
     * @param ticks
     *            The encoder ticks
     */
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

    /**
     * Lock the elevator in place with the latching mechanism, or unlock it if it is
     * already locked.
     * 
     * This should be triggered at the end of the climb to hold the elevator in
     * place.
     * 
     * TODO: when does the unlock occur?
     */
    public void lockElevator() {
	if (latchSolenoid.get() == LOCKED) {
	    lock(UNLOCKED);
	} else {
	    lock(LOCKED);
	}
    }

    /**
     * Set the lock solenoid to either locked or unlocked.
     * 
     * @param value
     */
    public void lock(Value value) {
	latchSolenoid.set(value);
    }

    /**
     * Break the chain. This should occur in the last 30 seconds of the match as the
     * robot is preparing to climb.
     * 
     * WARNING: This is a one-time use during each match. Once you trigger it, the
     * chain can only be reconnected by a human.
     */
    public void breakChain() {
	chainBreaker.set(true);
    }

}
