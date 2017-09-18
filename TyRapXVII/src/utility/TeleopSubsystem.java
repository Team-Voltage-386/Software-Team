package utility;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class can be extended in order to create a subsystem that runs
 * continually while the robot is in teleop.
 * 
 * @author DJ LabView
 *
 */
abstract public class TeleopSubsystem extends Subsystem
{
    public TeleopSubsystem()
    {
	super();
    }

    @Override
    public void run()
    {
	setup();
	while (!Thread.interrupted())
	{
	    while (RobotState.isOperatorControl())
	    {
		loop();
		Timer.delay(0.01);
	    }
	    Timer.delay(0.01);
	}
    }
}
