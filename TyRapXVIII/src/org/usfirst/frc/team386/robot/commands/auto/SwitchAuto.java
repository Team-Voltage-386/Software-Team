package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.SetArms;
import org.usfirst.frc.team386.robot.commands.SetElevator;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnLeftWithoutPid;
import org.usfirst.frc.team386.robot.commands.TurnRight;
import org.usfirst.frc.team386.robot.commands.TurnRightWithoutPid;
import org.usfirst.frc.team386.robot.subsystems.ArmsSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Autonomous mode for winning the switch.
 */
public class SwitchAuto extends InstantCommand {

    /**
     * The elevator encoder tick height for switch firing.
     */
    public static final int ELEVATOR_SWITCH_HEIGHT = -600; // possibly -540

    /**
     * The amount of time to run the motors when releasing the cube.
     */
    public static final int CUBE_RELEASE_TIME = 1;

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
		if (Robot.chooserCrossSide.getSelected()) {
		    command = new LeftSwitchAutoRight();
		} else {
		    command = new AutoLine();
		}
	    }
	} else if (position.equals(Robot.RIGHT)) {
	    if (Robot.gameData.isSwitchRight()) {
		command = new RightSwitchAutoRight();
	    } else {
		if (Robot.chooserCrossSide.getSelected()) {
		    command = new RightSwitchAutoLeft();
		} else {
		    command = new AutoLine();
		}
	    }
	} else if (position.equals(Robot.CENTER)) {
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
	    addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    addSequential(new DriveForward(140));
	    addSequential(new TurnRight(90));
	    addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new DriveForward(18, 0.3));
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	}
    }

    /**
     * Auto mode for left switch starting on the right side.
     */
    class LeftSwitchAutoRight extends CommandGroup {

	LeftSwitchAutoRight() {
	    addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    addSequential(new DriveForward(215));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(183));
	    addSequential(new TurnRight(90));
	    addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new DriveForward(42));
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	}
    }

    /**
     * Auto mode for right switch starting on the right side.
     */
    class RightSwitchAutoRight extends CommandGroup {

	RightSwitchAutoRight() {
	    addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    addSequential(new DriveForward(140));
	    addSequential(new TurnLeft(90));
	    addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new DriveForward(10));
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	}
    }

    /**
     * Auto mode for left switch starting on the right side.
     */
    class RightSwitchAutoLeft extends CommandGroup {

	RightSwitchAutoLeft() {
	    addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    addSequential(new DriveForward(215));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(183));
	    addSequential(new TurnLeft(90));
	    addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new DriveForward(42));
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	}
    }

    /**
     * Auto mode for left switch starting from center.
     */
    class CenterSwitchAutoLeft extends CommandGroup {

	CenterSwitchAutoLeft() {
	    addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new CenterSwitchAutoLeftDrive());
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	    addSequential(new DriveForward(12, -.75));
	}
    }

    /**
     * Drive sequence for left switch starting from center.
     */
    class CenterSwitchAutoLeftDrive extends CommandGroup {
	CenterSwitchAutoLeftDrive() {
	    addSequential(new DriveForward(9));// 12
	    addSequential(new TurnLeftWithoutPid(45));
	    addSequential(new DriveForward(50)); // 60
	    addSequential(new TurnRightWithoutPid(45));
	    addSequential(new DriveForward(6)); // 9
	}
    }

    /**
     * Auto mode for right switch starting from center.
     */
    class CenterSwitchAutoRight extends CommandGroup {

	CenterSwitchAutoRight() {
	    addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
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
	    addSequential(new TurnRightWithoutPid(45));
	    addSequential(new DriveForward(50));
	    addSequential(new TurnLeftWithoutPid(45));
	    addSequential(new DriveForward(6));
	}
    }
}
