package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.commands.CommandUtils;
import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * Consists of a text and a status indicator. The status can be one of the
 * strings specified for the item.
 */
public class Ring extends MenuItem {

	private Integer selectedValue;

	private final String[] values;

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
	 *            Currently selected value (index to one of the strings in
	 *            "values").
	 * @param values
	 *            Allowed values in the ring
	 */
	Ring(LcdProcInternal lcdProc, Menu menu, String itemId, String name, int selectedValue, String... values) {

		super(lcdProc, menu, itemId, name);

		if (values == null || values.length == 0) {

			throw new IllegalArgumentException("No values passed.");
		}

		this.selectedValue = selectedValue < 0 || selectedValue > values.length ? 0 : selectedValue;
		this.values = values;
	}

	/**
	 * Get the array of all allowed values
	 * 
	 * @return Array of all allowed values
	 */
	public String[] getValues() {
		
		return values.clone();
	}

	/**
	 * Currently selected value
	 * 
	 * @return Currently selected value
	 */
	public String getSelectedValue() {
		
		return values[selectedValue];
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#onEvent(org.awi.jlcdproc.events.MenuEvent)
	 */
	@Override
	public void onEvent(MenuEvent event) {

		selectedValue = Integer.parseInt(event.getValue());
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#getType()
	 */
	@Override
	protected String getType() {

		return "ring";
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#collectMenuItemOptions(org.awi.jlcdproc.commands.menu.MenuOptions)
	 */
	@Override
	protected void collectMenuItemOptions(MenuOptions options) throws Exception {

		options.add("-value", selectedValue);
		options.add("-strings", CommandUtils.quote(String.join("\t", values)));
	}
}
