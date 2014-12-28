package org.awi.jlcdproc.events;

public class SuccessEvent implements FunctionEvent {

	@Override
	public boolean isSuccess() {
	
		return true;
	}
	
	@Override
	public String toString() {
		return "Success";
	}
}
