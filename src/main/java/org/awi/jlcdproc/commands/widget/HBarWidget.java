package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.io.Connection;

public class HBarWidget extends BarWidget {

	public HBarWidget(Connection connection, Screen screen, String widgetId)
			throws Exception {
		
		super(connection, screen, widgetId, "hbar");
	}

}
