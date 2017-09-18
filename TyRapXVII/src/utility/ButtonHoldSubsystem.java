package utility;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SafePWM;

/**
 * A basic button based subsystem that cycles between default and active states whenever the button is held and released
 * @author 0514844
 *
 */
public class ButtonHoldSubsystem extends DigitalSubsystem 
{
	/**
	 * 
	 Creates multiple constructors depending on what you pass to it. Can either be a PMW, a solenoid, or a relay
	 */
	public ButtonHoldSubsystem(Joystick t_controller, int t_rawButton, DoubleSolenoid t_solenoid, DoubleSolenoid.Value t_defaultState, DoubleSolenoid.Value t_activeState)
	{
		super(t_controller, t_rawButton, t_solenoid, t_defaultState, t_activeState);
	}
	
	public ButtonHoldSubsystem(Joystick t_controller, int t_rawButton, SafePWM t_motor, double t_defaultSpeed, double t_activeSpeed)
	{
		super(t_controller, t_rawButton, t_motor, t_defaultSpeed, t_activeSpeed);
	}
	
	public ButtonHoldSubsystem(Joystick t_controller, int t_rawButton, Relay t_relay, Relay.Value t_defaultValue, Relay.Value t_activeValue)
	{
		super(t_controller, t_rawButton, t_relay, t_defaultValue, t_activeValue);
	}
	
	private boolean state;
	@Override
	public void loop()
	{
		/**
		 * Is actuating whatever you decide to actuate while the button is held down.
		 *  If the button is not being pressed you are putting in 0 for the speed. 
		 *  If the button is being pressed then you are putting in whatever speed you put in the constructor
		 */
		state = m_controller.getRawButton(m_rawButton);
		
		actuate(state);
	}
}
