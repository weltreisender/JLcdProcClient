package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * A horizontal bar.
 */
public class HBarWidget extends BarWidget {

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param screen
	 *            {@link Screen}, to which this widget shall belong
	 * @param widgetId
	 *            ID for the widget
	 */
	public HBarWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {

		super(lcdProc, screen, widgetId, "hbar");
	}

}
