package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.SetArms;
import org.usfirst.frc.team386.robot.commands.SetElevator;
import org.usfirst.frc.team386.robot.commands.ShiftArms;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Autonomous mode for winning the switch.
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

	LeftSwitchAutoLeft() {
	    addSequential(new ShiftArms());
	    addSequential(new DriveForward(140));
	    addSequential(new TurnRight(90));
	    addSequential(new SetElevator(-540));
	    addSequential(new DriveForward(18, 0.3));
	    addSequential(new CubeRelease(1));
	}
    }

    /**
     * Auto mode for left switch starting on the right side.
     */
    class LeftSwitchAutoRight extends CommandGroup {

	LeftSwitchAutoRight() {
	    addSequential(new ShiftArms());
	    addSequential(new DriveForward(215));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(183));
	    addSequential(new TurnRight(90));
	    addSequential(new SetElevator(-600));
	    addSequential(new DriveForward(42));
	    addSequential(new CubeRelease(1));
	}
    }

    /**
     * Auto mode for right switch starting on the right side.
     */
    class RightSwitchAutoRight extends CommandGroup {

	RightSwitchAutoRight() {
	    addSequential(new ShiftArms());
	    addSequential(new DriveForward(140));
	    addSequential(new TurnLeft(90));
	    addSequential(new SetElevator(-540));
	    addSequential(new DriveForward(10));
	    addSequential(new CubeRelease(1));
	}
    }

    /**
     * Auto mode for left switch starting on the right side.
     */
    class RightSwitchAutoLeft extends CommandGroup {

	RightSwitchAutoLeft() {
	    addSequential(new ShiftArms());
	    addSequential(new DriveForward(215));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(183));
	    addSequential(new TurnLeft(90));
	    addSequential(new SetElevator(-540));
	    addSequential(new DriveForward(42));
	    addSequential(new CubeRelease(1));
	}
    }

    /**
     * Auto mode for left switch starting from center.
     */
    class CenterSwitchAutoLeft extends CommandGroup {

	CenterSwitchAutoLeft() {
	    addSequential(new SetArms(DoubleSolenoid.Value.kForward));
	    addSequential(new SetElevator(-540));
	    addSequential(new CenterSwitchAutoLeftDrive());
	    addSequential(new CubeRelease(1));
	    addSequential(new DriveForward(12, -.75));
	}
    }

    /**
     * Drive sequence for left switch starting from center.
     */
    class CenterSwitchAutoLeftDrive extends CommandGroup {
	CenterSwitchAutoLeftDrive() {
	    addSequential(new DriveForward(9));// 12
	    addSequential(new TurnLeft(45));
	    addSequential(new DriveForward(50));
	    addSequential(new TurnRight(45));
	    addSequential(new DriveForward(6));
	}
    }

    /**
     * Auto mode for right switch starting from center.
     */
    class CenterSwitchAutoRight extends CommandGroup {

	CenterSwitchAutoRight() {
	    addSequential(new SetArms(DoubleSolenoid.Value.kForward));
	    addSequential(new SetElevator(-540));
	    addSequential(new CenterSwitchAutoRightDrive());
	    addSequential(new CubeRelease(1));
	    addSequential(new DriveForward(12, -.75));
	}
    }

    /**
     * Drive sequence for right switch starting from center.
     */
    class CenterSwitchAutoRightDrive extends CommandGroup {
	CenterSwitchAutoRightDrive() {
	    addSequential(new DriveForward(9));
	    addSequential(new TurnRight(45));
	    addSequential(new DriveForward(50));
	    addSequential(new TurnLeft(45));
	    addSequential(new DriveForward(6));
	}
    }
}
