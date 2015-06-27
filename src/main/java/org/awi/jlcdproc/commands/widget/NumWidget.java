package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * A big number. They have a size of 3x4 characters. The special number 10 is a
 * colon, that you can use for a clock. This character is 1x4.
 */
public class NumWidget extends Widget {

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param screen
	 *            {@link Screen}, to which this widget shall belong
	 * @param widgetId
	 *            ID for the widget
	 */
	public NumWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {

		super(lcdProc, screen, widgetId);

		widgetAdd("num");
	}

	/**
	 * Displays decimal digit int at the horizontal position x, which is a
	 * normal character x coordinate on the display. The special value 10 for
	 * int displays a colon.
	 * 
	 * @param x
	 * @param num
	 * @throws Exception
	 */
	public void set(int x, int num) throws Exception {

		widgetSet(x, num);
	}
}
