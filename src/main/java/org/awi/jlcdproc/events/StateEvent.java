package org.awi.jlcdproc.events;

public interface StateEvent extends Event {

	public boolean isActive();
	
	public String getScreenId();
}
