package org.awi.jlcdproc.io;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;

public class CommandCompletedListener<T extends Event> extends EventListener<T> implements Callable<T> {

	private AtomicReference<T> event = new AtomicReference<>();
	
	public CommandCompletedListener(Class<? extends T> expectedClass) {
		super(expectedClass);
	}
	
	@Override
	public void onEvent(T event) {
		
		this.event.set(event);
	}

	@Override
	public T call() throws Exception {

		while (event.get() == null){
			Thread.sleep(50);
		}
		
		return event.get();
	}
	
}
