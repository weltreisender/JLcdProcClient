package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.events.MenuEvent;

/**
 * Event listener interface that allows the user to react on menu events.
 */
public interface MenuEventListener {

	/**
	 * Method that will be called when an event was received.
	 * 
	 * @param event {@link MenuEvent}
	 */
	public void onEvent(MenuEvent event);
}
