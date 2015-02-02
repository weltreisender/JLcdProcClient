package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

public class NumWidget extends Widget {

	public NumWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {

		super(lcdProc, screen, widgetId);
		
		widgetAdd("num");
	}

	public void set(int x, int num) throws Exception {

		widgetSet(x, num);
	}
}
