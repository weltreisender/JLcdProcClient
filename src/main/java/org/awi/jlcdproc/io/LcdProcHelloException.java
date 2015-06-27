package org.awi.jlcdproc.io;

/**
 * This exception is thrown, if the "hello" command could not be sent to the
 * LCDProc server.
 */
public class LcdProcHelloException extends Exception {

	/**
	 * Constructor 
	 */
	public LcdProcHelloException() {

		super("Could not connect to LCDproc daemon in time");
	}
}
