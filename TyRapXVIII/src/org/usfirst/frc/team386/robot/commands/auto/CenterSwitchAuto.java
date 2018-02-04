package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.LowerIntake;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start center and win the switch.
 * 
 * This command will decide whether the robot should go to the left or right
 * switch depending on the game data.
 */
public class CenterSwitchAuto extends InstantCommand {

    public CenterSwitchAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.gamedata.isSwitchLeft()) {
	    new CenterSwitchAutoLeft().start();
	} else {
	    new CenterSwitchAutoRight().start();
	}
    }

    /**
     * Auto mode for left switch starting from center.
     */
    class CenterSwitchAutoLeft extends CommandGroup {

	public CenterSwitchAutoLeft() {
	    addSequential(new LowerIntake());
	    addSequential(new DriveForward(18));
	    addSequential(new TurnLeft(45));
	    addSequential(new DriveForward(75));
	    addSequential(new TurnRight(45));
	    addSequential(new DriveForward(25));
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

    /**
     * Auto mode for right switch starting from center.
     */
    class CenterSwitchAutoRight extends CommandGroup {

	public CenterSwitchAutoRight() {
	    addSequential(new LowerIntake());
	    addSequential(new DriveForward(18));
	    addSequential(new TurnRight(45));
	    addSequential(new DriveForward(75));
	    addSequential(new TurnLeft(40));
	    addSequential(new DriveForward(26));
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}

    }
}
