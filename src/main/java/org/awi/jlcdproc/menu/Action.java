package org.awi.jlcdproc.menu;

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
	Object[] getMenuItemArguments() throws Exception {

		return new Object[] {option("-menu_result", menuResult)};
	}

	public Action menuResult(MenuResult menuResult) {
		
		this.menuResult = menuResult;
		return this;
	}
}
