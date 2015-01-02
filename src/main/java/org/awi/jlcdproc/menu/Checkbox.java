package org.awi.jlcdproc.menu;

import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.io.Connection;

public class Checkbox extends MenuItem {

	public enum Value {

		ON,
		OFF,
		GRAY;

		public String toString() {

			return name().toLowerCase();
		}
	}

	private Value selectedValue;
	
	private Boolean allowGray;

	Checkbox(Connection connection, Menu menu, String itemId, String name, Value selectedValue) {
		super(connection, menu, itemId, name);

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

	public Value getSelectedValue() {
		return selectedValue;
	}
	
	@Override
	public void onEvent(MenuEvent event) {
	
		selectedValue = Value.valueOf(event.getValue().toUpperCase());
	}
	
	@Override
	void collectMenuItemOptions(MenuOptions options) throws Exception {
	
		if (allowGray == null && selectedValue == Value.GRAY) {
			
			selectedValue = Value.OFF;
		}
		
		options.add("-value", selectedValue);
		options.add("-allow_gray", allowGray);
	}
}
