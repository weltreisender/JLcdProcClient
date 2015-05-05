package org.awi.jlcdproc.commands;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.concurrent.atomic.AtomicReference;

import org.awi.jlcdproc.commands.menu.MenuItem;
import org.awi.jlcdproc.events.CommandResultEvent;
import org.awi.jlcdproc.events.ErrorEvent;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.impl.LcdProcInternal;
import org.awi.jlcdproc.io.CommandExecutionException;
import org.awi.jlcdproc.io.CommandExecutionTimeoutException;
import org.awi.jlcdproc.io.CommandHolder;
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

	protected AtomicReference<CommandResultEvent> event = new AtomicReference<>();

	protected Thread blockedThread = null;

	private String commandString;

	private int optionCount;

	private int completionCount = 0;
	
	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 *            TODO
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

		return optionCount == ++completionCount;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public void send() throws Exception {
		
		send("", CommandParameters.params());
	}
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public void send(String command, CommandParameters parameters, CommandOption ... args) throws Exception {

		completionCount = 0;
		optionCount = args.length == 0 || this instanceof MenuItem ? 1 : args.length;
		
		StringBuilder commandBuilder = new StringBuilder(command).append(" ").append(parameters);

		for (CommandOption arg : args) {

			if (arg != null) {

				commandBuilder.append(arg.getOption()).append(" ").append(arg.getArg()).append(" ");
			}
		}

		commandString = commandBuilder.toString().trim();

		if (logger.isDebugEnabled()) {
			logger.debug("Sending " + toString());
		}

		event.set(null);

		Thread blockedThread = Thread.currentThread();

		CommandHolder commandHolder = new CommandHolder(this, blockedThread);
		lcdProc.getConnection().send(commandHolder);

		try {

			blockedThread.join(1000);
			logger.error("Timeout for " + toString());
			throw new CommandExecutionTimeoutException(this);
		} catch (InterruptedException e) {
		}

		CommandResultEvent commandResultEvent = (CommandResultEvent) commandHolder.getEvent();

		event.set(commandResultEvent);

		if (!isSuccess(commandResultEvent)) {
			throw new CommandExecutionException(this);
		}

	}

	public boolean onCommandResultEvent(CommandHolder commandHolder, CommandResultEvent event) {

		if (logger.isDebugEnabled()) {

			logger.debug(String.format("%s received for %s", event, this));
		}

		commandHolder.setEvent(commandHolder.getEvent() == null || event instanceof ErrorEvent ? event : commandHolder.getEvent());

		boolean commandTerminated = isCommandCompleted(event);
		if (commandTerminated) {

			commandHolder.getBlockedThread().interrupt();
		}

		return commandTerminated;
	}

	public boolean isSuccess(CommandResultEvent event) {

		return event != null && event.isSuccess();
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
