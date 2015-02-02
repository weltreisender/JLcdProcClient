package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

public class Checkbox extends MenuItem {

	private CheckboxValue selectedValue;
	
	private Boolean allowGray;

	Checkbox(LcdProcInternal lcdProc, Menu menu, String itemId, String name, CheckboxValue selectedValue) {
		super(lcdProc, menu, itemId, name);

		this.selectedValue = selectedValue;
	}

	public Checkbox allowGray(boolean allowGray) {
		
		this.allowGray = allowGray;
		return this;
	}
	
	@Override
	protected String getType() {

		return "checkbox";
	}

	public CheckboxValue getSelectedValue() {
		return selectedValue;
	}
	
	@Override
	public void onEvent(MenuEvent event) {
	
		selectedValue = CheckboxValue.valueOf(event.getValue().toUpperCase());
	}
	
	@Override
	void collectMenuItemOptions(MenuOptions options) throws Exception {
	
		if (allowGray == null && selectedValue == CheckboxValue.GRAY) {
			
			selectedValue = CheckboxValue.OFF;
		}
		
		options.add("-value", selectedValue);
		options.add("-allow_gray", allowGray);
	}
}
