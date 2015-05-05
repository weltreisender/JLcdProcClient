package org.awi.jlcdproc.io;


public interface Connection {

	public abstract void connect() throws Exception;

	public abstract void send(CommandHolder command) throws Exception;

}