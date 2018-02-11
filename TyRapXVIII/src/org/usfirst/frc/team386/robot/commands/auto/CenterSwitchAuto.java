package org.usfirst.frc.team386.robot.commands.auto;

import org.usfirst.frc.team386.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Strategy: start center and win the switch.
 * 
 * This command will decide whether the robot should go to the left or right
 * switch depending on the game data.
 */
public class CenterSwitchAuto extends InstantCommand {

    public CenterSwitchAuto() {
	super();
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.gameData.isSwitchLeft()) {
	    // new CenterSwitchAutoLeft().start();
	} else {
	    // new CenterSwitchAutoRight().start();
	}
    }

    /**
     * Auto mode for left switch starting from center.
     */
    /*
     * class CenterSwitchAutoLeft extends CommandGroup {
     * 
     * public CenterSwitchAutoLeft() { addSequential(new LowerIntake());
     * addParallel(new CenterSwitchAutoLeftDrive()); addParallel(new
     * ElevatorRaise()); addSequential(new CubeRelease()); } }
     */

    /**
     * Drive sequence for left switch starting from center.
     *//*
        * class CenterSwitchAutoLeftDrive extends CommandGroup { public
        * CenterSwitchAutoLeftDrive() { addSequential(new DriveForward(12));
        * addSequential(new TurnLeft(45)); addSequential(new DriveForward(65));
        * addSequential(new TurnRight(45)); addSequential(new DriveForward(12)); } }
        */

    /**
     * Auto mode for right switch starting from center.
     */
    /*
     * class CenterSwitchAutoRight extends CommandGroup {
     * 
     * public CenterSwitchAutoRight() { addSequential(new LowerIntake());
     * addParallel(new CenterSwitchAutoRightDrive()); addParallel(new
     * ElevatorRaise()); addSequential(new CubeRelease()); } }
     */

    /**
     * Drive sequence for right switch starting from center.
     */
    /*
     * class CenterSwitchAutoRightDrive extends CommandGroup { public
     * CenterSwitchAutoRightDrive() { addSequential(new DriveForward(12));
     * addSequential(new TurnRight(45)); addSequential(new DriveForward(60));
     * addSequential(new TurnLeft(45)); addSequential(new DriveForward(8)); } }
     */
}
