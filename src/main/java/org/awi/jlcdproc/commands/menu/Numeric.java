package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * Allows the user to input an integer value. Is visible as a text. When
 * selected, a screen comes up that shows the current numeric value, that you
 * can edit with the cursor keys and Enter. The number is ended by selecting a
 * 'null' input digit. After that the menu returns.
 */
public class Numeric extends MenuItem {

	private int value = 0;

	private Integer minValue;

	private Integer maxValue;

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param menu
	 *            Menu to which this menu item will belong. Might only be
	 *            <code>null</code> for the main menu.
	 * @param itemId
	 *            ID of the menu item
	 * @param name
	 *            Name that will be displayed
	 */
	Numeric(LcdProcInternal lcdProc, Menu menu, String itemId, String name) {
		super(lcdProc, menu, itemId, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#getType()
	 */
	@Override
	protected String getType() {

		return "numeric";
	}

	/**
	 * Set the current value
	 * 
	 * @param value
	 *            Current value
	 * 
	 * @return this
	 */
	public Numeric value(int value) {

		this.value = value;
		return this;
	}

	/**
	 * Set the minimum allowed value
	 * 
	 * @param value
	 *            Min. allowed value
	 * 
	 * @return this
	 */
	public Numeric minValue(int value) {

		this.minValue = value;
		return this;
	}

	/**
	 * Set the maximum allowed number
	 * 
	 * @param value
	 *            Max. allowed number
	 * 
	 * @return this
	 */
	public Numeric maxValue(int value) {

		this.maxValue = value;
		return this;
	}

	/**
	 * Current value
	 * 
	 * @return current value
	 */
	public int getValue() {

		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.awi.jlcdproc.commands.menu.MenuItem#onEvent(org.awi.jlcdproc.events
	 * .MenuEvent)
	 */
	@Override
	public void onEvent(MenuEvent event) {

		if (event.getType().hasValue()) {

			value = Integer.parseInt(event.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.awi.jlcdproc.commands.menu.MenuItem#collectMenuItemOptions(org.awi
	 * .jlcdproc.commands.menu.MenuOptions)
	 */
	@Override
	protected void collectMenuItemOptions(MenuOptions options) throws Exception {

		options.add("-value", value);
		options.add("-minvalue", minValue);
		options.add("-maxvalue", maxValue);
	}
}
