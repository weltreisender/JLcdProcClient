package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.commands.Icon;
import org.awi.jlcdproc.impl.LcdProcInternal;

public class IconWidget extends Widget {

	public IconWidget(LcdProcInternal lcdProc, Screen screen, String widgetId) throws Exception {
		super(lcdProc, screen, widgetId);

		widgetAdd("icon");
	}

	public void set(int x, int y, Icon icon) throws Exception {
		
		set(x, y, icon.name().toLowerCase());
	}
	
	public void set(int x, int y, String icon) throws Exception {
		
		widgetSet(x, y, icon);
	}
	
	
}
