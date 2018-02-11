package org.usfirst.frc.team386.robot.subsystems;

import org.usfirst.frc.team386.robot.Robot;
import org.usfirst.frc.team386.robot.RobotMap;
import org.usfirst.frc.team386.robot.commands.ElevatorRaise;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The ElevatorSubsyste is responsible for operations related to the elevator,
 * such as raising and lowering the elevator.
 */
public class ElevatorSubsystem extends Subsystem {
    Spark leftElevator = new Spark(RobotMap.elevatorSpark);

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
	setDefaultCommand(new ElevatorRaise());
    }

    public void elevatorFromDPad() {
	while (RobotState.isEnabled())
	    if (Robot.oi.xboxControl.getPOV(0) != -1)
		leftElevator.set(SmartDashboard.getNumber("Elevator Speed", 0)
			* Math.acos(Math.toRadians(Robot.oi.xboxControl.getPOV(0))));
    }

    public void raiseElevatorTo(double percent) {
	System.out.println("Work In Progress");
	// plan is to make it a percentage on how much we can raise it in general.
	// 0 - lowest it can go
	// 100 - highest it can go
    }

    public void lowerElevatorTo(double percent) {
	System.out.println("Work In Progress");
	// plan is to make it a percentage on how much we can raise it in general.
	// 0 - lowest it can go
	// 100 - highest it can go
    }
}
