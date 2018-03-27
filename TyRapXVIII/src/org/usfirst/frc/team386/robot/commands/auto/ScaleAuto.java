package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.CubeVisionThread;
import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.CubeSuck;
import org.usfirst.frc.team386.robot.commands.DriveDistanceFromWall;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.DriveToCubeAuto;
import org.usfirst.frc.team386.robot.commands.GearShift;
import org.usfirst.frc.team386.robot.commands.SetArms;
import org.usfirst.frc.team386.robot.commands.SetElevator;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnLeftWithoutPid;
import org.usfirst.frc.team386.robot.commands.TurnRight;
import org.usfirst.frc.team386.robot.commands.TurnRightWithoutPid;
import org.usfirst.frc.team386.robot.commands.teleop.DriveSeconds;
import org.usfirst.frc.team386.robot.subsystems.ArmsSubsystem;
import org.usfirst.frc.team386.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Autonomous mode for winning the scale.
 */
public class ScaleAuto extends InstantCommand {

    public static final int ELEVATOR_SCALE_HEIGHT = -1800; // possibly -540
    public static final int ELEVATOR_SWITCH_HEIGHT = -600;
    /**
     * The amount of time to run the motors when releasing the cube.
     */
    public static final int CUBE_RELEASE_TIME = 1;
    public static SwitchAuto switchAutos = new SwitchAuto();

    /**
     * The distance in millimeters the robot's rear ultrasonic sensor should be from
     * the wall
     */
    public static final int DISTANCE_FROM_WALL = 558;

