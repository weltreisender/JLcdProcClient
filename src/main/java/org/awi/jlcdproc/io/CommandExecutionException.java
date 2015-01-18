package org.awi.jlcdproc.io;

import org.awi.jlcdproc.commands.Command;

public class CommandExecutionException extends Exception {

	public CommandExecutionException(Command command) {

		super(String.format("Could not execute command: <%s> Result: <%s>", command.getCommand(), command.getEvent().toString()));
	}
}
