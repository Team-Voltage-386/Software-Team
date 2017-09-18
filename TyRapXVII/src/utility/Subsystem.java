package utility;

import edu.wpi.first.wpilibj.Timer;

/**
 * This class can be extended in order to create a subsystem that runs
 * continually while the robot is enabled.
 * 
 * @author Adamn
 *
 */
abstract public class Subsystem extends Thread
{
    /**
     * Creates a constructor for the class then starts all the subsystems.
     * Therefore, once you call upon the method it will begin without you
     * telling it to.
     */
    public Subsystem()
    {
	super();
	start();
    }

    public void run()
    {
	setup();
	while (!Thread.interrupted())
	{
	    loop();
	    Timer.delay(0.005);
	}
    }

    abstract public void loop();

    public void setup()
    {

    }
}
