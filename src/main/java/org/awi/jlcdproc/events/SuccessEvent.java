package org.awi.jlcdproc.events;


/**
 * The LCDproc server sends one or more events of this type for each
 * successfully executed command.
 */
public class SuccessEvent implements CommandResultEvent {

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.events.CommandResultEvent#isSuccess()
	 */
	@Override
	public boolean isSuccess() {

		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Success";
	}
}
