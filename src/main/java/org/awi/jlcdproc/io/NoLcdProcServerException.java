package org.awi.jlcdproc.io;

public class NoLcdProcServerException extends Exception {

	public NoLcdProcServerException(String host, int port) {

		super(String.format("No LCDproc server found at: %s:%d", host, port));
	}

}
