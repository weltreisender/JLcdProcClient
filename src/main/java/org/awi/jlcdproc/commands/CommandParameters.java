package org.awi.jlcdproc.commands;

/**
 * {@link Command Commands} may have one or more parameters which are
 * represented by this class.
 */
public class CommandParameters {

	private final Object[] parameters;

	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	private CommandParameters(Object... parameters) {
		super();
		this.parameters = parameters;
	}

	/**
	 * Helper method to create an instance of this class
	 * 
	 * @param parameters
	 * @return Instance of this class
	 */
	public static CommandParameters params(Object... parameters) {

		return new CommandParameters(parameters);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (Object object : parameters) {

			sb.append(object);
			sb.append(" ");
		}

		return sb.toString();
	}
}
