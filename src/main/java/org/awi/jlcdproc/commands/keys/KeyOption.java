package org.awi.jlcdproc.commands.keys;

import org.awi.jlcdproc.commands.CommandOption;

public class KeyOption extends CommandOption {

	public KeyOption(Object arg) {
		super("", arg);
	}
	
	public KeyOption(String option, Object arg) {
		super(option, arg);
	}
}
