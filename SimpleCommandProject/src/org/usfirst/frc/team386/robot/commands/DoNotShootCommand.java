package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Command group that turns off shooting.
 */
public class DoNotShootCommand extends CommandGroup {

    public DoNotShootCommand() {
	addParallel(new StopShooterCommand());
	addParallel(new StopTriggerCommand());
    }
}
