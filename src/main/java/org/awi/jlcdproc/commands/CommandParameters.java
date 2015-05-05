package org.awi.jlcdproc.commands;

public class CommandParameters {

	private final Object[] parameters;

	private CommandParameters(Object ... parameters) {
		super();
		this.parameters = parameters;
	} 
	
	public static CommandParameters params(Object ... parameters) {
		
		return new CommandParameters(parameters);
	}
	
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
