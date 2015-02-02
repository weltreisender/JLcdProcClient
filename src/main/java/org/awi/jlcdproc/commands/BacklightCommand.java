package org.awi.jlcdproc.commands;

import org.awi.jlcdproc.impl.LcdProcInternal;


public class BacklightCommand extends Command {

	private Backlight backlight;

	public BacklightCommand(LcdProcInternal lcdProc, Backlight backlight) {
		super(lcdProc);
		
		this.backlight = backlight;
	}

	@Override
	public String getCommand() {

		return String.format("backlight %s", backlight);
	}
}
