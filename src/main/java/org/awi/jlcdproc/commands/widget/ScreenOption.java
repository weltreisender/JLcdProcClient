package org.awi.jlcdproc.commands.widget;

import org.awi.jlcdproc.commands.Backlight;
import org.awi.jlcdproc.commands.CommandOption;
import org.awi.jlcdproc.commands.Heartbeat;
import org.awi.jlcdproc.commands.Priority;

/**
 * Helper class that provides static functions to define {@link Screen} options
 */
public class ScreenOption extends CommandOption {

	private ScreenOption(String option, Object arg) {
		super(option, arg);
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
	public static CommandOption name(String name) {

		return new ScreenOption("-name", name);
	}

	/**
	 * Sets the ScreenOption width
	 * 
	 * @param width
	 */
	public static CommandOption width(int width) {

		return new ScreenOption("-wid", width);
	}

	/**
	 * Sets the ScreenOption height
	 * 
	 * @param height
	 */
	public static CommandOption height(int height) {

		return new ScreenOption("-hgt", height);
	}

	/**
	 * Sets the ScreenOption priority using predefined classes.
	 * <p>
	 * LCDd will only show screens with the highest priority at that moment. So
	 * when there are three {@link Priority#INFO INFO} screens and one
	 * {@link Priority#FOREGROUND FOREGROUND} screen, only the
	 * {@link Priority#FOREGROUND FOREGROUND} ScreenOption will be visible. Only
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
	public static CommandOption priority(Priority priority) {

		return new ScreenOption("-priority", priority.name().toLowerCase());
	}

	/**
	 * Sets the ScreenOption priority.
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
	public static CommandOption priority(int priority) {

		return new ScreenOption("-priority", priority);
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
	public static CommandOption heartbeat(Heartbeat heartbeat) {

		return new ScreenOption("-heartbeat", heartbeat.name().toLowerCase());
	}

	public static final CommandOption heartbeatOn = heartbeat(Heartbeat.ON);
	public static final CommandOption heartbeatOff = heartbeat(Heartbeat.OFF);

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
	public static CommandOption backlight(Backlight backlight) {

		return new ScreenOption("-backlight", backlight.name().toLowerCase());
	}

	/**
	 * A ScreenOption will be visible for this amount of time every rotation.
	 * 
	 * @param duration
	 *            The value is in eights of a second.
	 * @return this
	 * @throws Exception
	 *             if the duration cannot be set
	 */
	public static CommandOption duration(int duration) {

		return new ScreenOption("-duration", duration);
	}

	/**
	 * After the ScreenOption has been visible for a total of this amount of
	 * time, it will be deleted. Currently the client will not be informed of
	 * the deletion (this is an issue of the LCDproc server and not of this
	 * client).
	 * 
	 * @param timeout
	 *            The value is in eights of a second.
	 * @return this
	 * @throws Exception
	 *             if the timeout cannot be set
	 */
	public static CommandOption timeout(int timeout) {

		return new ScreenOption("-timeout", timeout);
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
	public static CommandOption cursor(Cursor cursor) {

		return new ScreenOption("-cursor", cursor.name().toLowerCase());
	}

	public static final CommandOption cursorOn = cursor(Cursor.ON);
	public static final CommandOption cursorOff = cursor(Cursor.OFF);
	public static final CommandOption cursorBlock = cursor(Cursor.BLOCK);
	public static final CommandOption cursorUnder = cursor(Cursor.UNDER);

	/**
	 * Set the cursor's x coordinate. Coordinates are always 1-based. So the
	 * default top-left corner is denoted by (1,1).
	 * 
	 * @param x
	 * @return this
	 */
	public static CommandOption cursorX(int x) {

		return new ScreenOption("-cursor_x", x);
	}

	/**
	 * Set the cursor's y coordinate. Coordinates are always 1-based. So the
	 * default top-left corner is denoted by (1,1).
	 * 
	 * @param y
	 * @return this
	 * @throws Exception
	 *             if t
	 */
	public static CommandOption cursorY(int y) {

		return new ScreenOption("-cursor_y", y);
	}
}
