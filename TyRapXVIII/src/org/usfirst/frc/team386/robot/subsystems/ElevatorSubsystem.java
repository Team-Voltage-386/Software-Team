package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.teleop.ManualElevator;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
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

    DoubleSolenoid chainBreaker = new DoubleSolenoid(RobotMap.chainBreakerIn, RobotMap.chainBreakerOut);

    DoubleSolenoid latchSolenoid = new DoubleSolenoid(RobotMap.latchForwardChannel, RobotMap.latchReverseChannel);
    DigitalInput lowerElevatorLimitSwitch = new DigitalInput(RobotMap.lowerElevatorLimitSwitch);
    DigitalInput upperElevatorLimitSwitch = new DigitalInput(RobotMap.upperElevatorLimitSwitch);

    Timer timer = new Timer();
    boolean previousState = false;

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
	SmartDashboard.putBoolean("DIO0", lowerElevatorLimitSwitch.get());
	SmartDashboard.putBoolean("DIO4", upperElevatorLimitSwitch.get());
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
	    if (lowerElevatorLimitSwitch.get())
		elevatorSpark.set(0);
	    else
		elevatorSpark.set(0);
	} else if (pov != -1) {
	    if (upperElevatorLimitSwitch.get())
		elevatorSpark.set(-1 * speed);
	    else {
		// double currentTime = timer.get();
		// while (timer.get() < currentTime + .1 && !previousState) {
		// elevatorSpark.set(-1 * speed);
		// }
		elevatorSpark.set(-.2);
	    }
	} else {
	    if (lowerElevatorLimitSwitch.get())
		elevatorSpark.set(-.2);
	    else
		elevatorSpark.set(0);
	}
	previousState = lowerElevatorLimitSwitch.get();
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
     * Toggle the elevator latching mechanism.
     * 
     * This should be triggered at the end of the climb to hold the elevator in
     * place.
     */
    public void toggleElevatorLock() {
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
	chainBreaker.set(DoubleSolenoid.Value.kReverse);
    }

}
