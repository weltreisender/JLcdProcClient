package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.commands.CommandUtils;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * A title bar on top of the screen. 
 */
public class TitleWidget extends Widget {

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param screen
	 *            {@link Screen}, to which this widget shall belong
	 * @param widgetId
	 *            ID for the widget
	 */
	public TitleWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {
		super(lcdProc, screen, widgetId);

		widgetAdd("title");
	}

	/**
	 * Set the given title
	 * 
	 * @param title
	 * @throws Exception
	 */
	public void setTitle(String title) throws Exception {
		
		widgetSet(CommandUtils.quote(title));
	}
}
