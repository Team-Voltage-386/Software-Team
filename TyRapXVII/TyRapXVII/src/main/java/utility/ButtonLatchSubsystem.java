package utility;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SafePWM;

/**
 * A button based subsystem that cycles between default and active states
 * whenever the button is pressed
 * 
 * @author h
 *
 */
public class ButtonLatchSubsystem extends DigitalSubsystem
{
    /**
     * 
     * Sets up constructors depending on what you want.
     */
    public ButtonLatchSubsystem(Joystick t_controller, int t_rawButton, DoubleSolenoid t_solenoid,
	    DoubleSolenoid.Value t_defaultState, DoubleSolenoid.Value t_activeState)
    {
	super(t_controller, t_rawButton, t_solenoid, t_defaultState, t_activeState);
    }

    public ButtonLatchSubsystem(Joystick t_controller, int t_rawButton, SafePWM t_motor, double t_defaultSpeed,
	    double t_activeSpeed)
    {
	super(t_controller, t_rawButton, t_motor, t_defaultSpeed, t_activeSpeed);
    }

    public ButtonLatchSubsystem(Joystick t_controller, int t_rawButton, Relay t_relay, Relay.Value t_defaultValue,
	    Relay.Value t_activeValue)
    {
	super(t_controller, t_rawButton, t_relay, t_defaultValue, t_activeValue);
    }
    
    protected boolean previousButtonState = false;
    protected boolean currentButtonState = false;
    protected boolean latchState = false;

    @Override
    /**
     * Creates a button latch so when you press the button it stays the same
     * until pressed again
     */
    public void loop()
    {
	currentButtonState = m_controller.getRawButton(m_rawButton);
	if (currentButtonState == true && previousButtonState == false)
	{
	    latchState = !latchState;
	}
	previousButtonState = currentButtonState;

	actuate(latchState);
    }
}