package org.awi.jlcdproc.io;

public class LcdProcHelloException extends Exception {

	public LcdProcHelloException() {
		
		super("Could not connect to LCDproc daemon in time");
	}
}
