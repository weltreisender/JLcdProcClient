package org.awi.jlcdproc.widgets;

import org.awi.jlcdproc.io.Connection;

public class StringWidget extends Widget {

	public StringWidget(Connection connection, Screen screen, String widgetId) throws Exception {

		super(connection, screen, widgetId);
		
		widgetAdd("string");
	}

	public void set(int x, int y, String text) throws Exception {

		widgetSet(x, y, "\"" + text + "\"");
	}
}
