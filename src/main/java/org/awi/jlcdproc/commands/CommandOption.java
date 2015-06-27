package org.awi.jlcdproc.commands;

/**
 * {@link Command Commands} may have one or more options. An option has a name
 * and an argument.
 */
public class CommandOption {

	private final String option;
	private final Object arg;

	/**
	 * Constructor
	 * 
	 * @param option
	 * @param arg
	 */
	public CommandOption(String option, Object arg) {

		this.option = option;
		this.arg = arg;
	}

	/**
	 * Getter
	 * 
	 * @return option
	 */
	public String getOption() {

		return option;
	}

	/**
	 * Getter
	 * 
	 * @return arg
	 */
	public Object getArg() {

		return arg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CommandOption [option" + "=" + arg + "]";
	}

}