package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

public class TitleWidget extends Widget {

	public TitleWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {
		super(lcdProc, screen, widgetId);

		widgetAdd("title");
	}

	public void setTitle(String title) throws Exception {
		
		widgetSet("\"" + title + "\"");
	}
}
