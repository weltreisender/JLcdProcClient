package org.awi.jlcdproc.widgets;

import org.awi.jlcdproc.Screen;
import org.awi.jlcdproc.io.Connection;

public class NumWidget extends Widget {

	public NumWidget(Connection connection, Screen screen, String widgetId) throws Exception {

		super(connection, screen, widgetId);
		
		widgetAdd("num");
	}

	public void set(int x, int num) throws Exception {

		widgetSet(x, num);
	}
}
