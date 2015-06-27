package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.commands.CommandUtils;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * A variation of the string type that scrolls the text horizontally or
 * vertically.
 */
public class ScrollerWidget extends Widget {

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param screen
	 *            {@link Screen}, to which this widget shall belong
	 * @param widgetId
	 *            ID for the widget
	 */
	protected ScrollerWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {
		super(lcdProc, screen, widgetId);

		widgetAdd("scroller");
	}

	/**
	 * Displays a scroller spanning from position (left,top) to (right,bottom)
	 * scrolling text in horizontal, vertical or marquee direction
	 * at a speed of speed, which is the number of movements per rendering
	 * stroke (8 times/second).
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param direction
	 * @param speed
	 * @param text
	 * @throws Exception
	 */
	public void set(int left, int top, int right, int bottom, Direction direction, int speed, String text) throws Exception {

		widgetSet(left, top, right, bottom, direction.getDirection(), speed, CommandUtils.quote(text));
	}
}
