package org.awi.jlcdproc.io;

import org.awi.jlcdproc.commands.Command;

/**
 * Exception that thrown if a command could not be executed 
 */
public class CommandExecutionException extends Exception {

	/**
	 * Constructor
	 * 
	 * @param command {@link Command} that could not be exected
	 */
	public CommandExecutionException(Command command) {

		super(String.format("Could not execute command: <%s> Result: <%s>", command.getCommand(), command.getEvent() != null ? command.getEvent().toString() : "unknown"));
	}
}
