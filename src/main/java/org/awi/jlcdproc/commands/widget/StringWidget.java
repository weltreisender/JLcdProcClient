package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

public class StringWidget extends Widget {

	public StringWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {

		super(lcdProc, screen, widgetId);
		
		widgetAdd("string");
	}

	public void set(int x, int y, String text) throws Exception {

		widgetSet(x, y, "\"" + text + "\"");
	}
}
