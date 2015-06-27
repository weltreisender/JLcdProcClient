package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * This item should trigger an action. It consists of simple text.
 */
public class Action extends MenuItem {

	private MenuResult menuResult;

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
	Action(LcdProcInternal lcdProc, Menu menu, String itemId, String name) {
		super(lcdProc, menu, itemId, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#getType()
	 */
	@Override
	protected String getType() {

		return "action";
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

		options.add("-menu_result", menuResult);
	}

	/**
	 * Sets what to do with the menu when this action is selected.
	 * 
	 * @param menuResult {@link MenuResult}
	 * 
	 * @return this
	 */
	public Action menuResult(MenuResult menuResult) {

		this.menuResult = menuResult;
		return this;
	}
}
