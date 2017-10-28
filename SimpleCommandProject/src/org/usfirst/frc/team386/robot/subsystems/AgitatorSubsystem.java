package org.usfirst.frc.team386.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The agitator shakes the ball basket to ensure balls fall through into the
 * feeder.
 */
public class AgitatorSubsystem extends Subsystem {

    private Relay agitator;

    public AgitatorSubsystem() {
	this.agitator = new Relay(3);
    }

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Start the agitator to ensure balls end up in the feeder.
     */
    public void start() {
	agitator.set(Value.kOn);
    }

    /**
     * Stop the agitator.
     */
    public void stop() {
	agitator.set(Value.kOff);
    }
}
