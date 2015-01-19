package org.awi.jlcdproc.commands;

public enum Backlight {

	ON,
	OFF,
	TOGGLE,
	OPEN,
	BLINK,
	FLASH;
	
	public String toString() {
		
		return name().toLowerCase();
	}
}
