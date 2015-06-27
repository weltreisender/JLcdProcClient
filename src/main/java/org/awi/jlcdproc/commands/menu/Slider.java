package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * Is visible as a text. When selected, a screen comes up that shows a slider.
 * You can set the slider using the cursor keys. When Enter is pressed, the menu
 * returns.
 */
public class Slider extends MenuItem {

	private int value = 0;

	private Integer minValue;

	private Integer maxValue;

	private String minText;

	private String maxText;

	private Integer stepSize;

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
	Slider(LcdProcInternal lcdProc, Menu menu, String itemId, String name) {
		super(lcdProc, menu, itemId, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#getType()
	 */
	@Override
	protected String getType() {

		return "slider";
	}

	/**
	 * Set current value
	 * 
	 * @param value
	 *            Current value
	 * 
	 * @return this
	 */
	public Slider value(int value) {

		this.value = value;
		return this;
	}

	/**
	 * Set minimum value
	 * 
	 * @param value
	 *            Min. value
	 * 
	 * @return this
	 */
	public Slider minValue(int value) {

		this.minValue = value;
		return this;
	}

	/**
	 * Set maximum value
	 * 
	 * @param value
	 *            Max. value
	 * 
	 * @return this
	 */
	public Slider maxValue(int value) {

		this.maxValue = value;
		return this;
	}

	/**
	 * Text at the left side of the slider
	 * 
	 * @param text
	 * 
	 * @return this
	 */
	public Slider minText(String text) {

		this.minText = text;
		return this;
	}

	/**
	 * Text at the right side of the slider
	 * 
	 * @param text
	 * @return this
	 */
	public Slider maxText(String text) {

		this.maxText = text;
		return this;
	}

	/**
	 * The stepsize of the slider. If you use 0, you can control the movement
	 * completely from your client.
	 * 
	 * @param stepSize
	 * @return this
	 */
	public Slider stepSize(int stepSize) {

		this.stepSize = stepSize;
		return this;
	}

	/**
	 * Get the current value
	 * @return Current value
	 */
	public int getValue() {

		return value;
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#onEvent(org.awi.jlcdproc.events.MenuEvent)
	 */
	@Override
	public void onEvent(MenuEvent event) {

		if (event.getType().hasValue()) {

			value = Integer.parseInt(event.getValue());
		}
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#collectMenuItemOptions(org.awi.jlcdproc.commands.menu.MenuOptions)
	 */
	@Override
	protected void collectMenuItemOptions(MenuOptions options) throws Exception {

		options.add("-value", value);
		options.add("-minvalue", minValue);
		options.add("-maxvalue", maxValue);
		options.add("-mintext", minText);
		options.add("-maxtext", maxText);
		options.add("-stepsize", stepSize);
	}
}
