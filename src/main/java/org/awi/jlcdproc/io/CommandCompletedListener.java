package org.awi.jlcdproc.io;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;

public class CommandCompletedListener implements EventListener, Callable<Event> {

	private AtomicReference<Event> event = new AtomicReference<>();
	
	private Thread currentThread;
	
	private final Class<? extends Event> expectedEvent;
	
	public CommandCompletedListener(Class<? extends Event> expectedEvent) {

		this.expectedEvent = expectedEvent;
	}
	
	@Override
	public void onEvent(Event event) {
		
		if (!expectedEvent.isInstance(event)) {
			
			return;
		}
		
		this.event.compareAndSet(null, event);
		currentThread.interrupt();
	}

	@Override
	public Event call() throws Exception {

		currentThread = Thread.currentThread();
		
		try {
			
			currentThread.join();
		} catch (InterruptedException e) {
			
		}
		
		return event.get();
	}
	
}
