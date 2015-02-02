package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

public class HBarWidget extends BarWidget {

	public HBarWidget(LcdProcInternal lcdProc, Screen screen, String widgetId)
			throws Exception {
		
		super(lcdProc, screen, widgetId, "hbar");
	}

}
