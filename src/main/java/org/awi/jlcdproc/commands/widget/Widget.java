package org.awi.jlcdproc.commands.widget;

import static org.awi.jlcdproc.commands.CommandParameters.params;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * Base class for all widgets that handles the basic communication with the
 * LCDproc server.
 */
public abstract class Widget extends Command {

	private static final String WIDGET_ADD = "widget_add";

	private static final String WIDGET_SET = "widget_set";

	private static final String WIDGET_DEL = "widget_del";

	private final Screen screen;

	private final String widgetId;

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param screen
	 *            {@link Screen}, to which this widget shall belong
	 * @param widgetId
	 *            ID for the widget
	 */
	protected Widget(LcdProcInternal lcdProc, Screen screen, String widgetId) {

		super(lcdProc);

		this.screen = screen;
		this.widgetId = widgetId;
	}

	/**
	 * Send command to LCDproc server to add the widget
	 * 
	 * @param type
	 *            Widget type
	 * @throws Exception
	 */
	protected void widgetAdd(String type) throws Exception {

		send(WIDGET_ADD, params(screen.getScreenId(), widgetId, type));
	}

	/**
	 * Send a command to the LCDproc server to set widget options
	 * 
	 * @param args
	 * @throws Exception
	 */
	protected void widgetSet(Object... args) throws Exception {

		Object[] parameters = new Object[args.length + 2];
		parameters[0] = screen.getScreenId();
		parameters[1] = widgetId;
		System.arraycopy(args, 0, parameters, 2, args.length);

		send(WIDGET_SET, params(parameters));
	}

	/**
	 * Delete the widget
	 * 
	 * @throws Exception
	 */
	public void delete() throws Exception {

		send(WIDGET_DEL);
	}
}
