package org.awi.jlcdproc.commands;

import static org.awi.jlcdproc.commands.CommandParameters.params;

import java.util.concurrent.atomic.AtomicReference;

import org.awi.jlcdproc.commands.menu.MenuItem;
import org.awi.jlcdproc.events.CommandResultEvent;
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
 * LCDproc server is received. This is done because there is no other way to
 * associate LCDproc's response (success, failure, ...) to the request.
 */
public abstract class Command {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	protected final LcdProcInternal lcdProc;

	protected AtomicReference<CommandResultEvent> resultEvent = new AtomicReference<>();

	protected Thread blockedThread = null;

	private String commandString;

	private int expectedResultEvents;

	private int receivedResultEvents = 0;

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 */
	public Command(LcdProcInternal lcdProc) {

		this.lcdProc = lcdProc;
	}

	/**
	 * Getter
	 * 
	 * @return returns the assembled command for the LCDproc server.
	 */
	public String getCommand() {

		return commandString;
	};

	/**
	 * Hook to process a {@link CommandResultEvent} to determine, if a command
	 * is completed.
	 * <p>
	 * A command is completed, when the number of the expected result events
	 * matches the number of received result events.
	 * 
	 * @param event
	 *            {@link Event} received from the LCDproc server
	 * 
	 * @return <code>true</code>, if the command is terminated,
	 *         <code>false</code> otherwise
	 */
	public boolean isCommandCompleted(CommandResultEvent event) {

		return expectedResultEvents == ++receivedResultEvents;
	}

	/**
	 * Send a command without parameters and without options to the LCDproc
	 * server.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	public void send() throws Exception {

		send("", CommandParameters.params());
	}

	/**
	 * Send a command without parameter but with options tho the LCDproc server
	 * 
	 * @param command
	 *            Name of the command
	 * @param options
	 *            Options of the command
	 * 
	 * @throws Exception
	 */
	public void send(String command, CommandOption... options) throws Exception {

		send(command, params(), options);
	}

	/**
	 * Sends a command with parameters and options to the LCDproc server.
	 * 
	 * @param command
	 *            Name of the command
	 * @param parameters
	 *            Parameters of the command
	 * @param options
	 *            Options of the command
	 * 
	 * @throws Exception
	 */
	public void send(String command, CommandParameters parameters, CommandOption... options) throws Exception {

		// determine the expected result events:
		// -For each option, a result event is expected (at least one result
		// event, if no option is present)
		// -For MenuItems only one result event is returned, independent of the
		// number of options
		expectedResultEvents = options.length == 0 || this instanceof MenuItem ? 1 : options.length;
		receivedResultEvents = 0;

		// Assemble the command string
		StringBuilder commandBuilder = new StringBuilder(command).append(" ").append(parameters);

		for (CommandOption option : options) {

			if (option != null) {

				commandBuilder.append(option.getOption()).append(" ").append(option.getArg()).append(" ");
			}
		}

		commandString = commandBuilder.toString().trim();

		if (logger.isDebugEnabled()) {
			logger.debug("Sending " + toString());
		}

		// Reset the result event
		resultEvent.set(null);

		// The current thread will be blocked until all expected result events
		// are received or a timeout occurs
		Thread currentThread = Thread.currentThread();

		CommandHolder commandHolder = new CommandHolder(this, currentThread);
		lcdProc.getConnection().send(commandHolder);

		try {

			// block the current thread with timeout
			currentThread.join(1000);

			// throw an exception, if the timeout occurs
			logger.error("Timeout for " + toString());
			throw new CommandExecutionTimeoutException(this);
		} catch (InterruptedException e) {
			// An interrupt is expected, so do nothing here
		}

		// set the result event
		CommandResultEvent commandResultEvent = (CommandResultEvent) commandHolder.getEvent();
		resultEvent.set(commandResultEvent);

		if (!isSuccess(commandResultEvent)) {
			throw new CommandExecutionException(this);
		}
	}

	private boolean isSuccess(CommandResultEvent event) {

		return event != null && event.isSuccess();
	}

	/**
	 * Getter for the result event
	 * 
	 * @return Result event of the command
	 */
	public CommandResultEvent getEvent() {

		return resultEvent.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "=[" + getCommand() + "]";
	}
}
