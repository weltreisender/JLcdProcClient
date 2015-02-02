package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.impl.LcdProcInternal;

public class VBarWidget extends BarWidget {

	public VBarWidget(LcdProcInternal lcdProc, Screen screen, String widgetId)
			throws Exception {
		
		super(lcdProc, screen, widgetId, "vbar");
	}

}
