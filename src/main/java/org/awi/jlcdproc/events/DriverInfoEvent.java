package org.awi.jlcdproc.events;

public class DriverInfoEvent implements Event {

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
}
