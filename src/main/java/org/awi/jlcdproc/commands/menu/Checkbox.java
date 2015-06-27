package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * Consists of a text and a status indicator. The status can be on (Y), off (N)
 * or gray (o).
 */
public class Checkbox extends MenuItem {

	private CheckboxValue selectedValue;

	private Boolean allowGray;

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
	 * @param selectedValue
	 *            Current value of the check box
	 */
	Checkbox(LcdProcInternal lcdProc, Menu menu, String itemId, String name, CheckboxValue selectedValue) {
		super(lcdProc, menu, itemId, name);

		this.selectedValue = selectedValue;
	}

	/**
	 * Set to <code>true</code>, if "gray" state should be allowed.
	 * 
	 * @param allowGray
	 *            <code>true</code>, if the "gray" state should be allowed
	 * 
	 * @return this
	 */
	public Checkbox allowGray(boolean allowGray) {

		this.allowGray = allowGray;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#getType()
	 */
	@Override
	protected String getType() {

		return "checkbox";
	}

	/**
	 * Current value of the check box
	 * 
	 * @return Current value
	 */
	public CheckboxValue getSelectedValue() {
		return selectedValue;
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

		selectedValue = CheckboxValue.valueOf(event.getValue().toUpperCase());
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

		if (allowGray == null && selectedValue == CheckboxValue.GRAY) {

			selectedValue = CheckboxValue.OFF;
		}

		options.add("-value", selectedValue);
		options.add("-allow_gray", allowGray);
	}
}
