package org.awi.jlcdproc.commands.menu;

import java.util.ArrayList;

import org.awi.jlcdproc.commands.CommandOption;

/**
 * Helper class to collect options for menu items
 */
class MenuOptions {

	private ArrayList<CommandOption> options = new ArrayList<>();
	
	public void add(String option, Object value) {
		
		if (value == null) {
			return;
		}
		
		options.add(new CommandOption(option, value));
	}
	
	public CommandOption[] optionsAsArray() {
		
		return options.toArray(new CommandOption[options.size()]);
	}

	@Override
	public String toString() {
		return "MenuOptions [options=" + options + "]";
	}
}
