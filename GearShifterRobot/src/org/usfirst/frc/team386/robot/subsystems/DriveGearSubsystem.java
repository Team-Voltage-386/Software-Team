package org.usfirst.frc.team386.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveGearSubsystem extends Subsystem {

    public static int LO = 1;
    public static int HI = 2;

    private Solenoid s1;
    private Solenoid s2;

    private int drive;

    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
    }

    public void shift() {
	if (drive == HI) {
	    lo();
	} else {
	    hi();
	}
    }

    public void hi() {
	if (drive != HI) {
	    s1.set(true);
	    s2.set(true);
	    this.drive = HI;
	}
    }

    public void lo() {
	if (drive != LO) {
	    s1.set(false);
	    s2.set(false);
	    this.drive = LO;
	}
    }
}
