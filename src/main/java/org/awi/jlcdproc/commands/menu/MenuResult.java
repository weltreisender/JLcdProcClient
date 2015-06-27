package org.awi.jlcdproc.commands.menu;

/**
 * Sets what to do with the menu when a menu action is selected
 */
public enum MenuResult {

	/**
	 * The menu stays as it is
	 */
	NONE,

	/**
	 * The menu closes and returns to a higher level
	 */
	CLOSE,

	/**
	 * Quits the menu completely so you can foreground your app
	 */
	QUIT;

	@Override
	public String toString() {

		return name().toLowerCase();
	};
}
