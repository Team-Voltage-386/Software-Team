package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.CubeVisionThread;
import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.DriveToCubeAuto;
import org.usfirst.frc.team386.robot.commands.TurnLeftWithoutPid;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Autonomous mode for crossing the auto line.
 */
public class AutoLine extends InstantCommand {

    public AutoLine() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	switch (Robot.chooserPosition.getSelected()) {
	case Robot.LEFT:
	    new Commands().start();
	    break;// leftMost
	case Robot.RIGHT:
	    new DriveForward(120).start();
	    break;
	case Robot.CENTER:
	    new SwitchAuto().start();
	}
    }

    class Commands extends CommandGroup {
	Commands() {
	    addSequential(new TurnLeftWithoutPid(110));
	    addSequential(new DriveToCubeAuto(CubeVisionThread.SelectorType.bottom, 5));
	}
    }

}
