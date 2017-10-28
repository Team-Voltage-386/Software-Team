package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Command group that groups together the commands needed to fire balls.
 */
public class ShootCommand extends CommandGroup {

    public ShootCommand() {
	// start shooter wheel
	addParallel(new StartShooterCommand());
	// TODO: wait until the wheel is up-to-speed

	// start agitator

	// start trigger
	addParallel(new StartTriggerCommand());

    }
}
