package org.awi.jlcdproc.io;

import org.awi.jlcdproc.commands.Command;

public class CommandExecutionTimeoutException extends Exception {

	public CommandExecutionTimeoutException(Command command) {

		super(String.format("Timeout for: <%s>", command.getCommand()));
	}
}
