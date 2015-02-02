package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

public abstract class BarWidget extends Widget {

	public BarWidget(LcdProcInternal lcdProc, Screen screen, String widgetId, String type) throws Exception {
		super(lcdProc, screen, widgetId);

		widgetAdd(type);
	}
	
	public void set(int x, int y, int length) throws Exception {
		
		widgetSet(x, y, length);
	}

}
