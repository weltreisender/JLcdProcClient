package org.awi.jlcdproc.events;

public class IgnoreEvent implements StateEvent {

	private final String screenId;

	public IgnoreEvent(String screenId) {
	
		this.screenId = screenId;
	}

	@Override
	public boolean isActive() {
	
		return false;
	}
	
	@Override
	public String getScreenId() {
		
		return screenId;
	}
	
	@Override
	public String toString() {
		return "IgnoreEvent [screenId=" + screenId + "]";
	}
}
