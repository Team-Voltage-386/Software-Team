package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.commands.SetArms;
import org.usfirst.frc.team386.robot.subsystems.ArmsSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Prepare the robot to climb. This will disconnect the chain so that the intake
 * will not rise with the robot and will shift the arms.
 */
public class PrepForClimb extends InstantCommand {

    public PrepForClimb() {
	super();
    }

    protected void initialize() {
	if (true/*
		 * DriverStation.getInstance().getMatchTime() < 30 &&
		 * Robot.oi.manipulator.getRawButton(RobotMap.prepForClimbButton2)
		 */) {
	    new ExecuteSteps().start();
	    // SmartDashboard.putString("prepClimbErrors", "No error");
	} else {
	    // SmartDashboard.putString("prepClimbErrors", "Error: climb is only allowed
	    // with 30 seconds of game end");
	}
    }

    /**
     * Executes the steps to prepare for the climb.
     */
    class ExecuteSteps extends CommandGroup {

	ExecuteSteps() {
	    addSequential(new BreakChain());
	    addSequential(new SetArms(ArmsSubsystem.RAISED));
	}
    }

}
