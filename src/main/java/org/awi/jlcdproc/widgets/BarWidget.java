package org.awi.jlcdproc.widgets;

import org.awi.jlcdproc.Screen;
import org.awi.jlcdproc.io.Connection;

public abstract class BarWidget extends Widget {

	public BarWidget(Connection connection, Screen screen, String widgetId, String type) throws Exception {
		super(connection, screen, widgetId);

		widgetAdd(type);
	}
	
	public void set(int x, int y, int length) throws Exception {
		
		widgetSet(x, y, length);
	}

}
