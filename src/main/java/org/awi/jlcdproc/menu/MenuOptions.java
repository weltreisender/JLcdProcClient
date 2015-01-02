package org.awi.jlcdproc.menu;

import java.util.ArrayList;

public class MenuOptions {

	private ArrayList<Object> options = new ArrayList<>();
	
	public void add(String option, Object value) {
		
		if (value == null) {
			return;
		}
		
		options.add(option);
		options.add(value);
	}
	
	public void add(Object value) {
		
		if (value == null) {
			return;
		}
		
		options.add(value);
	}
	
	public Object[] optionsAsArray() {
		
		return options.toArray();
	}
}
