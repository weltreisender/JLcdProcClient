package org.awi.jlcdproc.commands;

import org.awi.jlcdproc.commands.widget.Screen;

/**
 * Heartbeat options for the {@link Screen} command.
 */
public enum Heartbeat {

	/**
	 * Turns the heartbeat on.
	 */
	ON,

	/**
	 * Turns the heartbeat off.
	 */
	OFF,

	/**
	 * The client's heartbeat setting will be used. (Can only be set in the
	 * LCDproc client menu).
	 */
	OPEN
}
