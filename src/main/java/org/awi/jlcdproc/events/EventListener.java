package org.awi.jlcdproc.events;

import org.awi.jlcdproc.LcdProc;
import org.awi.jlcdproc.commands.Command;

/**
 * Each class that needs to react on {@link Event Events} must implement this
 * interface and register itself at the {@link Command} or the {@link LcdProc}
 * instance that emits the {@link Event}.
 */
public interface EventListener {

	/**
	 * Callback method that must be implemented to handle events.
	 * 
	 * @param event
	 *            Event sent by the LCDproc server.
	 */
	public void onEvent(Event event);
}
