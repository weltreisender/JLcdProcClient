package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.impl.LcdProcInternal;

public class Widget extends Command {

	protected static final String WIDGET_ADD = "widget_add";
	
	protected static final String WIDGET_SET = "widget_set";
	
	protected static final String WIDGET_DEL = "widget_del";
	
	protected final Screen screen;
	
	protected final String widgetId;
	
	public Widget(LcdProcInternal lcdProc, Screen screen, String widgetId) {
		
		super(lcdProc);
		
		this.screen = screen;
		this.widgetId = widgetId;
	}
	
	protected void widgetAdd(String type) throws Exception {
		
		send(WIDGET_ADD, screen.getScreenId(), widgetId, type);
	}
	
	protected void widgetSet(Object ... args) throws Exception {
		
		send(WIDGET_SET, screen.getScreenId(), widgetId,  args);
	}
	
	public void delete() throws Exception {
		
		send(WIDGET_DEL);
	}
}
