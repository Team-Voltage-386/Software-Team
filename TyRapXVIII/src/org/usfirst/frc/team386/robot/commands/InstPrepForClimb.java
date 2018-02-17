package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class InstPrepForClimb extends InstantCommand {

    public InstPrepForClimb() {
	super();
    }

    protected void initialize() {
	if (DriverStation.getInstance().getMatchTime() > 100) {
	    new PrepForClimb().start();
	} else {
	    SmartDashboard.putString("prepClimbErrors", "Error: Prep for climb failed");
	}
    }

}
