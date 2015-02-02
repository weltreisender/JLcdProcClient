package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.impl.LcdProcInternal;

public class Action extends MenuItem {

	private MenuResult menuResult;
	
	Action(LcdProcInternal lcdProc, Menu menu, String itemId, String name) {
		super(lcdProc, menu, itemId, name);
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
