package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * A command for shifting gears.
 * 
 * For teleop use, use the empty constructor (i.e. attach the GearShift command
 * to a button to allow the driver to shift gears up or down as needed).
 * 
 * For autonomous use, use the constructor with either DriveSubsystem.SLOW_GEAR
 * for low gear, or DriveSubsystem.FAST_GEAR for high gear.
 */
public class GearShift extends InstantCommand {

    private Value gear;

    /**
     * Execute a gear shift down if already in high, or up if already in low. For
     * teleop use.
     */
    public GearShift() {
	super();
	requires(Robot.driveSubsystem);
    }

    /**
     * Execute a gear shift to a specific gear. For autonomous use.
     * 
     * @param gear
     *            The gear to shift to
     */
    public GearShift(Value gear) {
	super();
	requires(Robot.driveSubsystem);
	this.gear = gear;
    }

    // Called once when the command executes
    protected void initialize() {
	if (gear != null) {
	    Robot.driveSubsystem.shift(gear);
	} else {
	    Robot.driveSubsystem.shift();
	}
    }

}
