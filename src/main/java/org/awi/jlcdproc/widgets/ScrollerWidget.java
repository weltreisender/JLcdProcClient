package org.awi.jlcdproc.widgets;

import org.awi.jlcdproc.Screen;
import org.awi.jlcdproc.io.Connection;

public class ScrollerWidget extends Widget {

	public ScrollerWidget(Connection connection, Screen screen, String widgetId) throws Exception {
		super(connection, screen, widgetId);

		widgetAdd("scroller");
	}

	public void set(int left, int top, int right, int bottom, Direction direction, int speed, String text) throws Exception {

		widgetSet(left,  top, right, bottom, direction.getDirection(), speed, "\"" + text + "\"");
	}
}
