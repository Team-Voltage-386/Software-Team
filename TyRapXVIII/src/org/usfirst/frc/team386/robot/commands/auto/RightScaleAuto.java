package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.commands.CubeRelease;
import org.usfirst.frc.team386.robot.commands.DriveForward;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;
import org.usfirst.frc.team386.robot.commands.TurnLeft;
import org.usfirst.frc.team386.robot.commands.TurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start right and win the scale.
 * 
 * This command will decide whether the robot should go to the left or right
 * scale depending on the game data.
 */
public class RightScaleAuto extends InstantCommand {

    public RightScaleAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.gameData.isScaleRight()) {
	    new RightScaleAutoRight().start();
	} else {
	    new RightScaleAutoLeft().start();
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
	    // TODO: check ultrasonic and adjust distance to scale by moving fwd or rev
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
	    // TODO: check ultrasonic and adjust distance to scale by moving fwd or rev
	    addSequential(new ElevatorRaise());
	    addSequential(new CubeRelease());
	}
    }

}
