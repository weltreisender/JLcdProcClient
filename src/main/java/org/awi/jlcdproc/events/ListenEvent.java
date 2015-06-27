package org.awi.jlcdproc.events;

import org.awi.jlcdproc.commands.widget.Screen;

/**
 * Each time a {@link Screen} gets active, this event is sent by the LCDproc
 * server
 */
public class ListenEvent extends StateEvent {

	/**
	 * Constructor 
	 *
	 * @param screenId ID of the {@link Screen} that is activated.
	 */
	public ListenEvent(String screenId) {

		super(screenId);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.events.StateEvent#isActive()
	 */
	@Override
	public boolean isActive() {

		return true;
	}
}
