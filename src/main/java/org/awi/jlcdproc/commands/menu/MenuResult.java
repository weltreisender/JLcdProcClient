package org.awi.jlcdproc.commands.menu;

public enum MenuResult {

	NONE,
	CLOSE,
	QUIT;
	
	@Override
	public String toString() {
		
		return name().toLowerCase();
	};
}
