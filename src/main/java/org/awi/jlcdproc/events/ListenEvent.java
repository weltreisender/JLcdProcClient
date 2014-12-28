package org.awi.jlcdproc.events;

public class ListenEvent implements StateEvent {

	private final String screenId;

	public ListenEvent(String screenId) {
	
		this.screenId = screenId;
	}

	@Override
	public boolean isActive() {

		return true;
	}
	
	@Override
	public String getScreenId() {
		
		return screenId;
	}
	
	@Override
	public String toString() {
		return "ListenEvent [screenId=" + screenId + "]";
	}
}
