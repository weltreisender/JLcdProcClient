package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * A predefined icon.
 */
public class IconWidget extends Widget {

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param screen
	 *            {@link Screen}, to which this widget shall belong
	 * @param widgetId
	 *            ID for the widget
	 */
	public IconWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {
		super(lcdProc, screen, widgetId);

		widgetAdd("icon");
	}

	/**
	 * Displays the icon iconname at position (x,y).
	 * 
	 * @param x
	 * @param y
	 * @param icon
	 * 
	 * @throws Exception
	 */
	public void set(int x, int y, Icon icon) throws Exception {

		widgetSet(x, y, icon.name().toLowerCase());
	}
}
