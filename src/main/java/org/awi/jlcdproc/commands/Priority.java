package org.awi.jlcdproc.commands;

/**
 * This enum describes the priority of a screen.
 */
public enum Priority {

	/** The screen will never be visible */
	HIDDEN,
	/** The screen is only visible when no normal info screens exists */
	BACKGROUND,
	/** Normal info screen, default priority */
	INFO,
	/** An active client */
	FOREGROUND,
	/** The screen has an important message for the user. */
	ALERT,
	/** The client is doing interactive input. */
	INPUT
}
