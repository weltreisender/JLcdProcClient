package org.awi.jlcdproc.commands.keys;

import org.awi.jlcdproc.events.KeyEvent;

/**
 * Callback interface for key events.
 */
public interface KeyEventListener {

	/**
	 * This method is called, if a certain key was pressed
	 * 
	 * @param event
	 *            {@link KeyEvent}
	 */
	public void onEvent(KeyEvent event);
}
