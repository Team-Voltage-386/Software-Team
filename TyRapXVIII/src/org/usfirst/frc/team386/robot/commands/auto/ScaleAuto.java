package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveDistanceFromWall;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.ShiftArms;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Autonomous mode for winning the scale.
 */
public class ScaleAuto extends InstantCommand {

    public ScaleAuto() {
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
	if (position.equals(Robot.LEFT)) {
	    if (Robot.gameData.isScaleLeft()) {
		return new LeftScaleAutoLeft();
	    } else {
		if (Robot.chooserCrossSide.getSelected()) {
		    return new LeftScaleAutoRight();
		} else {
		    return new AutoLine();
		}
	    }
	} else if (position.equals(Robot.RIGHT)) {
	    if (Robot.gameData.isScaleRight()) {
		return new RightScaleAutoRight();
	    } else {
		if (Robot.chooserCrossSide.getSelected()) {
		    return new RightScaleAutoLeft();
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
    class LeftScaleAutoLeft extends CommandGroup {

	LeftScaleAutoLeft() {
	    addSequential(new ShiftArms());
	    addSequential(new DriveForward(292));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveDistanceFromWall(558)); // measured in mm
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease(1));
	}
    }

    /**
     * Auto mode for right scale starting on the left side.
     */
    class LeftScaleAutoRight extends CommandGroup {

	LeftScaleAutoRight() {
	    // addSequential(new LowerIntake());
	    addSequential(new DriveForward(208, 1));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(216, 1));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(50, .7));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveDistanceFromWall(558)); // measured in mm
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease(1));
	}
    }

    /**
     * Auto mode for right scale starting on the right side.
     */
    class RightScaleAutoRight extends CommandGroup {

	RightScaleAutoRight() {
	    // addSequential(new LowerIntake());
	    addSequential(new DriveForward(292));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveDistanceFromWall(558)); // measured in mm
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease(1));
	}
    }

    /**
     * Auto mode for left scale starting on the right side.
     */
    class RightScaleAutoLeft extends CommandGroup {

	RightScaleAutoLeft() {
	    // addSequential(new LowerIntake());
	    addSequential(new DriveForward(208));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(216));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(50));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveDistanceFromWall(558)); // measured in mm
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease(1));
	}
    }
}
