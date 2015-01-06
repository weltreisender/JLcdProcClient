package org.awi.jlcdproc;

import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.StateEvent;
import org.awi.jlcdproc.io.Connection;
import org.awi.jlcdproc.widgets.Backlight;
import org.awi.jlcdproc.widgets.Cursor;
import org.awi.jlcdproc.widgets.FrameWidget;
import org.awi.jlcdproc.widgets.HBarWidget;
import org.awi.jlcdproc.widgets.Heartbeat;
import org.awi.jlcdproc.widgets.IconWidget;
import org.awi.jlcdproc.widgets.NumWidget;
import org.awi.jlcdproc.widgets.Priority;
import org.awi.jlcdproc.widgets.ScrollerWidget;
import org.awi.jlcdproc.widgets.StringWidget;
import org.awi.jlcdproc.widgets.TitleWidget;
import org.awi.jlcdproc.widgets.VBarWidget;

public class Screen implements EventListener {

	private static final String SCREEN_ADD = "screen_add";

	private static final String SCREEN_DEL = "screen_del";

	private static final String SCREEN_SET = "screen_set";
	
	private ScreenState state = ScreenState.UNKNOWN;

	private String screenId;
	
	private Connection connection;
	
	private int currentWidgetId = 0; 
	
	public Screen(Connection connection, int screenId) throws Exception {

		this(connection, Integer.toString(screenId));
	}

	public Screen(Connection connection, String screenId) throws Exception {

		this.connection = connection;
		this.screenId = screenId;
		
		connection.addEventListener(this);
		
		connection.send(SCREEN_ADD, screenId);
	}

	public ScreenState getState() {
		
		return state;
	}
	
	private Screen screenSet(Object ... args) throws Exception {
		
		connection.send(SCREEN_SET, screenId, args);
		
		return this;
	}
	
	public Screen setName(String name) throws Exception {
		
		return screenSet("-name", name);
	}
	
	public Screen setWidth(int width, int height) throws Exception {
		
		return screenSet("-wid", width, "-hgt", height);
	}
	
	public Screen setPriority(Priority priority) throws Exception {
		
		return screenSet("-priority", priority.name().toLowerCase());
	}
	
	public Screen setPriority(int priority) throws Exception {
		
		return screenSet("-priority", priority);
	}
	
	public Screen setHeartbeat(Heartbeat heartbeat) throws Exception {
		
		return screenSet("-heartbeat", heartbeat.name().toLowerCase());
	}
	
	public Screen setBacklight(Backlight backlight) throws Exception {
		
		return screenSet("-backlight", backlight.name().toLowerCase());
	}
	
	public Screen setDuration(int duration) throws Exception {
		
		return screenSet("-duration", duration);
	}

	public Screen setTimeout(int timeout) throws Exception {
		
		return screenSet("-timeout", timeout);
	}

	public Screen setCursor(Cursor cursor) throws Exception {
		
		return screenSet("-cursor", cursor.name().toLowerCase());
	}
	
	public Screen setCursorPosition(int x, int y) throws Exception {
		
		return screenSet("-cursor_x", x, "-cursor_y", y);
	}

	public void delete() throws Exception {
		
		connection.removeEventListener(this);
		
		connection.send(SCREEN_DEL, screenId);
	}
	
	public StringWidget stringWidget() throws Exception {
		
		return stringWidget(Integer.toString(currentWidgetId++));
	}

	public StringWidget stringWidget(String widgetId) throws Exception {
		
		return new StringWidget(connection, this, widgetId);
	}

	public TitleWidget titleWidget() throws Exception {
		
		return titleWidget(Integer.toString(currentWidgetId++));
	}

	public TitleWidget titleWidget(String widgetId) throws Exception {
		
		return new TitleWidget(connection, this, widgetId);
	}

	public VBarWidget vbarWidget() throws Exception {
		
		return vbarWidget(Integer.toString(currentWidgetId++));
	}

	public VBarWidget vbarWidget(String widgetId) throws Exception {
		
		return new VBarWidget(connection, this, widgetId);
	}

	public HBarWidget hbarWidget() throws Exception {
		
		return hbarWidget(Integer.toString(currentWidgetId++));
	}

	public HBarWidget hbarWidget(String widgetId) throws Exception {
		
		return new HBarWidget(connection, this, widgetId);
	}

	public IconWidget iconWidget() throws Exception {
		
		return iconWidget(Integer.toString(currentWidgetId++));
	}

	public IconWidget iconWidget(String widgetId) throws Exception {
		
		return new IconWidget(connection, this, widgetId);
	}

	public ScrollerWidget scrollerWidget() throws Exception {
		
		return scrollerWidget(Integer.toString(currentWidgetId++));
	}

	public ScrollerWidget scrollerWidget(String widgetId) throws Exception {
		
		return new ScrollerWidget(connection, this, widgetId);
	}
	
	public FrameWidget frameWidget() throws Exception {
		
		return frameWidget(Integer.toString(currentWidgetId++));
	}

	public FrameWidget frameWidget(String widgetId) throws Exception {
		
		return new FrameWidget(connection, this, widgetId);
	}
	
	public NumWidget numWidget() throws Exception {
		
		return numWidget(Integer.toString(currentWidgetId++));
	}

	public NumWidget numWidget(String widgetId) throws Exception {
		
		return new NumWidget(connection, this, widgetId);
	}
	
	public String getScreenId() {

		return screenId;
	}

	@Override
	public void onEvent(Event event) {

		if (!(event instanceof StateEvent)) {
			
			return;
		}
		
		StateEvent stateEvent = (StateEvent) event;
		
		if (stateEvent.getScreenId().equals(screenId)) {
			
			state = stateEvent.isActive() ? ScreenState.ACTIVE : ScreenState.INACTIVE;
		}
	}
}
