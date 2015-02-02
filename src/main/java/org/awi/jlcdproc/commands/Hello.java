package org.awi.jlcdproc.commands;

import org.awi.jlcdproc.impl.LcdProcInternal;


public class Hello extends Command {

	public Hello(LcdProcInternal lcdProc) {
		super(lcdProc);
	}

	@Override
	public String getCommand() {

		return "hello";
	}
}
