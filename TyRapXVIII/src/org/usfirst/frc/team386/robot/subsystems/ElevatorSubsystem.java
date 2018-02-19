package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.Robot;
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

    public static final DoubleSolenoid.Value LOCKED = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value UNLOCKED = DoubleSolenoid.Value.kReverse;

    Spark elevatorSpark = new Spark(RobotMap.elevatorSparks);
    public Encoder elevatorEncoder = new Encoder(RobotMap.elevatorEncoderA, RobotMap.elevatorEncoderB);

    DoubleSolenoid chainBreaker = new DoubleSolenoid(RobotMap.chainBreakerIn, RobotMap.chainBreakerOut);

    DoubleSolenoid latchSolenoid = new DoubleSolenoid(RobotMap.latchForwardChannel, RobotMap.latchReverseChannel);
    public DigitalInput lowerElevatorLimitSwitch = new DigitalInput(RobotMap.lowerElevatorLimitSwitch);
    public DigitalInput upperElevatorLimitSwitch = new DigitalInput(RobotMap.upperElevatorLimitSwitch);
    public DigitalInput switchLimitSwitch = new DigitalInput(RobotMap.switchLimitSwitch);
    public DigitalInput latchLimitSwitch = new DigitalInput(RobotMap.latchLimitSwitch);
    Timer timer = new Timer();
    boolean previousState = false;

    public ElevatorSubsystem() {
	super();
	lock(UNLOCKED);
	elevatorEncoder.reset();
	chainBreaker.set(DoubleSolenoid.Value.kForward);
    }

    public void resetEncoder() {
	elevatorEncoder.reset();
    }

    public void stopElevator() {
	elevatorSpark.set(SmartDashboard.getNumber("Elevator nuetral speed", 0));
    }

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
	SmartDashboard.putBoolean("Lower elevator limit switch", lowerElevatorLimitSwitch.get());
	SmartDashboard.putBoolean("Upper elevator limit switch", upperElevatorLimitSwitch.get());
	SmartDashboard.putNumber(ELEVATOR_ENCODER_VALUE, elevatorEncoder.get());
	SmartDashboard.putBoolean("Fangs", latchSolenoid.get().equals(LOCKED));
	SmartDashboard.putBoolean("Chain break", chainBreaker.get().equals(UNLOCKED));
	SmartDashboard.putBoolean("Switch limit switch", switchLimitSwitch.get());
	SmartDashboard.putBoolean("Latch limit switch", latchLimitSwitch.get());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
	setDefaultCommand(new ManualElevator());
    }

    public void climb() {
	elevatorSpark.set(-1);
    }

    /**
     * Control elevator via DPad values.
     * 
     * @param pov
     *            The POV value (one of the 8 angle values available from the DPad)
     * @param speed
     *            The speed at which the elevator moves
     */
    public void elevatorFromDPad(int pov, double speedUp, double speedDown, double nuetralSpeed) {
	// TODO: clean this up so it is easier to understand
	SmartDashboard.putString("Running", "True");
	if (pov != -1 && pov < 270 && pov > 90) {
	    if (lowerElevatorLimitSwitch.get())
		elevatorSpark.set(-1 * speedDown);
	    else
		elevatorSpark.set(0);
	} else if (pov != -1) {
	    if (upperElevatorLimitSwitch.get())
		elevatorSpark.set(speedUp);
	    else {
		elevatorSpark.set(nuetralSpeed);
	    }
	} else {
	    if (lowerElevatorLimitSwitch.get())
		elevatorSpark.set(nuetralSpeed);
	    else
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

    public void setHeight(int ticks, boolean down) {
	SmartDashboard.putString("Setting", "nuetral");
	if (down) {
	    SmartDashboard.putString("Setting", "Down");
	    elevatorSpark.set(-1 * SmartDashboard.getNumber("ELevator speed down", 0));
	} else {
	    SmartDashboard.putString("Setting", "Up");
	    elevatorSpark.set(SmartDashboard.getNumber(Robot.ELEVATOR_SPEED_LABEL, 0));
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
	if (chainBreaker.get() == DoubleSolenoid.Value.kForward)
	    chainBreaker.set(DoubleSolenoid.Value.kReverse);
	else
	    chainBreaker.set(DoubleSolenoid.Value.kForward);
    }

}
