package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveDistanceFromWall;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.LowerIntake;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ScaleAuto extends InstantCommand {

    public ScaleAuto() {
	super();
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.chooserPosition.getSelected().equals("Left")) {
	    if (Robot.gameData.isScaleLeft()) {
		new LeftScaleAutoLeft().start();
	    } else {
		if (Robot.chooserCrossSide.getSelected() == true) {
		    new LeftScaleAutoRight().start();
		} else {
		    new AutoLine().start();
		}
	    }
	} else if (Robot.chooserPosition.getSelected().equals("Right")) {
	    if (Robot.gameData.isScaleRight()) {
		new RightScaleAutoRight().start();
	    } else {
		if (Robot.chooserCrossSide.getSelected() == true) {
		    new RightScaleAutoLeft().start();
		} else {
		    new AutoLine().start();
		}
	    }
	} else if (Robot.chooserPosition.getSelected().equals("Center")) {
	    // This is where the auto line from center would go
	    // this is an error condition
	    // suggested to go to switch
	    new SwitchAuto().start();
	}
    }

    class LeftScaleAutoLeft extends CommandGroup {

	public LeftScaleAutoLeft() {
	    addSequential(new LowerIntake());
	    addSequential(new DriveForward(292));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveDistanceFromWall(558)); // measured in mm
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

    /**
     * Auto mode for right scale starting on the left side.
     */
    class LeftScaleAutoRight extends CommandGroup {

	public LeftScaleAutoRight() {
	    // addSequential(new LowerIntake());
	    addSequential(new DriveForward(208, 1));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(216, 1));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(50, .7));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveDistanceFromWall(558)); // measured in mm
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

    /**
     * Auto mode for right scale starting on the right side.
     */
    class RightScaleAutoRight extends CommandGroup {

	public RightScaleAutoRight() {
	    // addSequential(new LowerIntake());
	    addSequential(new DriveForward(292));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveDistanceFromWall(558)); // measured in mm
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

    /**
     * Auto mode for left scale starting on the right side.
     */
    class RightScaleAutoLeft extends CommandGroup {

	public RightScaleAutoLeft() {
	    // addSequential(new LowerIntake());
	    addSequential(new DriveForward(208));
	    addSequential(new TurnLeft(90));
	    addSequential(new DriveForward(216));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveForward(50));
	    addSequential(new TurnRight(90));
	    addSequential(new DriveDistanceFromWall(558)); // measured in mm
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }
}
