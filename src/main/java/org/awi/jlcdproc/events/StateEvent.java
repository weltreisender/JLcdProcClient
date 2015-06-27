package org.awi.jlcdproc.events;

import org.awi.jlcdproc.commands.widget.Screen;

/**
 * Base interface for events that indicate activation or deactivation of
 * {@link Screen Screens}.
 */
public abstract class StateEvent implements Event {

	private final String screenId;

	/**
	 * Constructor 
	 * 
	 * @param screenId ID of the {@link Screen} that is deactivated.
	 */
	public StateEvent(String screenId) {
	
		this.screenId = screenId;
	}

	/**
	 * Returns <code>true</code>, if the associated {@link Screen} was
	 * activated. Otherwise <code>false</code>
	 * 
	 * @return <code>true</code>, if the {@link Screen} was activated.
	 */
	public abstract boolean isActive();

	/**
	 * Returns the screen ID for which the event was emitted.
	 * 
	 * @return Screen id.
	 */
	public String getScreenId() {
		
		return screenId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return getClass().getSimpleName() + " [screenId=" + screenId + "]";
	}
}
