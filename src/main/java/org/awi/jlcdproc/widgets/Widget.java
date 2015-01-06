package org.awi.jlcdproc.widgets;

import org.awi.jlcdproc.io.Connection;

public class Widget {

	protected static final String WIDGET_ADD = "widget_add";
	
	protected static final String WIDGET_SET = "widget_set";
	
	protected static final String WIDGET_DEL = "widget_del";
	
	protected final Connection connection;
	
	protected final Screen screen;
	
	protected final String widgetId;
	
	public Widget(Connection connection, Screen screen, String widgetId) {
		
		this.connection = connection;
		this.screen = screen;
		this.widgetId = widgetId;
	}
	
	protected void widgetAdd(String type) throws Exception {
		
		connection.send(WIDGET_ADD, screen.getScreenId(), widgetId, type);
	}
	
	protected void widgetSet(Object ... args) throws Exception {
		
		connection.send(WIDGET_SET, screen.getScreenId(), widgetId,  args);
	}
	
	public void delete() throws Exception {
		
		connection.send(WIDGET_DEL);
	}
}
