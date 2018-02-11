package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start right and win the switch.
 * 
 * This command will decide whether the robot should go to the left or right
 * switch depending on the game data.
 */
public class RightSwitchAuto extends InstantCommand {

    public RightSwitchAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.gameData.isSwitchLeft()) {
	    // new RightSwitchAutoLeft().start();
	} else {
	    // new RightSwitchAutoRight().start();
	}
    }

    /**
     * Auto mode for left switch starting on the right side.
     */
    /*
     * class RightSwitchAutoLeft extends CommandGroup {
     * 
     * public RightSwitchAutoLeft() { // addSequential(new LowerIntake());
     * addSequential(new DriveForward(215)); addSequential(new TurnLeft(90));
     * addSequential(new DriveForward(183)); addSequential(new TurnLeft(90));
     * addSequential(new DriveForward(42)); addSequential(new ElevatorRaise());
     * addSequential(new CubeRelease()); } }
     * 
     * /** Auto mode for right switch starting on the right side.
     */
    /*
     * class RightSwitchAutoRight extends CommandGroup {
     * 
     * public RightSwitchAutoRight() { // addSequential(new LowerIntake());
     * addSequential(new DriveForward(140)); addSequential(new TurnLeft(90));
     * addSequential(new DriveForward(10)); addSequential(new ElevatorRaise());
     * addSequential(new CubeRelease()); } }
     */
}