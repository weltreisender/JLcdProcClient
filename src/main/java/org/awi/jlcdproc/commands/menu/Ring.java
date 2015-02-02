package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

public class Ring extends MenuItem {

	private Integer selectedValue;

	private final String[] values;

	Ring(LcdProcInternal lcdProc, Menu menu, String itemId, String name, int selectedValue, String... values) {

		super(lcdProc, menu, itemId, name);

		if (values == null || values.length == 0) {
			
			throw new IllegalArgumentException("No values passed.");
		}
		
		this.selectedValue = selectedValue < 0 || selectedValue > values.length ? 0 : selectedValue;
		this.values = values;
	}

	public String[] getValues() {
		return values;
	}
	
	public String getSelectedValue() {
		return values[selectedValue];
	}

	@Override
	public void onEvent(MenuEvent event) {
		
		selectedValue = Integer.parseInt(event.getValue());
	}
	
	@Override
	protected String getType() {

		return "ring";
	}

	@Override
	void collectMenuItemOptions(MenuOptions options) throws Exception {
	
		options.add("-value", selectedValue);
		options.add("-strings", quote(String.join("\t", values)));
	}
}
