package org.awi.jlcdproc.events;

public class ErrorEvent implements FunctionEvent {

	private final String message;

	public ErrorEvent(String message) {
		super();
		this.message = message;
	}

	@Override
	public boolean isSuccess() {
		
		return false;
	}
	
	@Override
	public String toString() {
		return "ErrorEvent [message=" + message + "]";
	}
}
