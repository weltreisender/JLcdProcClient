package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * A frame with that can contain widgets itself. In fact a frame displays an
 * other screen in it.
 */
public class FrameWidget extends Widget {

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param screen
	 *            {@link Screen}, to which this widget shall belong
	 * @param widgetId
	 *            ID for the widget
	 */
	public FrameWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {
		super(lcdProc, screen, widgetId);

		widgetAdd("frame");
	}

	/**
	 * Sets up a frame spanning from (left,top) to (right,bottom) that is width
	 * columns wide and height rows high. It scrolls in either horizontal (h) or
	 * vertical (v) direction at a speed of speed, which is the number of
	 * movements per rendering stroke (8 times/second).
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param width
	 * @param height
	 * @param direction
	 * @param speed
	 * @throws Exception
	 */
	public void set(int left, int top, int right, int bottom, int width, int height, Direction direction, int speed)
			throws Exception {

		widgetSet(left, top, right, bottom, width, height, direction.getDirection(), speed);
	}
}
