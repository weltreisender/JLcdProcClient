package org.awi.jlcdproc.commands;

import org.awi.jlcdproc.events.CommandResultEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;


public class Hello extends Command {

	public Hello(LcdProcInternal lcdProc) {
		super(lcdProc);
	}

	@Override
	public boolean isCommandCompleted(CommandResultEvent event) {
	
		return true;
	}
	
	@Override
	public String getCommand() {

		return "hello";
	}
}
