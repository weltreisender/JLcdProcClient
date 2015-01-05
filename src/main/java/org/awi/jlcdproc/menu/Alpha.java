package org.awi.jlcdproc.menu;

import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.io.Connection;

public class Alpha extends MenuItem {

	public enum Allowed {
		
		NONE,
		CAPS,
		NONCAPS,
		NUMBERS;
	}
	
	private String value;
	
	private Integer minLength;
	
	private Integer maxLength;
	
	private String passwordChar;
	
	private Boolean allowCaps;
	
	private Boolean allowNonCaps;
	
	private Boolean allowNumbers;
	
	private String allow;

	Alpha(Connection connection, Menu menu, String itemId, String name) {
		super(connection, menu, itemId, name);
	}

	@Override
	protected String getType() {

		return "alpha";
	}

	public Alpha value(String value) {
		
		this.value = value;
		return this;
	}
	
	public Alpha minLength(int length) {
		
		this.minLength = length;
		return this;
	}
	
	public Alpha maxLength(int length) {
		
		this.maxLength = length;
		return this;
	}
	
	public Alpha allow(Allowed ...allowed) {
		
		allowCaps = false;
		allowNonCaps = false;
		allowNumbers = false;
		
		for (Allowed a : allowed) {
			
			switch (a) {
			case CAPS:
				allowCaps = true;
				break;
			case NONCAPS:
				allowNonCaps = true;
				break;
			case NUMBERS:
				allowNumbers = true;
				break;

			default:
				break;
			}
		}
		
		return this;
	}
	
	public Alpha allow(String allow) {
		
		this.allow = allow;
		return this;
	}
	
	public Alpha passwordChar(char passwordChar) {
		
		this.passwordChar = new String(new char[] {passwordChar});
		return this;
	}
	
	public String getValue() {
		
		return value;
	}
	
	@Override
	public void onEvent(MenuEvent event) {
	
		if (event.getType().hasValue()) {
			
			value = event.getValue();
		}
	}
	
	@Override
	void collectMenuItemOptions(MenuOptions options) throws Exception {

		options.add("-value", quote(value));
		options.add("-minlength", minLength);
		options.add("-maxlength", maxLength);
		options.add("-password_char", passwordChar);
		options.add("-allow_caps", allowCaps);
		options.add("-allow_noncaps", allowNonCaps);
		options.add("-allow_numbers", allowNumbers);
		options.add("-allow_extra", quote(allow));
}
}
