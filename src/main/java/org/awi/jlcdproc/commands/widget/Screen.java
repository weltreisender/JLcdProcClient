package org.awi.jlcdproc.commands.widget;


import org.awi.jlcdproc.LcdProc;
import org.awi.jlcdproc.commands.Backlight;
import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.commands.CommandOption;
import org.awi.jlcdproc.commands.Heartbeat;
import org.awi.jlcdproc.commands.Priority;
import org.awi.jlcdproc.events.CommandResultEvent;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.StateEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

import static org.awi.jlcdproc.commands.CommandParameters.*;
import static org.awi.jlcdproc.commands.widget.ScreenOption.*;

/**
 * Class representing an LCDproc screen.
 * <p>
 * It encapsulates the screen identifier that may either be generated
 * automatically or provided by the user.
 * <p>
 * The class provides several methods to create {@link Widget Widgets} that will
 * be shown on this screen.
 */
public class Screen extends Command implements EventListener {

	private static final String SCREEN_ADD = "screen_add";

	private static final String SCREEN_DEL = "screen_del";

	private static final String SCREEN_SET = "screen_set";

	private ScreenState state = ScreenState.UNKNOWN;

	private String screenId;

	private int currentWidgetId = 0;
	
	/**
	 * Constructor called internally by {@link LcdProc#screen()}
	 * 
	 * @param lcdProc
	 *            {@link LcdProc} instance creating this object
	 * @param screenId
	 *            automatically generated screen identifier
	 * @throws Exception
	 *             if the screen cannot be created
	 */
	public Screen(LcdProcInternal lcdProc, int screenId) throws Exception {

		this(lcdProc, Integer.toString(screenId));
	}

	/**
	 * Constructor called internally by {@link LcdProc#screen(String)}
	 * 
	 * @param lcdProc
	 *            {@link LcdProc} instance creating this object
	 * @param screenId
	 *            screen identifier provided by the user
	 * @throws Exception
	 *             if the screen cannot be created
	 */
	public Screen(LcdProcInternal lcdProc, String screenId) throws Exception {

		super(lcdProc);

		this.screenId = screenId;

		lcdProc.addEventListener(this);

		send(SCREEN_ADD, params(screenId));
	}

	/**
	 * Returns the {@link ScreenState}
	 * 
	 * @return {@link ScreenState}
	 */
	public ScreenState getState() {

		return state;
	}

	public Screen set(CommandOption ... args) throws Exception {

		send(SCREEN_SET, params(screenId), args);

		return this;
	}

	/**
	 * Sets the LCDproc screen name
	 * 
	 * @param name
	 *            Name of the screen
	 * @return this
	 * @throws Exception
	 *             if the screen name cannot be set
	 */
	public Screen setName(String name) throws Exception {

		return set(name(name));
	}

	/**
	 * Sets the screen width and height
	 * 
	 * @param width
	 * @param height
	 * @return this
	 * @throws Exception
	 *             if the width and hight cannot be set
	 */
	public Screen setDimensions(int width, int height) throws Exception {

		return  set(width(width), height(height));
	}

	/**
	 * Sets the screen priority using predefined classes.
	 * <p>
	 * LCDd will only show screens with the highest priority at that moment. So
	 * when there are three {@link Priority#INFO INFO} screens and one
	 * {@link Priority#FOREGROUND FOREGROUND} screen, only the
	 * {@link Priority#FOREGROUND FOREGROUND} screen will be visible. Only
	 * {@link Priority#BACKGROUND BACKGROUND}, {@link Priority#INFO INFO} and
	 * {@link Priority#FOREGROUND FOREGROUND} screens will rotate; higher
	 * classes do not rotate because their purpose is not suitable for rotation.
	 * 
	 * @param priority
	 *            {@link Priority#HIDDEN HIDDEN}, {@link Priority#BACKGROUND
	 *            BACKGROUND}, {@link Priority#INFO INFO},
	 *            {@link Priority#FOREGROUND FOREGROUND}, {@link Priority#ALERT
	 *            ALERT}, {@link Priority#INPUT INPUT}
	 * @return this
	 * @throws Exception
	 *             if the priotity cannot be set
	 */
	public Screen setPriority(Priority priority) throws Exception {

		return set(priority(priority));
	}

