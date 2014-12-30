package org.awi.jlcdproc.io;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;

public class CommandCompletedListener implements EventListener, Callable<Event> {

	private AtomicReference<Event> event = new AtomicReference<>();
	
	private final Class<? extends Event> expectedEvent;
	
	public CommandCompletedListener(Class<? extends Event> expectedEvent) {

		this.expectedEvent = expectedEvent;
	}
	
	@Override
	public void onEvent(Event event) {
		
		if (!expectedEvent.isInstance(event)) {
			
			return;
		}
		
		this.event.set(event);
	}

	@Override
	public Event call() throws Exception {

		while (event.get() == null){
			Thread.sleep(50);
		}
		
		return event.get();
	}
	
}
