package org.awi.jlcdproc.commands.menu;

/**
 * Allowed character groups for {@link Alpha}
 */
public enum AllowedCharGroups {

	/**
	 * Use this to disable all other groups. Usefull, if you want to specify a
	 * set extra characters using {@link Alpha#allow(String)}.
	 */
	NONE,

	/**
	 * Allow captial letters
	 */
	CAPS,

	/**
	 * Allow lowercase letters
	 */
	NONCAPS,

	/**
	 * Allow numbers
	 */
	NUMBERS;
}