    public ScaleAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	Command chosen = select(Robot.chooserPosition.getSelected());
	SmartDashboard.putString("Auto mode", chosen.getName());
	chosen.start();
    }

    /**
     * Select the command to use based on the position.
     * 
     * @param position
     *            The robot starting position
     * @return The Command to start
     */
    private Command select(String position) {
	if (position.equals(Robot.LEFT)) {
	    if (Robot.gameData.isScaleLeft()) {
		if (Robot.gameData.isSwitchLeft())
		    return new LeftScaleAutoLeftWithSwitch();
		else
		    return new LeftScaleAutoLeft();
	    } else {
		if (Robot.chooserCrossSide.getSelected()) {
		    return new LeftScaleAutoRight();
		} else if (Robot.gameData.isSwitchLeft()) {
		    return switchAutos.getLeftSwitchAutoLeft();
		} else {
		    return new AutoLine();
		}
	    }
	} else if (position.equals(Robot.RIGHT)) {
	    if (Robot.gameData.isScaleRight()) {
		if (Robot.gameData.isSwitchRight())
		    return new RightScaleAutoRightWithSwitch();
		else
		    return new RightScaleAutoRight();
	    } else {
		if (Robot.chooserCrossSide.getSelected()) {
		    return new RightScaleAutoLeft();
		} else if (Robot.gameData.isSwitchRight()) {
		    return switchAutos.getRightSwitchAutoRight();
		} else {
		    return new AutoLine();
		}
	    }
	} else if (position.equals(Robot.CENTER)) {
	    // This is where the auto line from center would go
	    // this is an error condition
	    // suggested to go to switch
	    return new SwitchAuto();
	} else {
	    throw new IllegalArgumentException("Unsupported position: " + position);
	}
    }

    /**
     * Auto mode for left scale starting on the left side.
     */
    class LeftScaleAutoLeftWithSwitch extends CommandGroup {

	LeftScaleAutoLeftWithSwitch() {

	    addSequential(new GearShift(DriveSubsystem.FAST_GEAR));
	    addSequential(new DriveForward(400));
	    addParallel(new CubeSuck(5));
	    addSequential(new GearShift());
	    addSequential(new TurnRight(45));
	    addParallel(new SetArms(ArmsSubsystem.LOWERED));
	    // addParallel(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new SetElevator(ELEVATOR_SCALE_HEIGHT));
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	    addSequential(new SetElevator(0));
	    addSequential(new TurnRightWithoutPid(90));
	    addParallel(new CubeSuck(2));
	    addSequential(new DriveToCubeAuto(CubeVisionThread.SelectorType.bottom, 2));// leftMost
	    addSequential(new SetElevator(SwitchAuto.ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new DriveSeconds(1));
	    addSequential(new CubeRelease(1));
	}
    }

    class LeftScaleAutoLeft extends CommandGroup {

	LeftScaleAutoLeft() {

	    addSequential(new GearShift(DriveSubsystem.FAST_GEAR));
	    addSequential(new DriveForward(400));
	    addParallel(new CubeSuck(5));
	    addSequential(new GearShift());
	    addSequential(new TurnRight(45));
	    addParallel(new SetArms(ArmsSubsystem.LOWERED));
	    addSequential(new SetElevator(ELEVATOR_SCALE_HEIGHT));
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	    addSequential(new SetElevator(0));
	    addSequential(new TurnRightWithoutPid(90));
	    addParallel(new CubeSuck(2));
	    addSequential(new DriveToCubeAuto(CubeVisionThread.SelectorType.bottom, 2));// leftMost
	}
    }

    /**
     * Auto mode for right scale starting on the left side.
     */
    class LeftScaleAutoRight extends CommandGroup {

	LeftScaleAutoRight() {

	    addSequential(new DriveForward(208, 1));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(216, 1));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(50, .7));
	    addSequential(new TurnLeft(90));
	    // addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    addSequential(new DriveDistanceFromWall(DISTANCE_FROM_WALL)); // measured in
	    // mm
	    // addSequential(new SetElevator(-1800));
	    // addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	}
    }

    /**
     * Auto mode for right scale starting on the right side.
     */
    class RightScaleAutoRightWithSwitch extends CommandGroup {

	RightScaleAutoRightWithSwitch() {
	    addSequential(new GearShift(DriveSubsystem.FAST_GEAR));
	    addSequential(new DriveForward(400));
	    addParallel(new CubeSuck(5));
	    addSequential(new GearShift());
	    addSequential(new TurnLeft(45));
	    addParallel(new SetArms(ArmsSubsystem.LOWERED));
	    // addParallel(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new SetElevator(ELEVATOR_SCALE_HEIGHT));
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	    addSequential(new SetElevator(0));
	    addSequential(new TurnLeftWithoutPid(90));
	    addParallel(new CubeSuck(2));
	    addSequential(new DriveToCubeAuto(CubeVisionThread.SelectorType.bottom, 2));// leftMost
	    addSequential(new SetElevator(SwitchAuto.ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new DriveSeconds(1));
	    addSequential(new CubeRelease(1));

	    // addSequential(new DriveForward(282));
	    // addParallel(new CubeSuck(5));
	    // addSequential(new TurnLeft(90));
	    // addSequential(new DriveDistanceFromWall(DISTANCE_FROM_WALL)); // measured in
	    // mm
	    // addParallel(new SetArms(ArmsSubsystem.LOWERED));
	    // addParallel(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    // addSequential(new SetElevator(ELEVATOR_SCALE_HEIGHT));
	    // addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	    // addSequential(new SetElevator(0));
	}
    }

    class RightScaleAutoRight extends CommandGroup {

	RightScaleAutoRight() {
	    addSequential(new GearShift(DriveSubsystem.FAST_GEAR));
	    addSequential(new DriveForward(400));
	    addParallel(new CubeSuck(5));
	    addSequential(new GearShift());
	    addSequential(new TurnLeft(45));
	    addParallel(new SetArms(ArmsSubsystem.LOWERED));
	    // addParallel(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    addSequential(new SetElevator(ELEVATOR_SCALE_HEIGHT));
	    addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	    addSequential(new SetElevator(0));
	    addSequential(new TurnLeftWithoutPid(90));
	    addParallel(new CubeSuck(2));
	    addSequential(new DriveToCubeAuto(CubeVisionThread.SelectorType.bottom, 2));// leftMost
	    // addSequential(new SetElevator(SwitchAuto.ELEVATOR_SWITCH_HEIGHT));
	    // addSequential(new DriveSeconds(1));
	    // addSequential(new CubeRelease(1));

	    // addSequential(new DriveForward(282));
	    // addParallel(new CubeSuck(5));
	    // addSequential(new TurnLeft(90));
	    // addSequential(new DriveDistanceFromWall(DISTANCE_FROM_WALL)); // measured in
	    // mm
	    // addParallel(new SetArms(ArmsSubsystem.LOWERED));
	    // addParallel(new SetElevator(ELEVATOR_SWITCH_HEIGHT));
	    // addSequential(new SetElevator(ELEVATOR_SCALE_HEIGHT));
	    // addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	    // addSequential(new SetElevator(0));
	}
    }

    /**
     * Auto mode for left scale starting on the right side.
     */
    class RightScaleAutoLeft extends CommandGroup {

	RightScaleAutoLeft() {
	    // COMMMENTS
	    // COMMMENTS
	    // ADD MORE COMMENTS UNDER PAIN OF SARCASM

	    addSequential(new DriveForward(208));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(200));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(50));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveDistanceFromWall(DISTANCE_FROM_WALL)); // measured in mm
	    // addSequential(new SetArms(ArmsSubsystem.LOWERED));
	    // addSequential(new SetElevator(-1800));
	    // addSequential(new CubeRelease(CUBE_RELEASE_TIME));
	}
    }

    public Command getLeftScaleAutoLeft() {
	return new LeftScaleAutoLeft();
    }

    public Command getRightScaleAutoRight() {
	return new RightScaleAutoRight();
    }
}
