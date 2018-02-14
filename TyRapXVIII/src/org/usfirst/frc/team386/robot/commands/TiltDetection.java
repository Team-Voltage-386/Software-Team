package org.usfirst.frc.team386.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.subsystems.TiltSubsystem;
/**
 *
 */
public class TiltDetection extends Command {
	
    public TiltDetection() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	requires(Robot.elevatorSubsystem);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if ((TiltSubsystem.pitch() > Math.abs(TiltSubsystem.pitchLeeway) && Robot.elevatorSubsystem.elevatorIsUp) || (TiltSubsystem.pitch() < -Math.abs(TiltSubsystem.pitchLeeway) && Robot.elevatorSubsystem.elevatorIsUp)) {
    		new TiltCorrect().start();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
