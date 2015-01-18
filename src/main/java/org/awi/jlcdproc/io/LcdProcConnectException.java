package org.awi.jlcdproc.io;

public class LcdProcConnectException extends Exception {

	public LcdProcConnectException(String host, int port) {

		super(String.format("No LCDproc server found at: %s:%d", host, port));
	}

}
