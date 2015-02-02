package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.commands.Direction;
import org.awi.jlcdproc.impl.LcdProcInternal;

public class ScrollerWidget extends Widget {

	public ScrollerWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {
		super(lcdProc, screen, widgetId);

		widgetAdd("scroller");
	}

	public void set(int left, int top, int right, int bottom, Direction direction, int speed, String text) throws Exception {

		widgetSet(left,  top, right, bottom, direction.getDirection(), speed, "\"" + text + "\"");
	}
}
