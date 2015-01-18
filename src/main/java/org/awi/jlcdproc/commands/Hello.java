package org.awi.jlcdproc.commands;

import org.awi.jlcdproc.io.Connection;


public class Hello extends Command {

	public Hello(Connection connection) {
		super(connection);
	}

	@Override
	public String getCommand() {

		return "hello";
	}
}
