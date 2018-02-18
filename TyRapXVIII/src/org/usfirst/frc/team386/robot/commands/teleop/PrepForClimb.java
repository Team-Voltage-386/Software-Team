package org.usfirst.frc.team386.robot.commands.teleop;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.ShiftArms;

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
	if (DriverStation.getInstance().getMatchTime() < 30
		&& Robot.oi.manipulator.getRawButton(RobotMap.prepForClimbButton2)) {
	    new ExecuteSteps().start();
	} else {
	    SmartDashboard.putString("prepClimbErrors", "Error: climb is only allowed with 30 seconds of game end");
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
