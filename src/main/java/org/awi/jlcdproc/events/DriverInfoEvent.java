package org.awi.jlcdproc.events;

public class DriverInfoEvent implements CommandResultEvent {

	private String driverInfo;

	public DriverInfoEvent(String driverInfo) {
		
		this.driverInfo = driverInfo;
	}

	public String getDriverInfo() {
		
		return driverInfo;
	}
	
	@Override
	public String toString() {
	
		return driverInfo;
	}

	@Override
	public boolean isSuccess() {

		return true;
	}
}
