package org.awi.jlcdproc.commands.menu;

/**
 * Possible values for {@link Checkbox Checkboxes}
 */
public enum CheckboxValue {

	/**
	 * The {@link Checkbox} is checked
	 */
	ON,
	
	/**
	 * The {@link Checkbox} is not checked
	 */
	OFF,
	
	/**
	 * The {@link Checkbox} is gray
	 */
	GRAY;

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {

		return name().toLowerCase();
	}
}