package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.commands.CommandUtils;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * A simple text.
 */
public class StringWidget extends Widget {

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param screen
	 *            {@link Screen}, to which this widget shall belong
	 * @param widgetId
	 *            ID for the widget
	 */
	protected StringWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {

		super(lcdProc, screen, widgetId);
		
		widgetAdd("string");
	}

	/**
	 * Displays text at position (x,y). 
	 * 
	 * @param x
	 * @param y
	 * @param text
	 * @throws Exception
	 */
	public void set(int x, int y, String text) throws Exception {

		widgetSet(x, y, CommandUtils.quote(text));
	}
}
