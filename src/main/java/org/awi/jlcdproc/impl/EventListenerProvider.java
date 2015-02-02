package org.awi.jlcdproc.impl;

import org.awi.jlcdproc.commands.keys.Key;
import org.awi.jlcdproc.commands.menu.MenuItem;
import org.awi.jlcdproc.commands.widget.Screen;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;

public interface EventListenerProvider {

	/**
	 * Add an {@link EventListener}. The {@link EventListener} will be called
	 * for every event received from the LCDproc server.
	 * 
	 * There are specialized event listeners for {@link Key}, {@link Screen} and
	 * {@link MenuItem} objects that can be added to these objects. They should
	 * be prefered to this method as they are called only, if the event is for
	 * the given object.
	 * 
	 * @param eventListener
	 *            {@link EventListener} to add
	 */
	public abstract void addEventListener(EventListener eventListener);

	/**
	 * Removes the {@link EventListener}
	 * 
	 * @param eventListener
	 *            {@link EventListener} to remove
	 */
	public abstract void removeEventListener(EventListener eventListener);

	/**
	 * Fire the given {@link Event} to all listeners asynchronously
	 * 
	 * @param event
	 *            {@link Event} to fire
	 */
	public abstract void fireEvent(Event event);

}