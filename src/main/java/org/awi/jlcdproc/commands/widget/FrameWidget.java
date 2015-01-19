package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.commands.Direction;
import org.awi.jlcdproc.io.Connection;

public class FrameWidget extends Widget {

	public FrameWidget(Connection connection, Screen screen, String widgetId) throws Exception {
		super(connection, screen, widgetId);

		widgetAdd("frame");
	}

	public void set(int left, int top, int right, int bottom, int width, int height, Direction direction, int speed)
			throws Exception {

		widgetSet(left, top, right, bottom, width, height, direction.getDirection(), speed);
	}
}
