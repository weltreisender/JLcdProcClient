package org.awi.jlcdproc.io;

public class CommandExecutionException extends Exception {

	public CommandExecutionException(String command) {

		super(String.format("Could not execute command: <%s> ", command));
	}

	public CommandExecutionException(String command, String result) {

		super(String.format("Could not execute command: <%s>\nResult: <%s>",
				command, result));
	}

}
