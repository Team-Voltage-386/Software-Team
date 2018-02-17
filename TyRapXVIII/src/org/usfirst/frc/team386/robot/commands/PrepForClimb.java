package org.usfirst.frc.team386.robot.commands;

import org.usfirst.frc.team386.robot.commands.teleop.BreakChain;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepForClimb extends CommandGroup {

    public PrepForClimb() {
	addParallel(new BreakChain());
	addParallel(new ShiftArms());
    }
}
