package org.awi.jlcdproc.commands;

import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * "hello" command used when a client connects to the LCDproc server.
 */
public class Hello extends Command {

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 */
	public Hello(LcdProcInternal lcdProc) {
		super(lcdProc);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.Command#getCommand()
	 */
	@Override
	public String getCommand() {

		return "hello";
	}
}
