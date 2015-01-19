package org.awi.jlcdproc.commands;

import org.awi.jlcdproc.io.Connection;


public class BacklightCommand extends Command {

	private Backlight backlight;

	public BacklightCommand(Connection connection, Backlight backlight) {
		super(connection);
		
		this.backlight = backlight;
	}

	@Override
	public String getCommand() {

		return String.format("backlight %s", backlight);
	}
}
