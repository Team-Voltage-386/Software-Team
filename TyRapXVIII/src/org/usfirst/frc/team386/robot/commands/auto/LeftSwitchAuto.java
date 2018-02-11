package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start left and win the switch.
 * 
 * This command will decide whether the robot should go to the left or right
 * switch depending on the game data.
 */
public class LeftSwitchAuto extends InstantCommand {

    public LeftSwitchAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.gameData.isSwitchLeft()) {
	    // new LeftSwitchAutoLeft().start();
	} else {
	    // new LeftSwitchAutoRight().start();
	}
    }

    /**
     * Auto mode for left switch starting on the left side.
     */
    /*
     * class LeftSwitchAutoLeft extends CommandGroup {
     * 
     * public LeftSwitchAutoLeft() { // addSequential(new LowerIntake());
     * addSequential(new DriveForward(140)); addSequential(new TurnRight(90));
     * addSequential(new DriveForward(18, 0.3)); addSequential(new ElevatorRaise());
     * addSequential(new CubeRelease()); } }
     * 
     * /** Auto mode for left switch starting on the right side.
     */
    /*
     * class LeftSwitchAutoRight extends CommandGroup {
     * 
     * public LeftSwitchAutoRight() { // addSequential(new LowerIntake());
     * addSequential(new DriveForward(215)); addSequential(new TurnRight(90));
     * addSequential(new DriveForward(183)); addSequential(new TurnRight(90));
     * addSequential(new DriveForward(42)); addSequential(new ElevatorRaise());
     * addSequential(new CubeRelease()); } }
     */
}
