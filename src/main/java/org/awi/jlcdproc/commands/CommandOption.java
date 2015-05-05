package org.awi.jlcdproc.commands;

public class CommandOption {

	private final String option;
	private final Object arg;
	
	
	
	public CommandOption(String option, Object arg) {
	
		this.option = option;
		this.arg = arg;
	}

	public String getOption() {

		return option;
	}

	public Object getArg() {

		return arg;
	}

	@Override
	public String toString() {
		return "CommandOption [option" + "=" + arg + "]";
	}

	
}