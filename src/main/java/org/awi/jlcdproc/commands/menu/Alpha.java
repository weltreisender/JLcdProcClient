package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.commands.CommandUtils;
import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * Is visible as a text. When selected, a screen comes up that shows the current
 * string value, that you can edit with the cursor keys and Enter. The string is
 * ended by selecting a 'null' input character. After that the menu returns.
 */
public class Alpha extends MenuItem {

	private String value;

	private Integer minLength;

	private Integer maxLength;

	private String passwordChar;

	private Boolean allowCaps;

	private Boolean allowNonCaps;

	private Boolean allowNumbers;

	private String allow;

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param menu
	 *            Menu to which this menu item will belong. Might only be
	 *            <code>null</code> for the main menu.
	 * @param itemId
	 *            ID of the menu item
	 * @param name
	 *            Name that will be displayed
	 */
	Alpha(LcdProcInternal lcdProc, Menu menu, String itemId, String name) {
		super(lcdProc, menu, itemId, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#getType()
	 */
	@Override
	protected String getType() {

		return "alpha";
	}

	/**
	 * Set the current value of the menu item
	 * 
	 * @param value
	 *            Value of the menu item
	 * 
	 * @return this
	 */
	public Alpha value(String value) {

		this.value = value;
		return this;
	}

	/**
	 * Set the minimum allowed length
	 * 
	 * @param length
	 *            Length
	 * 
	 * @return this
	 */
	public Alpha minLength(int length) {

		this.minLength = length;
		return this;
	}

	/**
	 * Set the maximum allowed length
	 * 
	 * @param length
	 *            Length
	 * 
	 * @return this
	 */
	public Alpha maxLength(int length) {

		this.maxLength = length;
		return this;
	}

	/**
	 * Specifies, which character groups are allowed
	 * 
	 * @param allowed
	 *            {@link AllowedCharGroups}
	 * 
	 * @return this
	 */
	public Alpha allow(AllowedCharGroups... allowed) {

		allowCaps = false;
		allowNonCaps = false;
		allowNumbers = false;

		for (AllowedCharGroups a : allowed) {

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

	/**
	 * Allow all characters contained in the passed string. In this case,
	 * {@link #allow(AllowedCharGroups...)} should be called with
	 * {@link AllowedCharGroups#NONE}.
	 * 
	 * @param allow
	 *            String containing the allowed characters
	 * 
	 * @return this
	 */
	public Alpha allow(String allow) {

		this.allow = allow;
		return this;
	}

	/**
	 * If used, instead of the typed characters, this character will be visible.
	 * 
	 * @param passwordChar
	 *            Character, that will be displayed
	 * 
	 * @return this
	 */
	public Alpha passwordChar(char passwordChar) {

		this.passwordChar = new String(new char[] { passwordChar });
		return this;
	}

	/**
	 * Current value of the menu item
	 * 
	 * @return Current value.
	 */
	public String getValue() {

		return value;
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#onEvent(org.awi.jlcdproc.events.MenuEvent)
	 */
	@Override
	public void onEvent(MenuEvent event) {

		if (event.getType().hasValue()) {

			value = event.getValue();
		}
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#collectMenuItemOptions(org.awi.jlcdproc.commands.menu.MenuOptions)
	 */
	@Override
	protected void collectMenuItemOptions(MenuOptions options) throws Exception {

		options.add("-value", CommandUtils.quote(value));
		options.add("-minlength", minLength);
		options.add("-maxlength", maxLength);
		options.add("-password_char", passwordChar);
		options.add("-allow_caps", allowCaps);
		options.add("-allow_noncaps", allowNonCaps);
		options.add("-allow_numbers", allowNumbers);
		options.add("-allowed_extra", CommandUtils.quote(allow));
	}
}
