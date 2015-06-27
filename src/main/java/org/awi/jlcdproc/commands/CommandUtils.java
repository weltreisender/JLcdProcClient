package org.awi.jlcdproc.commands;

public class CommandUtils {

	/**
	 * Helper method to escape double quotation marks and add double quotes
	 * before and after the string
	 * 
	 * @param s
	 *            String to be quoted
	 * 
	 * @return Quoted string
	 */
	public static String quote(String s) {
	
		if (s == null) {
	
			return null;
		}
	
		String quotedString = s.replace("\"", "\"\"");
	
		return String.format("\"%s\"", quotedString);
	}

}
