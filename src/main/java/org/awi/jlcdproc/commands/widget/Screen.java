package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.commands.Backlight;
import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.commands.Heartbeat;
import org.awi.jlcdproc.commands.Priority;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.StateEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * Class representing an LCDproc screen.
 * <p> 
 * It encapsulates the screen identifier that may either be generated automatically or provided by the user.
 * <p>
 * The class provides several methods to create {@link Widget Widgets} that will be shown on this screen.
 */
public class Screen extends Command implements EventListener {

	private static final String SCREEN_ADD = "screen_add";

	private static final String SCREEN_DEL = "screen_del";

	private static final String SCREEN_SET = "screen_set";
	
	private ScreenState state = ScreenState.UNKNOWN;

	private String screenId;
	
	private int currentWidgetId = 0; 
	
	public Screen(LcdProcInternal lcdProc, int screenId) throws Exception {

		this(lcdProc, Integer.toString(screenId));
	}

	public Screen(LcdProcInternal lcdProc, String screenId) throws Exception {
		
		super(lcdProc);

		this.screenId = screenId;
		
		lcdProc.addEventListener(this);
		
		send(SCREEN_ADD, screenId);
	}

	public ScreenState getState() {
		
		return state;
	}
	
	private Screen screenSet(Object ... args) throws Exception {
		
		send(SCREEN_SET, screenId, args);
		
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
		
		lcdProc.removeEventListener(this);
		
		send(SCREEN_DEL, screenId);
	}
	
	public StringWidget stringWidget() throws Exception {
		
		return stringWidget(Integer.toString(currentWidgetId++));
	}

	public StringWidget stringWidget(String widgetId) throws Exception {
		
		return new StringWidget(lcdProc, this, widgetId);
	}

	public TitleWidget titleWidget() throws Exception {
		
		return titleWidget(Integer.toString(currentWidgetId++));
	}

	public TitleWidget titleWidget(String widgetId) throws Exception {
		
		return new TitleWidget(lcdProc, this, widgetId);
	}

	public VBarWidget vbarWidget() throws Exception {
		
		return vbarWidget(Integer.toString(currentWidgetId++));
	}

	public VBarWidget vbarWidget(String widgetId) throws Exception {
		
		return new VBarWidget(lcdProc, this, widgetId);
	}

	public HBarWidget hbarWidget() throws Exception {
		
		return hbarWidget(Integer.toString(currentWidgetId++));
	}

	public HBarWidget hbarWidget(String widgetId) throws Exception {
		
		return new HBarWidget(lcdProc, this, widgetId);
	}

	public IconWidget iconWidget() throws Exception {
		
		return iconWidget(Integer.toString(currentWidgetId++));
	}

	public IconWidget iconWidget(String widgetId) throws Exception {
		
		return new IconWidget(lcdProc, this, widgetId);
	}

	public ScrollerWidget scrollerWidget() throws Exception {
		
		return scrollerWidget(Integer.toString(currentWidgetId++));
	}

	public ScrollerWidget scrollerWidget(String widgetId) throws Exception {
		
		return new ScrollerWidget(lcdProc, this, widgetId);
	}
	
	public FrameWidget frameWidget() throws Exception {
		
		return frameWidget(Integer.toString(currentWidgetId++));
	}

	public FrameWidget frameWidget(String widgetId) throws Exception {
		
		return new FrameWidget(lcdProc, this, widgetId);
	}
	
	public NumWidget numWidget() throws Exception {
		
		return numWidget(Integer.toString(currentWidgetId++));
	}

	public NumWidget numWidget(String widgetId) throws Exception {
		
		return new NumWidget(lcdProc, this, widgetId);
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
