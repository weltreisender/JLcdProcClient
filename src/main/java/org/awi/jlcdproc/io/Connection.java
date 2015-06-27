package org.awi.jlcdproc.io;

import org.awi.jlcdproc.commands.Command;

/**
 * Connection interface. Implementations of this interface physically connect to
 * the LCDProc server.
 */
public interface Connection extends AutoCloseable {

	/**
	 * Connect to the LCDProc server
	 * 
	 * @throws Exception
	 *             if the connection could not be established
	 */
	public void connect() throws Exception;

	/**
	 * Send a command to the LCDProc server
	 * 
	 * @param command
	 *            {@link Command} that shall be sent to the LCDProc server
	 * 
	 * @throws Exception
	 *             if the command could not be sent
	 */
	public void send(CommandHolder command) throws Exception;
}