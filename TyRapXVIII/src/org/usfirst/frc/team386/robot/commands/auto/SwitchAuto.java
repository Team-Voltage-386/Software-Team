package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.CubeSuck;
import org.usfirst.frc.team386.robot.commands.CubeSuckUltra;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.GearShift;
import org.usfirst.frc.team386.robot.commands.SetArms;
import org.usfirst.frc.team386.robot.commands.SetElevator;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;
import org.usfirst.frc.team386.robot.commands.teleop.DriveSeconds;
import org.usfirst.frc.team386.robot.subsystems.ArmsSubsystem;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Autonomous mode for winning the switch.
 */
public class SwitchAuto extends InstantCommand {

    /**
     * The elevator encoder tick height for switch firing.
     */
    public static final int ELEVATOR_SWITCH_HEIGHT = -600; // possibly -540 NEGATIVE
    public static ScaleAuto scaleAutos = new ScaleAuto();

    /**
     * The amount of time to run the motors when releasing the cube.
     */
    public static final int CUBE_RELEASE_TIME = 1;

    public SwitchAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	Command chosen = select(Robot.chooserPosition.getSelected());
	SmartDashboard.putString("Auto mode", chosen.getName());
	chosen.start();
    }

    /**
     * Select the command to use based on the position. Don't even bother tracing
     * the logic, this method is too far gone
     *
     * @param position
     *            The robot starting position
     * @return The Command to start
     */
    private Command select(String position) {
	// Let this method be a valuable lesson in the uses of switch statements :P
	if (position.equals(Robot.LEFT)) {
	    if (Robot.gameData.isSwitchLeft()) {
		return new LeftSwitchAutoLeft();
	    } else {
		if (Robot.chooserCrossSide.getSelected()) {
		    return new LeftSwitchAutoRight();
		} else if (Robot.gameData.isScaleLeft()) {
		    return scaleAutos.getRightScaleAutoRight();
		} else {
		    return new AutoLine();
		}
	    }
	} else if (position.equals(Robot.RIGHT)) {
	    if (Robot.gameData.isSwitchRight()) {
		return new RightSwitchAutoRight();
	    } else {
		if (Robot.chooserCrossSide.getSelected()) {
		    return new RightSwitchAutoLeft();
		} else if (Robot.gameData.isScaleRight()) {
		    return scaleAutos.getRightScaleAutoRight();
		} else {
		    return new AutoLine();
		}
	    }
	} else if (position.equals(Robot.CENTER)) {
	    if (Robot.gameData.isSwitchLeft()) {
		return new CenterSwitchAutoLeft();
	    } else {
		return new CenterSwitchAutoRight();
	    }
	} else {
	    throw new IllegalArgumentException("Unsupported position: " + position);
	}
    }

    /**
     * Auto mode for left switch starting on the left side.
     */
    class LeftSwitchAutoLeft extends CommandGroup {

	LeftSwitchAutoLeft() {
	    addSequential(new GearShift());
	    addSequential(new DriveForward(110));
	    addSequential(new GearShift(DriveSubsystem.SLOW_GEAR));
	    addSequential(new TurnRight(90));
	    addSequential(new GearShift());
	    addParallel(new SetArms(ArmsSubsystem.LOWERED));
	    addParallel(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new DriveSeconds(.5));
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	}
    }

    /**
     * Auto mode for left switch starting on the right side.
     */
    class LeftSwitchAutoRight extends CommandGroup {

	LeftSwitchAutoRight() {

	    addSequential(new DriveForward(208, 1));
	    addSequential(new GearShift());
	    addSequential(new TurnRight(90));
	    addSequential(new GearShift());
	    addSequential(new DriveForward(216, 1));
	    // addSequential(new TurnRight(90));
	    // addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    // addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    // addSequential(new DriveForward(42));
	    // addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	}
    }

    /**
     * Auto mode for right switch starting on the right side.
     */
    class RightSwitchAutoRight extends CommandGroup {

	RightSwitchAutoRight() {
	    addSequential(new GearShift());
	    addSequential(new DriveForward(110));
	    addSequential(new GearShift(Robot.driveSubsystem.SLOW_GEAR));
	    addSequential(new TurnLeft(90));
	    addSequential(new GearShift());
	    addParallel(new SetArms(ArmsSubsystem.LOWERED));
	    addParallel(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new DriveSeconds(.5));
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	}
    }

    /**
     * Auto mode for left switch starting on the right side.
     */
    class RightSwitchAutoLeft extends CommandGroup {

	RightSwitchAutoLeft() {

	    addSequential(new DriveForward(208, 1));
	    addSequential(new GearShift());
	    addSequential(new TurnLeft(90));
	    addSequential(new GearShift());
	    addSequential(new DriveForward(216, 1));
	    // addSequential(new TurnLeft(90));
	    // addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    // addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    // addSequential(new DriveForward(42));
	    // addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	}
    }

    /**
     * Auto mode for left switch starting from center.
     */
    class CenterSwitchAutoLeft extends CommandGroup {

	CenterSwitchAutoLeft() {

	    addSequential(new GearShift(DriveSubsystem.SLOW_GEAR));
	    addSequential(new DriveForward(12));

	    addSequential(new TurnLeft(45));
	    // addSequential(new GearShift());
	    addSequential(new DriveForward(60));
	    // addSequential(new CubeSuck(5));
	    // addSequential(new GearShift());
	    addSequential(new TurnRight(45));
	    // addSequential(new GearShift());

	    addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    // addSequential(new DriveForward(1));
	    // addSequential(new DriveForward(25));
	    addSequential(new DriveSeconds(1.5));
	    // addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	    // addSequential(new SetArms(DoubleSolenoid.Value.kForward));
	    // addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    // addSequential(new CenterSwitchAutoRightDrive());
	    addParallel(new CubeSuck(5));
	    addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    addSequential(new CubeSuck(.5));
	    addSequential(new CubeRelease(1));
	    addSequential(new DriveForward(17.6, -.75));// 12

	    addParallel(new SetElevator(0));
	    addSequential(new TurnRight(75));
	    addParallel(new CubeSuck(9));
	    addSequential(new DriveForward(25, .75));
	    addSequential(new CubeSuckUltra(3));
	    addSequential(new DriveForward(20, -.75));
	    addParallel(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new TurnLeft(75));
	    addSequential(new DriveSeconds(1));
	    addSequential(new CubeRelease(1));
	    addSequential(new DriveForward(12, -.75));
	}
    }

    /**
     * Drive sequence for left switch starting from center.
     */
    class CenterSwitchAutoLeftDrive extends CommandGroup {
	CenterSwitchAutoLeftDrive() {

	}
    }

    /**
     * Auto mode for right switch starting from center.
     */
    class CenterSwitchAutoRight extends CommandGroup {

	CenterSwitchAutoRight() {
	    addSequential(new GearShift(DriveSubsystem.SLOW_GEAR));
	    addSequential(new DriveForward(12));

	    addSequential(new TurnRight(45));
	    // addSequential(new GearShift());
	    addSequential(new DriveForward(60));
	    // addSequential(new GearShift());
	    // addSequential(new CubeSuck(5));
	    addSequential(new TurnLeft(45));
	    // addSequential(new GearShift());

	    addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    // addSequential(new DriveForward(1));
	    // addSequential(new DriveForward(25));
	    addSequential(new DriveSeconds(1.5));
	    // addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	    // addSequential(new SetArms(DoubleSolenoid.Value.kForward));
	    // addSequential(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    // addSequential(new CenterSwitchAutoRightDrive());
	    addParallel(new CubeSuck(5));
	    addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    addSequential(new CubeSuck(.5));
	    addSequential(new CubeRelease(1));
	    addSequential(new DriveForward(20, -.75));// 12, 20

	    addParallel(new SetElevator(0));
	    addSequential(new TurnLeft(75));
	    addParallel(new CubeSuck(9));
	    addSequential(new DriveForward(25, .75));
	    addSequential(new CubeSuckUltra(3));
	    addParallel(new CubeSuck(3));
	    addSequential(new DriveForward(25, -.75));
	    addParallel(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new TurnRight(75));
	    addSequential(new DriveSeconds(1));
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
	    addSequential(new GearShift());
	    addSequential(new TurnRight(45));
	    addSequential(new GearShift());
	    addSequential(new DriveForward(50));
	    addSequential(new GearShift());
	    addSequential(new TurnLeft(45));
	    addSequential(new GearShift());
	    addSequential(new DriveForward(6));
	}
    }

    public Command getLeftSwitchAutoLeft() {
	return new LeftSwitchAutoLeft();
    }

    public Command getRightSwitchAutoRight() {
	return new RightSwitchAutoRight();
    }
}
