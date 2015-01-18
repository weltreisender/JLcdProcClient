package org.awi.jlcdproc.commands;

import org.awi.jlcdproc.io.Connection;


public class Info extends Command {

	public Info(Connection connection) {
		super(connection);
	}

	@Override
	public String getCommand() {

		return "info";
	}
}
