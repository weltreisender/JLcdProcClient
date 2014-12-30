package org.awi.jlcdproc.menu;

public enum MenuResult {

	NONE,
	CLOSE,
	QUIT;
	
	public String toString() {
		
		return name().toLowerCase();
	};
}
