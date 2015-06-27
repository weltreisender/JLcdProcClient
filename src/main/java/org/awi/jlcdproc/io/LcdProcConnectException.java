package org.awi.jlcdproc.io;

/**
 * This exception is thrown, if the LCDProc server could not be connected.
 */
public class LcdProcConnectException extends Exception {

	/**
	 * Constructor
	 * 
	 * @param host
	 * @param port
	 */
	public LcdProcConnectException(String host, int port) {

		super(String.format("No LCDproc server found at: %s:%d", host, port));
	}

}
