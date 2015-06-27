package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * Abstract base class for {@link HBarWidget} and {@link VBarWidget}. Handles
 * the common functionality.
 */
public abstract class BarWidget extends Widget {

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param screen
	 *            {@link Screen}, to which this widget shall belong
	 * @param widgetId
	 *            ID for the widget
	 * @param type
	 *            Widget type (hbar or vbar)
	 */
	protected BarWidget(LcdProcInternal lcdProc, Screen screen, String widgetId, String type) throws Exception {
		super(lcdProc, screen, widgetId);

		widgetAdd(type);
	}

	/**
	 * Displays a horizontal (hbar) resp. vertical (vbar) starting at position
	 * (x,y) that is length pixels wide resp. high.
	 * 
	 * @param x
	 * @param y
	 * @param length
	 * @throws Exception
	 */
	public void set(int x, int y, int length) throws Exception {

		widgetSet(x, y, length);
	}

}
