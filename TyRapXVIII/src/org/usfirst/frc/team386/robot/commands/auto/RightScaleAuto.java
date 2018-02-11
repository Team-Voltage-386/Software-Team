package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;

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
	    // new RightScaleAutoRight().start();
	} else {
	    // new RightScaleAutoLeft().start();
	}
    }

    /**
     * Auto mode for left scale starting on the right side.
     */
    /*
     * class RightScaleAutoLeft extends CommandGroup {
     * 
     * public RightScaleAutoLeft() { // addSequential(new LowerIntake());
     * addSequential(new DriveForward(208)); addSequential(new TurnLeft(90));
     * addSequential(new DriveForward(216)); addSequential(new TurnRight(90));
     * addSequential(new DriveForward(50)); addSequential(new TurnRight(90));
     * addSequential(new DriveDistanceFromWall(558)); // measured in mm
     * addSequential(new ElevatorRaise()); addSequential(new CubeRelease()); } }
     */
    /**
     * Auto mode for right scale starting on the right side.
     */
    /*
     * class RightScaleAutoRight extends CommandGroup {
     * 
     * public RightScaleAutoRight() { // addSequential(new LowerIntake());
     * addSequential(new DriveForward(292)); addSequential(new TurnLeft(90));
     * addSequential(new DriveDistanceFromWall(558)); // measured in mm
     * addSequential(new ElevatorRaise()); addSequential(new CubeRelease()); } }
     */
}
