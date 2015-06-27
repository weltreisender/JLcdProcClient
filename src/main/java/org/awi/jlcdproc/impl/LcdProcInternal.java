package org.awi.jlcdproc.impl;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListenerProvider;
import org.awi.jlcdproc.io.Connection;

/**
 * Interface used by the {@link LcdProcImpl} to expose the {@link Connection} to
 * the {@link Command Commands}.
 */
public interface LcdProcInternal extends EventListenerProvider {

	/**
	 * Getter
	 * 
	 * @return Connection object used to communicate with the LCDProc server.
	 */
	public Connection getConnection();
	
	/**
	 * Fire the given {@link Event} to all listeners asynchronously
	 * 
	 * @param event
	 *            {@link Event} to fire
	 */
	public abstract void fireEvent(Event event);
}
