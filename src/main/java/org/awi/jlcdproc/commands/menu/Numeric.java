package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

public class Numeric extends MenuItem {

	private int value = 0;
	
	private Integer minValue;
	
	private Integer maxValue;

	Numeric(LcdProcInternal lcdProc, Menu menu, String itemId, String name) {
		super(lcdProc, menu, itemId, name);
	}

	@Override
	protected String getType() {

		return "numeric";
	}

	public Numeric value(int value) {
		
		this.value = value;
		return this;
	}
	
	public Numeric minValue(int value) {
		
		this.minValue = value;
		return this;
	}
	
	public Numeric maxValue(int value) {
		
		this.maxValue = value;
		return this;
	}
	
	public int getValue() {
		
		return value;
	}
	
	@Override
	public void onEvent(MenuEvent event) {
	
		if (event.getType().hasValue()) {
			
			value = Integer.parseInt(event.getValue());
		}
	}
	
	@Override
	void collectMenuItemOptions(MenuOptions options) throws Exception {

		options.add("-value", value);
		options.add("-minvalue", minValue);
		options.add("-maxvalue", maxValue);
	}
}
