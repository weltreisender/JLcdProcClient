package org.awi.jlcdproc.events;

import org.awi.jlcdproc.commands.widget.Screen;

/**
 * Each time a {@link Screen} gets inactive, this event is sent by the LCDproc server
 */
public class IgnoreEvent extends StateEvent {

	/**
	 * Constructor 
	 * 
	 * @param screenId ID of the {@link Screen} that is deactivated.
	 */
	public IgnoreEvent(String screenId) {
		super(screenId);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.events.StateEvent#isActive()
	 */
	@Override
	public boolean isActive() {
	
		return false;
	}
	}
