package org.awi.jlcdproc.commands;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.concurrent.atomic.AtomicReference;

import org.awi.jlcdproc.events.CommandResultEvent;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.impl.LcdProcInternal;
import org.awi.jlcdproc.io.CommandExecutionException;
import org.awi.jlcdproc.io.CommandExecutionTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstraction of commands that can be sent to the LCDproc server.
 * <p>
 * Sending a command blocks the sending thread until the response from the
 * LCDproc server is received.
 */
public abstract class Command implements ChannelFutureListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	protected final LcdProcInternal lcdProc;
	
	private AtomicReference<CommandResultEvent> event = new AtomicReference<>();

	private Thread blockedThread = null;

	private String commandString;

	/**
	 * Constructor
	 * @param lcdProc TODO
	 */
	public Command(LcdProcInternal lcdProc) {

		this.lcdProc = lcdProc;
	}

	public String getCommand() {

		return commandString;
	};

	/**
	 * Hook to process a {@link CommandResultEvent} to determine, if a command
	 * is completed.
	 * <p>
	 * While most commands are completed when either a succes or an error event
	 * is received, some commands respond with both error AND success events.
	 * Therefore the concrete command implementation has to return whether is is
	 * completed or not.
	 * 
	 * @param event
	 *            {@link Event} received from the LCDproc server
	 * 
	 * @return <code>true</code>, if the command is terminated,
	 *         <code>false</code> otherwise
	 */
	public boolean isCommandCompleted(CommandResultEvent event) {

		return true;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public void send(Object... args) throws Exception {

		StringBuilder commandBuilder = new StringBuilder();

		for (Object arg : args) {

			if (arg instanceof Object[]) {

				Object[] objects = (Object[]) arg;
				for (Object object : objects) {

					if (object != null) {

						commandBuilder.append(object).append(" ");
					}
				}
			} else {

				if (arg != null) {

					commandBuilder.append(arg).append(" ");
				}
			}
		}

		commandString = commandBuilder.toString().trim();

		blockedThread = Thread.currentThread();

		lcdProc.getConnection().send(this);

		try {

			blockedThread.join(500);
			logger.error("Timeout for " + toString());
			throw new CommandExecutionTimeoutException(this);
		} catch (InterruptedException e) {
		}

		if (!isSuccess()) {
			throw new CommandExecutionException(this);
		}

	}

	public boolean onCommandResultEvent(CommandResultEvent event) {

		this.event.compareAndSet(null, event);

		boolean commandTerminated = isCommandCompleted(event);
		if (commandTerminated) {

			blockedThread.interrupt();
		}

		return commandTerminated;
	}

	public boolean isComplete() {

		return event.get() != null;
	}

	public boolean isSuccess() {

		return event.get().isSuccess();
	}

	public CommandResultEvent getEvent() {

		return event.get();
	}

	@Override
	public void operationComplete(ChannelFuture future) throws Exception {

		if (!future.isSuccess()) {
			blockedThread.interrupt();
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "=[" + getCommand() + "]";
	}
}
