package org.awi.jlcdproc.events;

public abstract class EventListener<T extends Event> {

	private final Class<? extends T> expectedClass;
	
	public EventListener(Class<? extends T> expectedClass) {
		super();
		this.expectedClass = expectedClass;
	}

	public void onCheckedEvent(Event event) {
	
		try {
			
			onEvent(expectedClass.cast(event));
		} catch (ClassCastException e) {
			
			// Safely ignore
		}
	}
	
	public abstract void onEvent(T event);
}