	/**
	 * Sets the screen priority.
	 * <p>
	 * 
	 * <table border="1">
	 * <colgroup><col><col></colgroup><thead>
	 * <tr>
	 * <th>range</th>
	 * <th>priority</th>
	 * </tr>
	 * </thead><tbody>
	 * <tr>
	 * <td>1 - 64</td>
	 * <td>foreground</td>
	 * </tr>
	 * <tr>
	 * <td>65 - 192</td>
	 * <td>info</td>
	 * </tr>
	 * <tr>
	 * <td>193 - &#8734;</td>
	 * <td>background</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 * 
	 * @param priority
	 *            A positive integer that maps to priority classes above
	 *            according to the mapping given in the table above.
	 * @return
	 * @throws Exception
	 */
	public Screen setPriority(int priority) throws Exception {

		return set(priority(priority));
	}

	/**
	 * Changes the heartbeat setting for this screen. If set to
	 * {@link Heartbeat#OPEN OPEN}, the default, the client's heartbeat setting
	 * will be used.
	 * 
	 * @param heartbeat
	 *            {@link Heartbeat}
	 * @return this
	 * @throws Exception
	 *             if the heartbeat cannot be set
	 */
	public Screen setHeartbeat(Heartbeat heartbeat) throws Exception {

		return set(heartbeat(heartbeat));
	}

	/**
	 * Changes the screen's backlight setting. If set to the default value
	 * {@link Backlight#OPEN OPEN}, the state will be determined by the client's
	 * setting. {@link Backlight#BLINK BLINK} is a moderately striking backlight
	 * variation, {@link Backlight#FLASH FLASH} is <em>very</em> strinking.
	 * 
	 * @param backlight
	 * @return
	 * @throws Exception
	 */
	public Screen setBacklight(Backlight backlight) throws Exception {

		return set(backlight(backlight));
	}

	/**
	 * A screen will be visible for this amount of time every rotation.
	 * 
	 * @param duration
	 *            The value is in eights of a second.
	 * @return this
	 * @throws Exception
	 *             if the duration cannot be set
	 */
	public Screen setDuration(int duration) throws Exception {

		return set(duration(duration));
	}

	/**
	 * After the screen has been visible for a total of this amount of time, it
	 * will be deleted. Currently the client will not be informed of the
	 * deletion (this is an issue of the LCDproc server and not of this client).
	 * 
	 * @param timeout
	 *            The value is in eights of a second.
	 * @return this
	 * @throws Exception
	 *             if the timeout cannot be set
	 */
	public Screen setTimeout(int timeout) throws Exception {

		return set(timeout(timeout));
	}

	/**
	 * Determines the visibility of a cursor. If {@link Cursor#ON ON}, a cursor
	 * will be visible. Depending on your hardware, this will be a hardware or
	 * software cursor. The specified cursor shape ({@link Cursor#BLOCK BLOCK}
	 * or {@link Cursor#UNDER UNDER}) might not be available in which case an
	 * other cursor shape will be used instead. Default is {@link Cursor#OFF
	 * OFF}.
	 * 
	 * @param cursor
	 *            {@link Cursor}
	 * @return this
	 * @throws Exception
	 *             if the cursor cannot be set
	 */
	public Screen setCursor(Cursor cursor) throws Exception {

		return set(cursor(cursor));
	}

	private boolean screenSetCursorPositionMode = false;

	@Override
	public boolean isCommandCompleted(CommandResultEvent event) {
	
		if (screenSetCursorPositionMode) {
			
			screenSetCursorPositionMode = false;
			return false;
		}
		return super.isCommandCompleted(event);
	}
	
	/**
	 * Set the cursor's x and y coordinates respectively. Coordinates are always
	 * 1-based. So the default top-left corner is denoted by (1,1).
	 * 
	 * @param x 
	 * @param y
	 * @return this
	 * @throws Exception
	 * if t
	 */
	public Screen setCursorPosition(int x, int y) throws Exception {

		return set(ScreenOption.cursorX(x), ScreenOption.cursorY(y));
	}

	/**
	 * Remove the screen
	 *  
	 * @throws Exception
	 */
	public void delete() throws Exception {

		lcdProc.removeEventListener(this);

		send(SCREEN_DEL, params(screenId));
	}

	/**
	 * Creates a new {@link StringWidget}
	 * 
	 * @return {@link StringWidget}
	 * 
	 * @throws Exception
	 */
	public StringWidget stringWidget() throws Exception {

		return stringWidget(Integer.toString(currentWidgetId++));
	}

	/**
	 * Creates a new {@link StringWidget} with the given widget ID.
	 * 
	 * @return {@link StringWidget}
	 * 
	 * @throws Exception
	 */
	public StringWidget stringWidget(String widgetId) throws Exception {

		return new StringWidget(lcdProc, this, widgetId);
	}

