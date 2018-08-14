package utility;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SafePWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A teleop subsystem with constructors for the three types of actuators I could
 * think of at the moment of writing. Designed to allow a digital actuation to
 * be easily made from a digital button.
 * 
 * @author yo mom
 *
 */
abstract class DigitalSubsystem extends TeleopSubsystem
{
    protected enum actuator
    {
	solenoid, motor, relay
    }

    protected Joystick m_controller;
    protected int m_rawButton;

    protected actuator m_actuationMethod;

    protected DoubleSolenoid m_solenoid;
    protected DoubleSolenoid.Value m_defaultState;
    protected DoubleSolenoid.Value m_activeState;

    protected SafePWM m_motor;
    protected double m_defaultSpeed;
    protected double m_activeSpeed;

    protected Relay m_relay;
    protected Relay.Value m_defaultValue;
    protected Relay.Value m_activeValue;

    /**
     * 
     * Setting up constructors for the digital subsystem. Differs depending on
     * what you are putting into it. PWM, Solenoid, or Relay
     */

    protected DigitalSubsystem(Joystick t_controller, int t_rawButton, DoubleSolenoid t_solenoid,
	    DoubleSolenoid.Value t_defaultState, DoubleSolenoid.Value t_activeState)
    {
	super();
	m_controller = t_controller;
	m_rawButton = t_rawButton;
	m_actuationMethod = actuator.solenoid;
	m_solenoid = t_solenoid;
	m_defaultState = t_defaultState;
	m_activeState = t_activeState;
    }

    protected DigitalSubsystem(Joystick t_controller, int t_rawButton, SafePWM t_motor, double t_defaultSpeed,
	    double t_activeSpeed)
    {
	super();
	m_controller = t_controller;
	m_rawButton = t_rawButton;
	m_actuationMethod = actuator.motor;
	m_motor = t_motor;
	m_defaultSpeed = t_defaultSpeed;
	m_activeSpeed = t_activeSpeed;
	//System.out.println(4);
    }

    protected DigitalSubsystem(Joystick t_controller, int t_rawButton, Relay t_relay, Relay.Value t_defaultValue,
	    Relay.Value t_activeValue)
    {
	super();
	m_controller = t_controller;
	m_rawButton = t_rawButton;
	m_actuationMethod = actuator.motor;
	m_relay = t_relay;
	m_defaultValue = t_defaultValue;
	m_activeValue = t_activeValue;
    }

    /**
     * 
     * @param buttonState
     *            is setting (whatever you put in) to (whatever you put in). If
     *            the button is false then it will stay the default value.I f
     *            the button is pressed it will be set to the value of your
     *            choice.
     */

    protected void actuate(boolean buttonState)
    {
	if (buttonState == false)
	{
	    switch (m_actuationMethod)
	    {
	    case motor:
		m_motor.setSpeed(m_defaultSpeed);
		break;
	    case solenoid:
		m_solenoid.set(m_defaultState);
		break;
	    case relay:
		SmartDashboard.putString("Relay state", "Default");
		m_relay.set(m_defaultValue);
		break;
	    }
	}

	else if (buttonState == true)
	{
	    switch (m_actuationMethod)
	    {
	    case motor:
		m_motor.setSpeed(m_activeSpeed);
		break;
	    case solenoid:
		m_solenoid.set(m_activeState);
		break;
	    case relay:
		SmartDashboard.putString("Relay state", "Active");
		m_relay.set(m_activeValue);
		break;
	    }
	}
    }
}