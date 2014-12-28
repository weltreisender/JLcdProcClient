package org.awi.jlcdproc.widgets;

import org.awi.jlcdproc.Screen;
import org.awi.jlcdproc.io.Connection;

public class TitleWidget extends Widget {

	public TitleWidget(Connection connection, Screen screen, String widgetId) throws Exception {
		super(connection, screen, widgetId);

		widgetAdd("title");
	}

	public void setTitle(String title) throws Exception {
		
		widgetSet("\"" + title + "\"");
	}
}