	/**
	 * Create a new {@link TitleWidget}
	 * 
	 * @return {@link TitleWidget}
	 * 
	 * @throws Exception
	 */
	public TitleWidget titleWidget() throws Exception {

		return titleWidget(Integer.toString(currentWidgetId++));
	}

	/**
	 * Create a new {@link TitleWidget} with the given widget ID
	 * 
	 * @return {@link TitleWidget}
	 * 
	 * @throws Exception
	 */
	public TitleWidget titleWidget(String widgetId) throws Exception {

		return new TitleWidget(lcdProc, this, widgetId);
	}

	/**
	 * Create a new {@link VBarWidget}
	 * 
	 * @return {@link VBarWidget}
	 * 
	 * @throws Exception
	 */
	public VBarWidget vbarWidget() throws Exception {

		return vbarWidget(Integer.toString(currentWidgetId++));
	}

	/**
	 * Create a new {@link VBarWidget} with the given widget ID
	 * 
	 * @return {@link VBarWidget}
	 * 
	 * @throws Exception
	 */
	public VBarWidget vbarWidget(String widgetId) throws Exception {

		return new VBarWidget(lcdProc, this, widgetId);
	}

	/**
	 * Create a new {@link HBarWidget}
	 * 
	 * @return {@link HBarWidget}
	 * 
	 * @throws Exception
	 */
	public HBarWidget hbarWidget() throws Exception {

		return hbarWidget(Integer.toString(currentWidgetId++));
	}

	/**
	 * Create a new {@link HBarWidget} with the given widget ID
	 * 
	 * @return {@link HBarWidget}
	 * 
	 * @throws Exception
	 */
	public HBarWidget hbarWidget(String widgetId) throws Exception {

		return new HBarWidget(lcdProc, this, widgetId);
	}

	/**
	 * Create a new {@link IconWidget}
	 * 
	 * @return {@link IconWidget}
	 * 
	 * @throws Exception
	 */
	public IconWidget iconWidget() throws Exception {

		return iconWidget(Integer.toString(currentWidgetId++));
	}

	/**
	 * Create a new {@link IconWidget} with the given widget ID
	 * 
	 * @return {@link IconWidget}
	 * 
	 * @throws Exception
	 */
	public IconWidget iconWidget(String widgetId) throws Exception {

		return new IconWidget(lcdProc, this, widgetId);
	}

	/**
	 * Create a new {@link ScrollerWidget}
	 * 
	 * @return {@link ScrollerWidget}
	 * 
	 * @throws Exception
	 */
	public ScrollerWidget scrollerWidget() throws Exception {

		return scrollerWidget(Integer.toString(currentWidgetId++));
	}

	/**
	 * Create a new {@link ScrollerWidget} with the given widget ID
	 * 
	 * @return {@link ScrollerWidget}
	 * 
	 * @throws Exception
	 */
	public ScrollerWidget scrollerWidget(String widgetId) throws Exception {

		return new ScrollerWidget(lcdProc, this, widgetId);
	}

	/**
	 * Create a new {@link FrameWidget}
	 * 
	 * @return {@link FrameWidget}
	 * 
	 * @throws Exception
	 */
	public FrameWidget frameWidget() throws Exception {

		return frameWidget(Integer.toString(currentWidgetId++));
	}

	/**
	 * Create a new {@link FrameWidget} with the given widget ID
	 * 
	 * @return {@link FrameWidget}
	 * 
	 * @throws Exception
	 */
	public FrameWidget frameWidget(String widgetId) throws Exception {

		return new FrameWidget(lcdProc, this, widgetId);
	}

	/**
	 * Create a new {@link NumWidget}
	 * 
	 * @return {@link NumWidget}
	 * 
	 * @throws Exception
	 */
	public NumWidget numWidget() throws Exception {

		return numWidget(Integer.toString(currentWidgetId++));
	}

	/**
	 * Create a new {@link NumWidget} with the given widget ID
	 * 
	 * @return {@link NumWidget}
	 * 
	 * @throws Exception
	 */
	public NumWidget numWidget(String widgetId) throws Exception {

		return new NumWidget(lcdProc, this, widgetId);
	}

	/**
	 * Getter
	 * 
	 * @return Screen ID
	 */
	public String getScreenId() {

		return screenId;
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.events.EventListener#onEvent(org.awi.jlcdproc.events.Event)
	 */
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
