package org.awi.jlcdproc.io;

public class LcdProcConnectException extends Exception {

	public LcdProcConnectException() {
		
		super("Could not connect to LCDproc daemon in time");
	}
}
