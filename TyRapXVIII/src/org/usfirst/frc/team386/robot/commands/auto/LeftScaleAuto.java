package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start left and win the scale.
 * 
 * This command will decide whether the robot should go to the left or right
 * scale depending on the game data.
 */
public class LeftScaleAuto extends InstantCommand {

    public LeftScaleAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.gameData.isScaleLeft()) {
	    // new LeftScaleAutoLeft().start();
	} else {
	    // new LeftScaleAutoRight().start();
	}
    }

    /**
     * Auto mode for left scale starting on the left side.
     */
    /*
     * class LeftScaleAutoLeft extends CommandGroup {
     * 
     * public LeftScaleAutoLeft() { addSequential(new LowerIntake());
     * addSequential(new DriveForward(292)); addSequential(new TurnRight(90));
     * addSequential(new DriveDistanceFromWall(558)); // measured in mm
     * addSequential(new ElevatorRaise()); addSequential(new CubeRelease()); } }
     * 
     * /** Auto mode for right scale starting on the left side.
     */
    /*
     * class LeftScaleAutoRight extends CommandGroup {
     * 
     * public LeftScaleAutoRight() { // addSequential(new LowerIntake());
     * addSequential(new DriveForward(208, 1)); addSequential(new TurnRight(90));
     * addSequential(new DriveForward(216, 1)); addSequential(new TurnLeft(90));
     * addSequential(new DriveForward(50, .7)); addSequential(new TurnLeft(90));
     * addSequential(new DriveDistanceFromWall(558)); // measured in mm
     * addSequential(new ElevatorRaise()); addSequential(new CubeRelease()); } }
     */
}
