package org.awi.jlcdproc.io;

import org.awi.jlcdproc.commands.Command;

public interface Connection {

	public abstract void connect() throws Exception;

	public abstract void send(Command command) throws Exception;

}