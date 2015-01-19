package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.io.Connection;

public class VBarWidget extends BarWidget {

	public VBarWidget(Connection connection, Screen screen, String widgetId)
			throws Exception {
		
		super(connection, screen, widgetId, "vbar");
	}

}
