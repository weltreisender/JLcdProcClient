package org.awi.jlcdproc.widgets;

public enum Direction {

	HORIZONTAL,
	VERTICAL,
	MARQUEE;
	
	public String getDirection() {
		
		return name().substring(0, 1).toLowerCase();
	}
}
