package org.awi.jlcdproc.menu;

import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.io.Connection;

public class Slider extends MenuItem {

	private int value = 0;
	
	private Integer minValue;
	
	private Integer maxValue;
	
	private String minText;
	
	private String maxText;

	private Integer stepSize;
	
	Slider(Connection connection, Menu menu, String itemId, String name) {
		super(connection, menu, itemId, name);
	}

	@Override
	protected String getType() {

		return "slider";
	}

	public Slider value(int value) {
		
		this.value = value;
		return this;
	}
	
	public Slider minValue(int value) {
		
		this.minValue = value;
		return this;
	}
	
	public Slider maxValue(int value) {
		
		this.maxValue = value;
		return this;
	}
	
	public Slider minText(String text) {
		
		this.minText = text;
		return this;
	}
	
	public Slider maxText(String text) {
		
		this.maxText = text;
		return this;
	}
	
	public Slider stepSize(int stepSize) {
		
		this.stepSize = stepSize;
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
		options.add("-mintext", minText);
		options.add("-maxtext", maxText);
		options.add("-stepsize", stepSize);
	}
}
