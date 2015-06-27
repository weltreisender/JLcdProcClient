package org.awi.jlcdproc.commands;

import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * "info" command used to retrieve information about the currently used LCDproc
 * driver.
 */
public class Info extends Command {

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 */
	public Info(LcdProcInternal lcdProc) {
		super(lcdProc);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.Command#getCommand()
	 */
	@Override
	public String getCommand() {

		return "info";
	}
}
