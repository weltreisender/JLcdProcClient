package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.io.Connection;

public class Action extends MenuItem {

	private MenuResult menuResult;
	
	Action(Connection connection, Menu menu, String itemId, String name) {
		super(connection, menu, itemId, name);
	}

	@Override
	protected String getType() {

		return "action";
	}

	@Override
	void collectMenuItemOptions(MenuOptions options) throws Exception {

		options.add("-menu_result", menuResult);
	}

	public Action menuResult(MenuResult menuResult) {
		
		this.menuResult = menuResult;
		return this;
	}
}
