package org.awi.jlcdproc.commands;

public enum Backlight {

	ON,
	OFF,
	TOGGLE,
	OPEN,
	BLINK,
	FLASH;
	
	@Override
	public String toString() {
		
		return name().toLowerCase();
	}
}
