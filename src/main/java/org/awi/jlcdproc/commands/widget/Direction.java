package org.awi.jlcdproc.commands.widget;


/**
 * Direction of {@link FrameWidget FrameWidgets} and {@link ScrollerWidget
 * ScrollerWidgets}.
 */
public enum Direction {

	HORIZONTAL,
	VERTICAL,
	MARQUEE;

	/**
	 * The direction needed for the LCDproc command is the first letter in lower
	 * case.
	 * 
	 * @return direction
	 */
	public String getDirection() {

		return name().substring(0, 1).toLowerCase();
	}
}
