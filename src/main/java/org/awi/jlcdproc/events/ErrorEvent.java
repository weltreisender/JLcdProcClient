package org.awi.jlcdproc.events;

import org.awi.jlcdproc.commands.Command;

/**
 * If a {@link Command} could not be executed successfully, this event is thrown. 
 */
public class ErrorEvent implements CommandResultEvent {

	private final String message;

	/**
	 * Constructor
	 * 
	 * @param message Error message returned by the LCDproc server
	 */
	public ErrorEvent(String message) {
		super();
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.events.CommandResultEvent#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ErrorEvent [message=" + message + "]";
	}
}
