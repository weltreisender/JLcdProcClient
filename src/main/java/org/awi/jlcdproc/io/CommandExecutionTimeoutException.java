package org.awi.jlcdproc.io;

import org.awi.jlcdproc.commands.Command;

/**
 * Exception that is thrown, if the exected command timed out
 */
public class CommandExecutionTimeoutException extends Exception {

	/**
	 * Constructor
	 * 
	 * @param command {@link Command} that timed out
	 */
	public CommandExecutionTimeoutException(Command command) {

		super(String.format("Timeout for: <%s>", command.getCommand()));
	}
}
