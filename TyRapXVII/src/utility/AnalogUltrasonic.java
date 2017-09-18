package utility;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Handles ultrasonic sensors that output between 0-5 volts. Adds a getInches() functions that allows you to actually get a workable number
 * @author TeamVoltage
 *
 */
public class AnalogUltrasonic extends AnalogInput
{
    double maxRange;
    double minRange;
    double multiplier;
    /**
     * Instantiates an analog ultrasonic sensor
     * @param channel
     * @param maxRangeInInches
     */
    public AnalogUltrasonic(int channel, double minRangeInInches, double maxRangeInInches)
    {
	super(channel);
	maxRange = maxRangeInInches;
	minRange = minRangeInInches;
	multiplier = (maxRange-minRange)/5;
	
    }
    
    /**
     * calculates how far away the target is based on the voltage and the max range of the sensor
     * @return the distance in inches
     */
    public double getInches()
    {
	return getVoltage()*multiplier+minRange;
    }
}