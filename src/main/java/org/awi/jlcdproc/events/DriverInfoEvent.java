package org.awi.jlcdproc.events;

import org.awi.jlcdproc.commands.Info;

/**
 * This event occurs as result of the {@link Info} command.
 */
public class DriverInfoEvent implements CommandResultEvent {

	private String driverInfo;

	/**
	 * Constructor
	 * 
	 * @param driverInfo Info returned by the LCDproc driver
	 */
	public DriverInfoEvent(String driverInfo) {
		
		this.driverInfo = driverInfo;
	}

	/**
	 * Getter
	 * 
	 * @return driver info
	 */
	public String getDriverInfo() {
		
		return driverInfo;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	
		return driverInfo;
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.events.CommandResultEvent#isSuccess()
	 */
	@Override
	public boolean isSuccess() {

		return true;
	}
}
