package org.awi.jlcdproc.io;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.events.ErrorEvent;
import org.awi.jlcdproc.events.Event;

/**
 * Holder object that combines the currently executed command, the blocked
 * thread that executed the command and the result event that was received for
 * the command.
 */
public class CommandHolder implements ChannelFutureListener {

	private final Command command;

	private final Thread blockedThread;

	private Event event;

	/**
	 * Constructor
	 * 
	 * @param command
	 *            Executed command
	 * @param blockedThread
	 *            Thread, that was blocked for that command
	 */
	public CommandHolder(Command command, Thread blockedThread) {
		super();
		this.blockedThread = blockedThread;
		this.command = command;
	}

	/**
	 * Getter
	 * 
	 * @return event.
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Updates the event for the the executed command.
	 * 
	 * The event is updated, if it was null or if the new event is an
	 * {@link ErrorEvent}.
	 * 
	 * @param event
	 */
	public void updateEvent(Event event) {

		if (this.event == null || event instanceof ErrorEvent) {

			this.event = event;
		}
	}

	/**
	 * Getter
	 * 
	 * @return blocked thread
	 */
	public Thread getBlockedThread() {
		return blockedThread;
	}

	/**
	 * Getter
	 * 
	 * @return command
	 */
	public Command getCommand() {
		return command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.netty.util.concurrent.GenericFutureListener#operationComplete(io.netty
	 * .util.concurrent.Future)
	 */
	@Override
	public void operationComplete(ChannelFuture future) throws Exception {

		// release the thread, if the netty command failed
		if (!future.isSuccess()) {
			blockedThread.interrupt();
		}
	}
}
