package org.awi.jlcdproc.commands;

import org.awi.jlcdproc.impl.LcdProcInternal;


public class Info extends Command {

	public Info(LcdProcInternal lcdProc) {
		super(lcdProc);
	}

	@Override
	public String getCommand() {

		return "info";
	}
}
