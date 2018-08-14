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

    public static final DoubleSolenoid.Value LOCKED = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value UNLOCKED = DoubleSolenoid.Value.kReverse;

    Spark elevatorSpark = new Spark(RobotMap.elevatorSparks);
    public Encoder elevatorEncoder = new Encoder(RobotMap.elevatorEncoderA, RobotMap.elevatorEncoderB);

    DoubleSolenoid chainBreaker = new DoubleSolenoid(RobotMap.chainBreakerIn, RobotMap.chainBreakerOut);

    DoubleSolenoid fangsSolenoid = new DoubleSolenoid(RobotMap.latchForwardChannel, RobotMap.latchReverseChannel);
    public DigitalInput lowerElevatorLimitSwitch = new DigitalInput(RobotMap.lowerElevatorLimitSwitch);
    public DigitalInput upperElevatorLimitSwitch = new DigitalInput(RobotMap.upperElevatorLimitSwitch);
    public DigitalInput switchLimitSwitch = new DigitalInput(RobotMap.switchLimitSwitch);
    public DigitalInput latchLimitSwitch = new DigitalInput(RobotMap.latchLimitSwitch);
    Timer timer = new Timer();
    boolean previousState = false;
    public static final double SPEED_UP = 1/* .9 */, SPEED_DOWN = -.75, SPEED_NUETRAL = .15, CLIMB_SPEED = -1;

    public ElevatorSubsystem() {
	super();
	lock(UNLOCKED);
	elevatorEncoder.reset();
	chainBreaker.set(LOCKED);
    }

    /**
     * Reset's the elevator's encoder
     */
    public void resetEncoder() {
	elevatorEncoder.reset();
    }

    /**
     * Sets the elevator to the default speed (stall speed)
     */
    public void stopElevator() {
	elevatorSpark.set(SPEED_NUETRAL);
    }

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	SmartDashboard.putBoolean("Lower elevator limit switch", lowerElevatorLimitSwitch.get());
	SmartDashboard.putBoolean("Upper elevator limit switch", upperElevatorLimitSwitch.get());
	SmartDashboard.putNumber(ELEVATOR_ENCODER_VALUE, elevatorEncoder.get());
	// SmartDashboard.putBoolean("Fangs", fangsSolenoid.get().equals(LOCKED));
	// SmartDashboard.putBoolean("Chain break",
	// chainBreaker.get().equals(UNLOCKED));
	// SmartDashboard.putBoolean("Switch limit switch", switchLimitSwitch.get());
	// SmartDashboard.putBoolean("Latch limit switch", latchLimitSwitch.get());
    }

    public void initDefaultCommand() {
	setDefaultCommand(new ManualElevator());
    }

    public void stopDefaultCommand() {
	setDefaultCommand(null);
    }

    /**
     * Sets the elevator to climb speed (full throttle down)
     */
    public void climb() {
	elevatorSpark.set(CLIMB_SPEED);
    }

    /**
     * Control elevator via DPad values.
     * 
     * @param pov
     *            The POV value (one of the 8 angle values available from the DPad)
     * @param speedUp
     *            The speed at which the elevator moves up
     * @param speedDown
     *            The speed the elevator moves down at
     */
    public void elevatorFromDPad(int pov, double speedUp, double speedDown, double nuetralSpeed) {
	// if POV is downward
	if (pov != -1 && pov < 270 && pov > 90) {
	    if (lowerElevatorLimitSwitch.get())
		elevatorSpark.set(speedDown);
	    else
		elevatorSpark.set(0);
	    // if POV is upward
	} else if (pov != -1) {
	    if (upperElevatorLimitSwitch.get())
		elevatorSpark.set(speedUp);
	    else {
		elevatorSpark.set(nuetralSpeed);
	    }
	    // If POV isn't pressed
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
     * @param down
     *            Direction of travel False: down True: up
     */

    public void setHeight(int ticks, boolean down) {
	if (down) {
	    elevatorSpark.set(SPEED_DOWN);
	} else {
	    elevatorSpark.set(SPEED_UP);
	}
    }

    /**
     * Toggle the elevator's fang mechanism.
     * 
     * This should be triggered at the end of the climb to hold the elevator in
     * place.
     */
    public void toggleElevatorLock() {
	if (fangsSolenoid.get() == LOCKED) {
	    lock(UNLOCKED);
	} else {
	    lock(LOCKED);
	}
    }

    /**
     * Set the fang's solenoid to either locked or unlocked.
     * 
     * @param value
     *            DOubleSolenoid.Value to set
     */
    public void lock(Value value) {
	fangsSolenoid.set(value);
    }

    /**
     * Break the chain. This should occur in the last 30 seconds of the match as the
     * robot is preparing to climb.
     * 
     * WARNING: This is a one-time use during each match. Once you trigger it, the
     * chain can only be reconnected by a human.
     */
    public void breakChain() {
	if (chainBreaker.get() == LOCKED)
	    chainBreaker.set(UNLOCKED);
	else
	    chainBreaker.set(LOCKED);
    }

    /**
     * Stops all functions of the subsystem
     */
    public void stopSubsystem() {
	setDefaultCommand(null);
	elevatorSpark.set(0);
    }

}
