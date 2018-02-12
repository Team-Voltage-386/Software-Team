package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.LowerIntake;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SwitchAuto extends InstantCommand {

    public SwitchAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	select(Robot.chooserPosition.getSelected()).start();
    }

    /**
     * Select the command to use based on the position.
     * 
     * @param position
     *            The robot starting position
     * @return The Command to start
     */
    private Command select(String position) {
	Command command = null;
	if (position.equals(Robot.LEFT)) {
	    if (Robot.gameData.isSwitchLeft()) {
		command = new LeftSwitchAutoLeft();
	    } else {
		if (Robot.chooserCrossSide.getSelected() == true) {
		    command = new LeftSwitchAutoRight();
		} else {
		    command = new AutoLine();
		}
	    }
	} else if (Robot.chooserPosition.getSelected().equals(Robot.RIGHT)) {
	    if (Robot.gameData.isSwitchRight()) {
		command = new RightSwitchAutoRight();
	    } else {
		if (Robot.chooserCrossSide.getSelected() == true) {
		    command = new RightSwitchAutoLeft();
		} else {
		    command = new AutoLine();
		}
	    }
	} else if (Robot.chooserPosition.getSelected().equals(Robot.CENTER)) {
	    if (Robot.gameData.isSwitchLeft()) {
		command = new CenterSwitchAutoLeft();
	    } else {
		command = new CenterSwitchAutoRight();
	    }
	} else {
	    throw new IllegalArgumentException("Unsupported position: " + position);
	}
	return command;
    }

    /**
     * Auto mode for left switch starting on the left side.
     */
    class LeftSwitchAutoLeft extends CommandGroup {

	public LeftSwitchAutoLeft() {
	    // addSequential(new LowerIntake());
	    addSequential(new DriveForward(140));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(18, 0.3));
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

    /**
     * Auto mode for left switch starting on the right side.
     */
    class LeftSwitchAutoRight extends CommandGroup {

	public LeftSwitchAutoRight() {
	    // addSequential(new LowerIntake());
	    addSequential(new DriveForward(215));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(183));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(42));
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

    /**
     * Auto mode for right switch starting on the right side.
     */
    public class RightSwitchAutoRight extends CommandGroup {

	public RightSwitchAutoRight() {
	    // addSequential(new LowerIntake());
	    addSequential(new DriveForward(140));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(10));
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

    /**
     * Auto mode for left switch starting on the right side.
     */
    public class RightSwitchAutoLeft extends CommandGroup {

	public RightSwitchAutoLeft() {
	    // addSequential(new LowerIntake());
	    addSequential(new DriveForward(215));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(183));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(42));
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

    /**
     * Auto mode for left switch starting from center.
     */
    class CenterSwitchAutoLeft extends CommandGroup {

	public CenterSwitchAutoLeft() {
	    addSequential(new LowerIntake());
	    addParallel(new CenterSwitchAutoLeftDrive());
	    addParallel(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

    /**
     * Drive sequence for left switch starting from center.
     */
    class CenterSwitchAutoLeftDrive extends CommandGroup {
	public CenterSwitchAutoLeftDrive() {
	    addSequential(new DriveForward(12));
	    addSequential(new TurnLeft(45));
	    addSequential(new DriveForward(65));
	    addSequential(new TurnRight(45));
	    addSequential(new DriveForward(12));
	}
    }

    /**
     * Auto mode for right switch starting from center.
     */
    class CenterSwitchAutoRight extends CommandGroup {

	public CenterSwitchAutoRight() {
	    addSequential(new LowerIntake());
	    addParallel(new CenterSwitchAutoRightDrive());
	    addParallel(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

    /**
     * Drive sequence for right switch starting from center.
     */
    class CenterSwitchAutoRightDrive extends CommandGroup {
	public CenterSwitchAutoRightDrive() {
	    addSequential(new DriveForward(12));
	    addSequential(new TurnRight(45));
	    addSequential(new DriveForward(60));
	    addSequential(new TurnLeft(45));
	    addSequential(new DriveForward(8));
	}
    }
}
