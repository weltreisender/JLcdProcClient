package org.awi.jlcdproc.events;

import org.awi.jlcdproc.commands.Command;

/**
 * Base interface for {@link Event Events} that occur as a result of
 * {@link Command Commands}.
 */
public interface CommandResultEvent extends Event {

	/**
	 * Indicates, if the event represents a successful event.
	 *  
	 * @return <code>true</code>, if command was executed successfully
	 */
	public boolean isSuccess();
}
