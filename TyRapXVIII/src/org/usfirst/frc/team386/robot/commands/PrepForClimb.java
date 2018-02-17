package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.commands.teleop.BreakChain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Prepare the robot to climb. This will disconnect the chain so that the intake
 * will not rise with the robot and will shift the arms.
 */
public class PrepForClimb extends InstantCommand {

    public PrepForClimb() {
	super();
    }

    protected void initialize() {
	if (DriverStation.getInstance().getMatchTime() > 100) {
	    new ExecuteSteps().start();
	} else {
	    SmartDashboard.putString("prepClimbErrors", "Error: Prep for climb failed");
	}
    }

    /**
     * Executes the steps to prepare for the climb.
     */
    class ExecuteSteps extends CommandGroup {

	ExecuteSteps() {
	    addParallel(new BreakChain());
	    addParallel(new ShiftArms());
	}
    }

}
